import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import java.util.Arrays;
import java.util.Random;
import java.util.random.*;

public class MovePanel_2048 extends JPanel {
    private View_2048 view;
    private MousetHandler MH;
    private Data_2048 data;
    public MovePanel_2048(View_2048 v)
    {
        view = v;
        data = new Data_2048();
        MH = new MousetHandler();
        addMouseListener(MH);
        addMouseMotionListener(MH);

    }
    //게임을 시작하기 위한 메서드
    public void Start()
    {	//1. 현재 점수, 최고 점수, smallBox 초기화
        //data.bestScore=0;
        data.nowScore=0;
        for(int i=0;i<4;i++)
        {
            for(int j=0;j<4;j++)
            {
                data.smallBoxnumber[j][i]=0;
                view.smallBoxNumbertxt[j][i].setText(String.valueOf(data.smallBoxnumber[j][i]));
            }
        }
        //2. smallBox 배열에 랜덤으로 2 or 4 2개 배치
        for(int i=0;i<2;i++)
        {
            CreateSmallBox();

        }
        //3.생성된 smallBox에 대한 점수 반영
        ChangeNowScore();
        ChangeBestScore();

    }
    //현재 점수를 변환하는 코드
    public void ChangeNowScore()
    {	int result =0;
        for(int i=0;i<4;i++)
        {
            for(int j=0;j<4;j++)
            {
                result+=data.smallBoxnumber[i][j];
            }}
        data.nowScore = result;
        view.nowScorenumbertxt.setText(String.valueOf(data.nowScore));

    }
    //최고 점수를 변환하는 코드
    public void ChangeBestScore()
    {	if(data.nowScore>data.bestScore)
    {data.bestScore = data.nowScore;
        view.bestScorenumbertxt.setText(String.valueOf(data.bestScore));}


    }
    //박스의 값을 변경하는 코드
    public void ChangeSmallBox(int a, int b)
    {
        view.smallBoxNumbertxt[a][b].setText(String.valueOf(data.smallBoxnumber[a][b]));

    }

    //박스의 배열 중 0의 값을 갖는 랜덤한 곳에 2 or 4의 값을 생성하는 코드
    public void CreateSmallBox()
    {	//랜검 값 2개
        int num1,num2;
        // 빈공간이 있는지를 검사하기 위한 변수
        int nullBox=0;

        //모든 작은 박스의 크기를 검사
        for(int i=0;i<4;i++)
        {for(int j=0;j<4;j++)
        {//해당 값이 0이라면 nullBox 1증가
            if(data.smallBoxnumber[i][j]==0)
            {nullBox++;}}}

        //nullBox가 0이 아니라면, smallBoxnumber에 하나라도 빈곳이 존재한다면
        while(nullBox!=0)
        {

            //0이 맞을 때까지 반복하며 랜덤 값을 뽑아줌.
            num1 =(int)(Math.random()*4);
            num2 = (int)(Math.random()*4);

            //smallBoxnumber에 들어가 있는 값이 0이면 값 입력후 탈출
            if(data.smallBoxnumber[num1][num2]==0)
            {	//만약 랜덤 값이 0이라면 2 대입
                if((int)(Math.random()*2)==0)
                {
                    data.smallBoxnumber[num1][num2]=2;
                }
                else//만약 랜덤 값이 1이라면 4대입
                {
                    data.smallBoxnumber[num1][num2]=4;
                }
                ChangeSmallBox(num1,num2);
                break;
            }
        }

    }

