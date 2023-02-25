import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;
import static java.util.logging.Level.INFO;
import static java.util.logging.Level.WARNING;
public class Controller_Omok {
    public MainPanel_Omok mainPanel;
    public BtnListener btnListener;
    public Select selectListener;
    Socket socket;
    ReceiveThread receiveThread;
    OutputStream outMsg;
    Logger logger;

    public Controller_Omok(MainPanel_Omok mainPanel) {
        this.mainPanel = mainPanel;
        this.btnListener = new BtnListener();
        this.selectListener = new Select();
        logger = Logger.getLogger(this.getClass().getName());
    }

    public void connect_server() {
        try {
            socket = new Socket("127.0.0.1", 8888);
            logger.log(INFO, "[Client]Server Connected!!");
            outMsg = socket.getOutputStream();
            String receive = Data_Omok.getId() + ">Connect";
            outMsg.write(receive.getBytes(StandardCharsets.UTF_8));
            outMsg.flush();
            receiveThread = new ReceiveThread();
            receiveThread.setSocket(socket);
            receiveThread.start();
            this.btnListener = new BtnListener();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            logger.log(WARNING, "[UI]connectServer() Exception");
            e.printStackTrace();
        }
    }

    private void send_msg(String msg) {
        System.out.println("메시지: "+msg);
        try {
            msg = Data_Omok.getId() + ">" + msg;
            outMsg.write(msg.getBytes());
            outMsg.flush();
            if (msg.equals(Data_Omok.getId() + ">exit")) {
                socket.close();
                outMsg.close();
            }
            System.out.println("전송 성공");
        } catch (IOException e) {
            System.out.println("전송 실패");
            e.printStackTrace();
        }
    }

    private class ReceiveThread extends Thread{

        Socket socket;

        @Override
        public void run() {
            super.run();

            try {
                InputStream is = socket.getInputStream();

                while (true) {
                    byte[] data = new byte[128];
                    int n = is.read(data);

                    final String msg_from_s = new String(data, 0, n);
                    System.out.println("메세지 받음 "+msg_from_s);
                    String[] split = msg_from_s.split(">");
                    switch (split[0]) {
                        case "Color" : {
                            System.out.println("Color 실행");
                            Data_Omok.myColor = Integer.parseInt(split[1]);
                            mainPanel.roomview.draw.init();
                            break;
                        }
                        case "Start" : {
                            System.out.println("게임 시작");
                            mainPanel.roomview.decision_btn.setText("두기");
                            break;
                        }
                        case "ID" : {
                            System.out.println("이름 불러오기");
                            mainPanel.roomview.opponent_id.setText(split[1]);
                            break;
                        }
                        case "Put" : {
                            System.out.println("상대가 둠");
                            int x = Integer.parseInt(split[1]);
                            int y = Integer.parseInt(split[2]);
                            mainPanel.roomview.draw.setBoard(x, y);
                            mainPanel.roomview.draw.my_turn = true;
                            break;
                        }
                        case "Win" : {
                            JOptionPane.showMessageDialog(mainPanel.roomview, "승리");
                            mainPanel.roomview.decision_btn.setText("재대결");
                            break;
                        }
                        case "Lose" : {
                            JOptionPane.showMessageDialog(mainPanel.roomview, "패배");
                            mainPanel.roomview.decision_btn.setText("재대결");
                            break;
                        }
                        case "Created" : {
                            System.out.println("created 실행");
                            mainPanel.roomview = new RoomView_Omok();
                            mainPanel.roomview.addButtonListener(btnListener);
                            break;
                        }
                        case "Overlap" : {
                            System.out.println("Overlap 실행");
                            JOptionPane.showMessageDialog(null, "중복된 이름");
                            break;
                        }
                        case "Full" : {
                            System.out.println("Full 실행");
                            JOptionPane.showMessageDialog(null, "자리 없음");
                            break;
                        }
                        case "RoomInfo" : {
                            System.out.println("RoomInfo 실행");
                            mainPanel.room_model = new DefaultListModel<>();
                            if (split.length != 1) {
                                String[] info = split[1].split("\\$");
                                for (int i = 0; i < split.length; i+=2) {
                                    String text = info[i] + "("+info[i+1]+"/2)";
                                    mainPanel.room_model.addElement(text);
                                }
                            }

                            mainPanel.room_list.setModel(mainPanel.room_model);
                            mainPanel.room_list.addListSelectionListener(selectListener);
                            break;
                        }

                        case "ExitRoom" : {
                            System.out.println("ExitRoom 실행");
                            RoomView_Omok room = mainPanel.roomview;
                            room.decision_btn.setText("준비");
                            room.opponent_id.setText("없음");
                            break;
                        }
                        case "Accept" : {
                            System.out.println("Accept 실행");
                            mainPanel.roomview = new RoomView_Omok();
                            mainPanel.roomview.addButtonListener(btnListener);
                            mainPanel.roomview.opponent_id.setText(split[1]);
                            break;
                        }
                        case "Enter" : {
                            System.out.println("Enter 실행");
                            mainPanel.roomview.opponent_id.setText(split[1]);
                            mainPanel.roomview.decision_btn.setText("준비");
                            break;
                        }

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public void setSocket(Socket socket) {
            this.socket = socket;
        }
    }

    private class BtnListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Object obj = e.getSource();

            if (obj == mainPanel.croom_btn) {
                System.out.println("방 생성");
                String name = JOptionPane.showInputDialog("방제목");
                send_msg("Create>"+name);
            } else if (obj == mainPanel.back_btn) {
                System.out.println("뒤로");
                send_msg("Exit");
                receiveThread.interrupt();
                System.exit(0);

            } else if (obj == mainPanel.roomview.decision_btn) {
                System.out.println("결정 버튼");
                String btn_txt = mainPanel.roomview.decision_btn.getText();
                if (btn_txt.equals("준비") || btn_txt.equals("재대결")) {
                    send_msg("Ready");
                    mainPanel.roomview.decision_btn.setText("준비 취소");
                } else if (btn_txt.equals("두기")){
                    mainPanel.roomview.draw.my_turn = false;
                    int x = mainPanel.roomview.draw.tmp_location[0];
                    int y = mainPanel.roomview.draw.tmp_location[1];
                    mainPanel.roomview.draw.clear_tmp();
                    send_msg("Put>"+x+">"+y);
                    if (mainPanel.roomview.draw.check5(x,y)) {
                        //System.out.println("-----승리-----");
                        send_msg("Win");
                        JOptionPane.showMessageDialog(mainPanel.roomview, "승리");
                        mainPanel.roomview.decision_btn.setText("재대결");
                    }
                }
            } else if (obj == mainPanel.roomview.surrender_btn) {
                System.out.println("항복 버튼");
                send_msg("Surrender");
                mainPanel.roomview.decision_btn.setText("준비");
            } else if (obj == mainPanel.roomview.exit_btn) {
                System.out.println("나가기 버튼");
                send_msg("ExitRoom");
                mainPanel.roomview.dispose();
                send_msg("RequestRoomInfo");
            }

        }
    }

    private class Select implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (mainPanel.room_list.getSelectedValue() != null) {
                int answer = JOptionPane.showConfirmDialog(null, "입장하시겠습니까?", mainPanel.room_list.getSelectedValue(), JOptionPane.OK_CANCEL_OPTION);
                if (answer == 0) {
                    String title = mainPanel.room_list.getSelectedValue();
                    String[] split = title.split("\\(");
                    String msg = "Join>" + split[0];
                    send_msg(msg);
                }
            }
        }
    }

}
