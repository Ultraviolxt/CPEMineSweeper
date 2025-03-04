package MineSweeper;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.JButton;

public class MineSweeper extends OnScreen{

    @Override
    public void initGame() {
        // คำนวณจำนวนลูกระเบิดให้เป็น 15% ของจำนวนช่องทั้งหมด
        mineCount = (int) Math.round(numRows * numCols * 0.15);
    
        // กำหนดขนาดฟอนต์ให้ลดลงเมื่อจำนวนแถวมากขึ้น
        int fontSize = Math.max(10, 50 - numRows);
    
        // ตั้งค่าหน้าต่างของเกม
        frame = new JFrame("Minesweeper");
        frame.setSize(boardWidth, boardHeight + 50);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
    
        // แสดงจำนวนระเบิดที่เหลือ
        textLabel = new JLabel("Minesweeper: " + mineCount, JLabel.CENTER);
        textLabel.setFont(new Font("Arial", Font.BOLD, 25));
        textLabel.setOpaque(true);
        
        // สร้างแผงสำหรับข้อความ และเพิ่มป้ายข้อความเข้าไป
        textPanel = new JPanel(new BorderLayout());
        textPanel.add(textLabel, BorderLayout.CENTER);
        frame.add(textPanel, BorderLayout.NORTH);
    
        // สร้างตารางของเกมโดยใช้ GridLayout
        boardPanel = new JPanel(new GridLayout(numRows, numCols));
        frame.add(boardPanel);
        
        // สร้างปุ่มเริ่มเกมใหม่
        replayButton = new JButton("Replay");
        replayButton.setFont(new Font("Arial", Font.BOLD, 16));
        replayButton.setVisible(false);
        replayButton.addActionListener(e -> {
            frame.dispose();
            initGame(); // เริ่มเกมใหม่
        });
        textPanel.add(replayButton, BorderLayout.EAST);
    
        // สร้างบอร์ดเกมและเก็บตำแหน่งของระเบิด
        board = new MineTile[numRows][numCols];
        mineList = new ArrayList<>();
        tilesClicked = 0;
        gameOver = false;
        
        // วนลูปสร้างช่อง MineTile ให้ครบทุกช่องบนกระดาน
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                MineTile tile = new MineTile(r, c);
                board[r][c] = tile;
                tile.setFocusable(false);
                tile.setMargin(new Insets(0, 0, 0, 0));
                tile.setFont(new Font("Arial Unicode MS", Font.PLAIN, fontSize));

                // เพิ่มการตรวจจับการคลิกของผู้เล่น
                tile.addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent e) {
                        if (gameOver) return;
                        MineTile tile = (MineTile) e.getSource();
                        
                        if (e.getButton() == MouseEvent.BUTTON1) { // คลิกซ้าย
                            if (tile.getText().equals("")) {
                                if (mineList.contains(tile)) {
                                    revealMines(); // ถ้าคลิกโดนระเบิด
                                } else {
                                    checkMine(tile.getRow(), tile.getColumn()); // ตรวจสอบพื้นที่
                                }
                            }
                        } else if (e.getButton() == MouseEvent.BUTTON3) { // คลิกขวา
                            if (tile.getText().equals("") && tile.isEnabled()) {
                                tile.setText("🚩"); // ตั้งธง
                            }
                            else if (tile.getText().equals("🚩")) {
                                tile.setText(""); // เอาธงออก
                            }
                        }
                    }
                });

                boardPanel.add(tile);
            }
        }
        
        frame.setVisible(true);
        setMines(); // สุ่มตำแหน่งระเบิด
    }

    @Override
    public void setMines() {
        int mineLeft = mineCount;
        while (mineLeft > 0) {
            int r = random.nextInt(numRows);
            int c = random.nextInt(numCols);
            MineTile tile = board[r][c];
            if (!mineList.contains(tile)) {
                mineList.add(tile);
                mineLeft--;
            }
        }
    }

    @Override
    public void revealMines() {
        // แสดงตำแหน่งของระเบิดทั้งหมด
        for (int i = 0; i < mineList.size(); i++) {
            MineTile tile = mineList.get(i);
            tile.setText("💣");
        }
        
        gameOver = true;
        textLabel.setText("Game Over!"); // แจ้งว่าเกมจบแล้ว
        replayButton.setVisible(true); // แสดงปุ่มเล่นใหม่
    }

    @Override
    public void checkMine(int r, int c) {
        // ตรวจสอบว่าอยู่ในขอบเขตของกระดานหรือไม่
        if (r < 0 || r >= numRows || c < 0 || c >= numCols) return;
        
        MineTile tile = board[r][c];
        if (!tile.isEnabled()) return;
        
        tile.setEnabled(false);
        tilesClicked++;

        // ตรวจสอบจำนวนระเบิดรอบๆ ช่องนี้
        int minesFound = countMinesAround(r, c);
        if (minesFound > 0) {
            tile.setText(String.valueOf(minesFound));
        } else {
            tile.setText(""); // ถ้าไม่มีระเบิดรอบข้างให้เปิดช่องว่างต่อไป
            spreadClear(r, c);
        }
        
        // เช็กว่าผู้เล่นชนะเกมหรือไม่
        if (tilesClicked == numRows * numCols - mineList.size()) {
            gameOver = true;
            textLabel.setText("Mines Cleared!"); // แจ้งว่าชนะเกมแล้ว
            replayButton.setVisible(true);
        }
    }

    // ตรวจสอบจำนวนระเบิดรอบๆ ช่องที่ระบุ
    public int countMinesAround(int r, int c) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (r + i >= 0 && r + i < numRows && c + j >= 0 && c + j < numCols) {
                    if (mineList.contains(board[r + i][c + j])) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    @Override
    public void spreadClear(int r, int c) {
        // เปิดช่องว่างรอบข้างหากไม่มีระเบิด
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                checkMine(r + i, c + j);
            }
        }
    }
}
