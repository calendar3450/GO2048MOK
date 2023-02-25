
import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.event.*;
import java.util.ArrayList;

public class View_2048 extends JPanel {

    //<패널>
    // 박스들을 가둘 큰 패널
    private JPanel bigBoxPanel;
    // 배치될 작은 박스 패널들(초기화 해줘야함.)
    public JPanel[][] smallBoxPanel;
    public JLabel[][] smallBoxNumbertxt;

    // 현재 점수를 보여줄 패널
    public JPanel nowScorePanel;
    public JLabel nowScorenumbertxt;


    // 최고 점수를 보여줄 패널
    public JPanel bestScorePanel;
    public JLabel bestScorenumbertxt;


    //<버튼>
    // 재시작 버튼
    public JButton btnReStart;
    // 재시작 버튼의 패널
    public JPanel RestartPanel;

    //움직이기 위한 패널
    public MovePanel_2048 movePanel;

    public View_2048() {
        //기본 화면
        setPreferredSize(new Dimension(500,650));
        setBackground(Color.white);
        setLayout(null);

        // 박스들을 가둘 큰 패널
        bigBoxPanel = new JPanel();

        //중앙 배치하기 위한 위치는 (배경화면 가로 or 배경화면 세로 / 2) = (가로 or 세로+(넓이 or 높이 / 2))
        bigBoxPanel.setBounds(50,175,400,400);
        bigBoxPanel.setBorder(new LineBorder(Color.black,3));
        bigBoxPanel.setBackground(Color.white);
        bigBoxPanel.setLayout(new GridLayout(4,4));

        add(bigBoxPanel);

        // 작은 박스들
        smallBoxPanel = new JPanel[4][4];
        smallBoxNumbertxt = new JLabel[4][4];

        for(int i=0;i<4;i++)
        {
            for(int j=0;j<4;j++)
            {
                smallBoxPanel[i][j] = new JPanel();
                smallBoxPanel[i][j].setBorder(new LineBorder(Color.black,1));
                smallBoxPanel[i][j].setBackground(Color.white);
                smallBoxNumbertxt[i][j] = new JLabel("",JLabel.CENTER);
                smallBoxNumbertxt[i][j].setFont(getFont().deriveFont(30.0f));
                smallBoxPanel[i][j].add(smallBoxNumbertxt[i][j]);
                bigBoxPanel.add(smallBoxPanel[i][j]);

            }
        }

        // 현재 점수
        nowScorePanel =new JPanel();
        nowScorePanel.setBounds(250,100,100,50);
        nowScorePanel.setBorder(new LineBorder(Color.black,1));
        nowScorePanel.setBackground(Color.white);
        nowScorenumbertxt = new JLabel("0",JLabel.CENTER);
        nowScorenumbertxt.setFont(getFont().deriveFont(30.0f));
        nowScorePanel.add(nowScorenumbertxt);
        add(nowScorePanel);

        // 최고 점수
        bestScorePanel =new JPanel();
        bestScorePanel.setBounds(350,100,100,50);
        bestScorePanel.setBorder(new LineBorder(Color.black,1));
        bestScorePanel.setBackground(Color.white);
        bestScorenumbertxt = new JLabel("0",JLabel.CENTER);
        bestScorenumbertxt.setFont(getFont().deriveFont(30.0f));
        bestScorePanel.add(bestScorenumbertxt);
        add(bestScorePanel);

        //재시작 버튼의 패널 RestartPanel
        RestartPanel = new JPanel();
        RestartPanel.setBounds(150,585,200,50);
        RestartPanel.setBorder(new LineBorder(Color.black,1));
        RestartPanel.setLayout(new BorderLayout(200,50));

        // 재시작 버튼 btnColor
        btnReStart = new JButton("Restart");
        btnReStart.setFont(getFont().deriveFont(20.0f));

        btnReStart.setBackground(Color.white);
        RestartPanel.add(btnReStart, BorderLayout.CENTER);
        add(RestartPanel);

        //움직위기 위한 패널
        movePanel = new MovePanel_2048(this);
        movePanel.setBounds(0,0,500,650);
        add(movePanel);
    } // View_2048()

    private class MenuMouseOver implements MouseListener
    {

        @Override
        public void mouseClicked(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseExited(MouseEvent e) {
            // TODO Auto-generated method stub

        }
        // 마우스가 눌렸을 때
        @Override
        public void mousePressed(MouseEvent e) {
            // TODO Auto-generated method stub


        }
        // 마우스를 땠을 때
        @Override
        public void mouseReleased(MouseEvent e) {


        }
    }

    public void addReStartButtonListener(ActionListener listener) {

        btnReStart.addActionListener(listener);
    }
}