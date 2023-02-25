import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainPanel_Omok extends JPanel {

    public RoomView_Omok roomview;
    public JButton croom_btn;
    public JButton back_btn;
    public JList<String> room_list;
    public DefaultListModel<String> room_model;

    MainPanel_Omok() {
        room_model = new DefaultListModel<>();
        this.setPreferredSize(new Dimension(800, 1000));
        this.setLayout(null);
        back_btn = new JButton("<");
        back_btn.setBounds(0, 0, 50, 50);
        add(back_btn);
        croom_btn = new JButton("+");
        croom_btn.setBounds(740, 0, 50, 50);
        add(croom_btn);

        JLabel roomLabel = new JLabel("방목록");
        roomLabel.setFont(roomLabel.getFont().deriveFont(22.0f));
        roomLabel.setHorizontalAlignment(JLabel.CENTER);
        roomLabel.setBounds(0, 0, 800, 50);
        add(roomLabel);

        room_list = new JList<>(room_model);
        room_list.setFixedCellHeight(50);
        room_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        room_list.setBounds(0, 50, 800, 950);

        add(room_list);
    }
    public void addButtonListener(ActionListener listener) {
        croom_btn.addActionListener(listener);
        back_btn.addActionListener(listener);
    }



}
