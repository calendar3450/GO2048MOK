import javax.swing.*;

public class Main_Omok extends JFrame{

    public Main_Omok() {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        Data_Omok.setId(JOptionPane.showInputDialog("닉네임"));
        MainPanel_Omok mainP = new MainPanel_Omok();
        Controller_Omok controller = new Controller_Omok(mainP);
        controller.connect_server();
        mainP.addButtonListener(controller.btnListener);
        this.getContentPane().add(mainP);
        this.pack();
        this.setVisible(true);

    }

}
