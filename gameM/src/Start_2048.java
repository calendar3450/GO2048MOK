import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class Start_2048 extends JFrame {

    public Start_2048() {

        super("MAIN");
        JPanel jPanel = new JPanel();
        JButton btn1 = new JButton("2048");
        setSize(300, 200); //창 크기 설정
        jPanel.add(btn1);
        add(jPanel);

        Dimension frameSize = getSize();

        Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((windowSize.width - frameSize.width) / 2,
                (windowSize.height - frameSize.height) / 2); //화면 중앙에 띄우기
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        btn1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Main_2048();
                setVisible(false); // 창 안보이게 하기
            }
        });
    }

    public static void main(String[] args) {
        new Start_2048();

    }


}
