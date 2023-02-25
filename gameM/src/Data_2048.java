import java.awt.Point;

public class Data_2048 {
    //1. 마우스를 눌렀다 땠는 지를 알기 위한 pt1, pt2
    public Point pt1,pt2;
    //2. 작은 박스들(int로 한 이유는 박스의 값을 int로 사용하고, 박스에 값이 있는지를 int를 통해 판단하기 위함)
    public int [][] smallBoxnumber;
    //3, 현재 점수와 최고 점수
    public int nowScore, bestScore;
    public Data_2048()
    {
        pt1 = new Point();
        pt2 = new Point();
        smallBoxnumber = new int [4][4];
        nowScore = 0;
        bestScore = 0;
        //배열 값 0으로 초기화
        for(int i=0;i<4;i++)
        {
            for(int j=0;j<4;j++)
            {
                smallBoxnumber[i][j]=0;
            }
        }

    }
}


