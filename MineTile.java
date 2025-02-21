import javax.swing.JButton;

public class MineTile extends JButton {
    /* 
    *@params
    row = เลขแถวในเกม
    column = เลขคอลัมม์ในเกม
    
    **/
    private int row;
    private int column;

    public MineTile(int r, int c) {
        this.row = r;
        this.column = c;
    }

    public int getRow(){
        return this.row;
    }

    public int getColumn(){
        return this.column;
    }
}