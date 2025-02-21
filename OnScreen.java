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

    int mineCount = 10;//จำนวนระเบิดในกระดาน
    MineTile[][] board;
    ArrayList<MineTile> mineList;
    Random random = new Random();
    
    JFrame frame;
    JLabel textLabel;
    JPanel textPanel, boardPanel;
    JButton replayButton;

    int tilesClicked;
    boolean gameOver;

    public abstract void disappear();
    public abstract void show();
}
