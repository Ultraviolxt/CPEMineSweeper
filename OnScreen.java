package MineSweeper;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public abstract class OnScreen {
    //ไปอยู่ initgame
    int tileSize = 70;//ขนาดพื้นที่ของช่องระเบิด
    int numRows = 10;//ความกว้างยาวของบอร์ดเกม(จตุรัส)
    int numCols = numRows;
    int boardWidth = numCols * tileSize;
    int boardHeight = numRows * tileSize;

    int mineCount = 15;//จำนวนระเบิดในกระดาน
    MineTile[][] board;
    ArrayList<MineTile> mineList;
    Random random = new Random();
    
    JFrame frame;
    JLabel textLabel;
    JPanel textPanel, boardPanel;
    JButton replayButton;

    int tilesClicked;
    boolean gameOver;

    // เมธอดที่ต้องให้คลาสลูก Override เอง
    public abstract void initGame();
    public abstract void setMines();
    public abstract void revealMines();
    public abstract void checkMine(int r, int c);
    public abstract void spreadClear(int r, int c);
}