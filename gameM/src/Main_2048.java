import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Main_2048 extends JFrame {

    public Main_2048() {
        super("선택창");

        JPanel jPanel = new JPanel();
        JButton btn1 = new JButton("2048");
        JButton btn2 = new JButton("오목");
        setSize(300, 200);
        jPanel.add(btn1);
        jPanel.add(btn2);
        add(jPanel);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        btn1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new playWindow();
                setVisible(false); // 창 안보이게 하기
            }
        });

        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Main_Omok();
                setVisible(false);
            }
        });
    }
    public static void main(String[] args) {
        new Main_2048();
    }
}


