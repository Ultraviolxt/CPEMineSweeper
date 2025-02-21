import javax.swing.JButton;

public class MineTile extends JButton {

    private int row;
    private int column;

    /**
     * 
     * @param r = เลขแถวของช่องระเบิด
     * @param c = เลขคอลัมม์ของช่องระเบิด
     */

    MineTile(int r, int c) {
        this.row = r;
        this.column = c;
    }
    /** 
     * @return เลขแถวของช่องระเบิด
     */
    public int getRow(){
        return this.row;
    }
    /**
    * 
    * @return เลขคอลัมม์ของช่องระเบิด
    */
    public int getColumn(){
        return this.column;
    }
}