    //박스 이동
    public void moveSmallBox(int Direction)
    {

        switch (Direction) {
            case 0: //아래
                //System.out.println("아래 실행");
                for (int i = 0; i < 4; i++) {
                    for (int j = 2; j >= 0; j--) {  //0이 아닌 칸까지 이동
                        int index = j;
                        for (int k = j+1; k < 4; k++) {
                            if (data.smallBoxnumber[k][i] != 0) {break;}
                            index = k;
                        }
                        if (index != j) {
                            data.smallBoxnumber[index][i] = data.smallBoxnumber[j][i];
                            data.smallBoxnumber[j][i] = 0;
                        }
                    }

                    for (int j = 2; j >= 0; j--) {  //한 라인의 이동이 끝난 후 옆 칸과 합치기 시작
                        if (data.smallBoxnumber[j+1][i] == 0) {
                            data.smallBoxnumber[j+1][i] = data.smallBoxnumber[j][i];
                            data.smallBoxnumber[j][i] = 0;
                        }
                        else if (data.smallBoxnumber[j][i] == data.smallBoxnumber[j+1][i]) {
                            data.smallBoxnumber[j+1][i] *= 2;
                            data.smallBoxnumber[j][i] = 0;
                        }
                        view.smallBoxNumbertxt[j][i].setText(String.valueOf(data.smallBoxnumber[j][i]));
                        view.smallBoxNumbertxt[j+1][i].setText(String.valueOf(data.smallBoxnumber[j+1][i]));

                    }
                }

                break;
            case 1:  //위
                //System.out.println("위 실행");
                for (int i = 0; i < 4; i++) {
                    for (int j = 1; j < 4; j++) {
                        int index = j;
                        for (int k = j-1; k >= 0; k--) {
                            if (data.smallBoxnumber[k][i] != 0) {break;}
                            index = k;
                        }
                        if (index != j) {
                            data.smallBoxnumber[index][i] = data.smallBoxnumber[j][i];
                            data.smallBoxnumber[j][i] = 0;
                        }
                    }

                    for (int j = 1; j < 4; j++) {
                        if (data.smallBoxnumber[j-1][i] == 0) {
                            data.smallBoxnumber[j-1][i] = data.smallBoxnumber[j][i];
                            data.smallBoxnumber[j][i] = 0;
                        }
                        else if (data.smallBoxnumber[j][i] == data.smallBoxnumber[j-1][i]) {
                            data.smallBoxnumber[j-1][i] *= 2;
                            data.smallBoxnumber[j][i] = 0;
                        }
                        view.smallBoxNumbertxt[j][i].setText(String.valueOf(data.smallBoxnumber[j][i]));
                        view.smallBoxNumbertxt[j-1][i].setText(String.valueOf(data.smallBoxnumber[j-1][i]));
                    }
                }
                break;
            case 2:  //오른쪽
                //System.out.println("오른쪽 실행");
                for (int i = 0; i < 4; i++) {
                    for (int j = 2; j >= 0; j--) {
                        int index = j;
                        for (int k = j+1; k < 4; k++) {
                            if (data.smallBoxnumber[i][k] != 0) {break;}
                            index = k;
                        }
                        if (index != j) {
                            data.smallBoxnumber[i][index] = data.smallBoxnumber[i][j];
                            data.smallBoxnumber[i][j] = 0;
                        }
                    }

                    for (int j = 2; j >= 0; j--) {
                        if (data.smallBoxnumber[i][j+1] == 0) {
                            data.smallBoxnumber[i][j+1] = data.smallBoxnumber[i][j];
                            data.smallBoxnumber[i][j] = 0;
                        }
                        else if (data.smallBoxnumber[i][j] == data.smallBoxnumber[i][j+1]) {
                            data.smallBoxnumber[i][j+1] *= 2;
                            data.smallBoxnumber[i][j] = 0;
                        }
                        view.smallBoxNumbertxt[i][j].setText(String.valueOf(data.smallBoxnumber[i][j]));
                        view.smallBoxNumbertxt[i][j+1].setText(String.valueOf(data.smallBoxnumber[i][j+1]));
                    }
                }
                break;
            case 3:  //왼쪽
                //System.out.println("왼쪽 실행");
                for (int i = 0; i < 4; i++) {
                    for (int j = 1; j < 4; j++) {
                        int index = j;
                        for (int k = j-1; k >= 0; k--) {
                            if (data.smallBoxnumber[i][k] != 0) {break;}
                            index = k;
                        }
                        if (index != j) {
                            data.smallBoxnumber[i][index] = data.smallBoxnumber[i][j];
                            data.smallBoxnumber[i][j] = 0;
                        }
                    }

                    for (int j = 1; j < 4; j++) {
                        if (data.smallBoxnumber[i][j-1] == 0) {
                            data.smallBoxnumber[i][j-1] = data.smallBoxnumber[i][j];
                            data.smallBoxnumber[i][j] = 0;
                        }
                        else if (data.smallBoxnumber[i][j] == data.smallBoxnumber[i][j-1]) {
                            data.smallBoxnumber[i][j-1] *= 2;
                            data.smallBoxnumber[i][j] = 0;
                        }

                        view.smallBoxNumbertxt[i][j].setText(String.valueOf(data.smallBoxnumber[i][j]));
                        view.smallBoxNumbertxt[i][j-1].setText(String.valueOf(data.smallBoxnumber[i][j-1]));
                    }
                }
                break;
        }

    }



    //마우스 클릭을 담당할 클래스
    class MousetHandler implements MouseListener, MouseMotionListener
    {

        @Override
        public void mouseClicked(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mousePressed(MouseEvent e) {
            // TODO Auto-generated method stub
            //클릭 시 시작점에 현재 마우스 위치 반환
            data.pt1 = e.getPoint();
            //System.out.println(e.getPoint());
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // TODO Auto-generated method stub

            //땠을 시 마지막점에 현재 마우스 위치 반환
            data.pt2 = e.getPoint();


            if(Math.abs(data.pt2.x - data.pt1.x) > Math.abs(data.pt2.y - data.pt1.y))
            {
                //좌우로 움직였을 경우

                if(data.pt1.x<data.pt2.x)
                {
                    //오른쪽으로 이동
                    moveSmallBox(2);
                }
                else
                {
                    //왼쪽으로 이동
                    moveSmallBox(3);
                }

            }
            else if(Math.abs(data.pt2.x - data.pt1.x) < Math.abs(data.pt2.y - data.pt1.y))
            {
                //상하로 움직었을 경우
                if(data.pt1.y<data.pt2.y)
                {
                    //아래쪽으로 이동
                    moveSmallBox(0);
                }
                else
                {
                    //위쪽으로 이동
                    moveSmallBox(1);
                }
            }
            //빈 박스 생성
            CreateSmallBox();
            //현재 점수 반영
            ChangeNowScore();
            //최고 점수 반영
            ChangeBestScore();

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseExited(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseDragged(MouseEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            // TODO Auto-generated method stub

        }
    }



}
