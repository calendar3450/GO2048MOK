import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class RoomView_Omok extends JFrame {
    public Draw_Omok draw;
    private JPanel mainPanel, btnPanel, playerPanel;

    public JButton decision_btn;
    public JButton exit_btn;
    public JButton surrender_btn;
    private JLabel player_id;
    public JLabel opponent_id;

    public RoomView_Omok() {
        mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(800, 1000));
        mainPanel.setBackground(Color.white);
        mainPanel.setLayout(null);

        draw = new Draw_Omok(this);
        draw.setBounds(0,0,800,800);
        mainPanel.add(draw);

        playerPanel = new JPanel();
        playerPanel.setBounds(0, 800, 800, 150);
        playerPanel.setBackground(Color.white);
        playerPanel.setLayout(new GridLayout(1, 3));
        mainPanel.add(playerPanel);

        Font font = new Font("궁서체", Font.BOLD, 30);

        player_id = new JLabel(Data_Omok.getId());
        player_id.setFont(font);
        player_id.setHorizontalAlignment(SwingConstants.CENTER);
        playerPanel.add(player_id);

        decision_btn = new JButton("준비");

        decision_btn.setFont(font);
        playerPanel.add(decision_btn);

        opponent_id = new JLabel("상대 아이디");
        opponent_id.setFont(font);
        opponent_id.setHorizontalAlignment(SwingConstants.CENTER);
        playerPanel.add(opponent_id);

        btnPanel = new JPanel();
        btnPanel.setBounds(0, 950, 800, 50);
        btnPanel.setBackground(Color.BLUE);
        btnPanel.setLayout(new GridLayout(1, 2));
        mainPanel.add(btnPanel);

        surrender_btn = new JButton("항복");
        surrender_btn.setFont(font);
        btnPanel.add(surrender_btn);

        exit_btn = new JButton("나가기");
        exit_btn.setFont(font);
        btnPanel.add(exit_btn);

        this.getContentPane().add(mainPanel);
        this.pack();
        this.setVisible(true);
    }

    private class MouseAction implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            JButton btn = (JButton) e.getSource();
            btn.setForeground(Color.red);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            JButton btn = (JButton) e.getSource();
            btn.setForeground(Color.BLACK);
        }
    }

    public void addButtonListener(ActionListener listener) {
        decision_btn.addActionListener(listener);
        surrender_btn.addActionListener(listener);
        exit_btn.addActionListener(listener);
    }

}
