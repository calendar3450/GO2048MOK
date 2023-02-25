import javax.swing.JFrame;

public class playWindow {
    public playWindow() {

        JFrame frame = new JFrame("2048");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        View_2048 view = new View_2048();
        Controller_2048 controller = new Controller_2048(view);
        view.addReStartButtonListener(controller.reStartButtonListener);


        frame.getContentPane().add(view);
        frame.pack();
        frame.setVisible(true);
    }
}
