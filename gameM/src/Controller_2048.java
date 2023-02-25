import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;




public class Controller_2048 {

    public View_2048 view;

    public ReStartButtonListener reStartButtonListener;


    public Controller_2048(View_2048 v) {
        view = v;
        reStartButtonListener = new ReStartButtonListener();
        view.movePanel.Start();
        view.movePanel.ChangeNowScore();
        view.movePanel.ChangeBestScore();

    }



    private class ReStartButtonListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            Object obj = e.getSource();

            if(obj == view.btnReStart)
            {
                //restart 코드
                view.movePanel.Start();

            }
        }}


}
