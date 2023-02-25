public class Data_Omok {

    private static String id;
    static int myColor;
    private static final int BOARD_SIZE = 31;
    private static final int CELL_SIZE = 25;
    private static final int STONE_SIZE = 28;

    public static String getId () {return id;}
    public static void setId(String _id) {id = _id;}
    public static int getBOARD_SIZE() {
        return BOARD_SIZE;
    }
    public static int getCELL_SIZE() {
        return CELL_SIZE;
    }
    public static int getSTONE_SIZE() {
        return STONE_SIZE;
    }
    public static void setMyColor(int color) {myColor = color;}
    public static int getMyColor(){return myColor;}



}
