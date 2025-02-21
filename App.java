public class App {
    /**
     * 
     * @param args = เปิดโปรแกรม
     * @throws Exception = เกิดข้อผิดพลาดหรือไม่
     * @param หน้าหลักที่รวมคำสั่งเปิดโปรแกรม
     */
    public static void main(String[] args) throws Exception {
        MineSweeper minesweeper = new MineSweeper();
        minesweeper.showStartMenu();
        
    }
}