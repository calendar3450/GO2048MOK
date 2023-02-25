import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Draw_Omok extends JPanel {

    private RoomView_Omok view;
    private int[][] board;
    public int[] tmp_location = {-1, -1};
    public boolean my_turn;

    public Draw_Omok(RoomView_Omok view) {
        this.view = view;

        setBackground(new Color(206, 167, 61));
        init();
        MouseML mouseML = new MouseML();
        addMouseListener(mouseML);
    }

    public void init() {
        board = new int[Data_Omok.getBOARD_SIZE()+1][Data_Omok.getBOARD_SIZE()+1];
        System.out.println("color = "+Data_Omok.myColor);
        if (Data_Omok.myColor == 1) {
            System.out.println("검은색임");
            my_turn = true;
        } else {
            System.out.println("흰 색임");
            my_turn = false;
        }
        repaint();
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.setColor(Color.BLACK);
        drawLine(graphics);
        drawStones(graphics);
        repaint();
    }

    private void drawLine(Graphics g) {
        for (int i = 1; i <= Data_Omok.getBOARD_SIZE(); i++) {
            g.drawLine(Data_Omok.getCELL_SIZE(), i*Data_Omok.getCELL_SIZE(), Data_Omok.getCELL_SIZE()*Data_Omok.getBOARD_SIZE(), i*Data_Omok.getCELL_SIZE());
            g.drawLine(i*Data_Omok.getCELL_SIZE(), Data_Omok.getCELL_SIZE(), i*Data_Omok.getCELL_SIZE() , Data_Omok.getCELL_SIZE()*Data_Omok.getBOARD_SIZE());
        }
    }

    private void drawStones(Graphics g) {
        for (int i = 0; i <= Data_Omok.getBOARD_SIZE(); i++) {
            for (int j = 0; j <= Data_Omok.getBOARD_SIZE(); j++) {
                if (board[i][j] == 1) {
                    g.setColor(Color.BLACK);
                    g.fillOval((j-1) * Data_Omok.getCELL_SIZE() + Data_Omok.getCELL_SIZE()/2, i * Data_Omok.getCELL_SIZE() - Data_Omok.getCELL_SIZE()/2, Data_Omok.getSTONE_SIZE(), Data_Omok.getSTONE_SIZE());
                } else if (board[i][j] == 2) {
                    g.setColor(Color.white);
                    g.fillOval((j-1) * Data_Omok.getCELL_SIZE() + Data_Omok.getCELL_SIZE()/2, i * Data_Omok.getCELL_SIZE() - Data_Omok.getCELL_SIZE()/2, Data_Omok.getSTONE_SIZE(), Data_Omok.getSTONE_SIZE());
                }
            }
        }
    }

    public void clear_tmp() {
        tmp_location[0] = -1;
        tmp_location[1] = -1;
    }

    public void setBoard(int x, int y) {
        int color;
        if (Data_Omok.myColor == 1) color = 2;
        else color = 1;
        this.board[y][x] = color;
        repaint();
    }

    public boolean check5(int x, int y) {
        int color = Data_Omok.myColor;
        System.out.println("color = "+color);

        int count = 0;
        //세로

        for (int i = y; i <= Data_Omok.getBOARD_SIZE(); i++) {
            if (board[i][x] == Data_Omok.myColor) {
                count++;
            } else break;
        }
        for (int i = y-1; i >= 0; i--) {
            if (board[i][x] == Data_Omok.myColor) count++;
            else break;
        }
        if (count >= 5) return true;

        //가로
        count = 0;
        for (int i = x; i <= Data_Omok.getBOARD_SIZE(); i++) {
            if (board[y][i] == Data_Omok.myColor) count++;
            else break;
        }
        for (int i = x-1; i >= 0; i--) {
            if (board[y][i] == Data_Omok.myColor) count++;
            else break;
        }
        if (count >= 5) return true;

        //대각선
        count = 0;
        for (int i = 0; i <= 5; i++) {
            if (y+i > Data_Omok.getBOARD_SIZE() || x+i >= Data_Omok.getBOARD_SIZE()) break;
            else if (board[y+i][x+i] == Data_Omok.myColor) count++;
            else break;
        }
        for (int i = 1; i <= 5; i++) {
            if (y-i < 0 || x - i < 0) break;
            else if  (board[y-i][x-i] == Data_Omok.myColor) count++;
            else break;
        }
        if (count >= 5) return true;
        //대각선 /
        count = 0;
        for (int i = 0; i <= 5; i++) {
            if (y+i > Data_Omok.getBOARD_SIZE() || x-i < 0) break;
            else if (board[y+i][x-i] == Data_Omok.myColor) count++;
            else break;
        }
        for (int i = 1; i <= 5; i++) {
            if (y-i < 0 || x+i > Data_Omok.getBOARD_SIZE()) break;
            else if (board[y-i][x+i] == Data_Omok.myColor) count++;
        }

        return count >= 5;
    }

    private class MouseML implements MouseListener, MouseMotionListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            //System.out.println("클릭");
            if (!my_turn) {
                System.out.println("차례가 아님");

                return;
            }
            int x = (int)Math.round(e.getX()/(double) Data_Omok.getCELL_SIZE());
            int y = (int)Math.round(e.getY()/(double) Data_Omok.getCELL_SIZE());
            //System.out.println("x = "+x+" y = "+y);
            if (board[y][x] != 0 || y < 1 || y > Data_Omok.getBOARD_SIZE() || x > Data_Omok.getBOARD_SIZE()) {
                //if (board[y][x] != 0) System.out.println("보드 자리 없음");
                //if (datas.getBOARD_SIZE() < y) System.out.println("y");
                //if (datas.getBOARD_SIZE() < x) System.out.println("x");
                System.out.println("범위 밖");
                return;
            }
            if (tmp_location[0] != -1) {
                board[tmp_location[1]][tmp_location[0]] = 0;
            }
            tmp_location[0] = x;
            tmp_location[1] = y;

            board[y][x] = Data_Omok.myColor;
            repaint();

        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}

        @Override
        public void mouseDragged(MouseEvent e) {}

        @Override
        public void mouseMoved(MouseEvent e) {}
    }

}
