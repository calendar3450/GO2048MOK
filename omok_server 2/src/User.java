import java.io.*;
import java.net.Socket;

public class User extends Thread{
    Socket socket;
    InputStream is;
    OutputStream os;
    Room my_room;
    String msg;
    String id;
    Boolean Status;
    public void run() {
        try {
            System.out.println("[Server]클라이언트 접속 >" + this.socket.toString());
            is = socket.getInputStream();
            os = socket.getOutputStream();
            Status = true;

            while (Status) {
                byte[] data = new byte[128];
                int n = is.read(data);
                final String msg1 = new String(data, 0, n);
                System.out.println(msg1);
                String[] split = msg1.split(">");
                switch (split[1]) {
                    case "Connect": {
                        int dup = 0;
                        for (User user : Main.userAll) {
                            if (user == this) {}
                            else if (user.id.equals(split[0])) {
                                dup++;
                                break;
                            }
                        }
                        if (dup > 0) {
                            this.os.write("Overlap".getBytes());
                            this.os.flush();
                            Main.userAll.remove(this);
                            break;
                        }
                        Main.userMain.add(this);
                        this.id = split[0];
                        this.os.write(getRoomInfo().getBytes());
                        this.os.flush();
                        break;
                    }
                    case "RequestMainInfo" : {
                        this.os.write(getMainInfo().getBytes());
                        this.os.flush();
                        break;
                    }
                    case "RequestAllInfo" : {
                        this.os.write(getAllInfo().getBytes());
                        this.os.flush();
                    }
                    case "RequestRoomInfo" : {
                        this.os.write(getRoomInfo().getBytes());
                        this.os.flush();
                        break;
                    }
                    case "Create" : {
                        System.out.println("방 생성 시작");
                        String room_title = split[2];
                        int count = 0;
                        for (Room rooms : Main.rooms) {
                            if (rooms.title.equals(room_title)) {
                                count++;
                                break;
                            }
                        }
                        if (count > 0) {
                            this.os.write("Overlap".getBytes());
                            this.os.flush();
                            System.out.println("중복으로 실패");
                            break;
                        }
                        System.out.println("방 생성 성공");

                        Room room = new Room(room_title, this);
                        Main.rooms.add(room);
                        my_room = room;
                        this.os.write("Created".getBytes());
                        this.os.flush();

                        Main.userMain.remove(this);
                        sendMainUsers(getRoomInfo());
                        break;
                    }
                    case "Join" : {
                        System.out.println("join 전송됨");
                        String room_title = split[2];
                        for (Room room : Main.rooms) {
                            if (room.title.equals(room_title)) {
                                if (room.users.size() == 1) {
                                    my_room = room;
                                    my_room.users.add(this);
                                    Main.userMain.remove(this);
                                    String msg = "Accept>"+my_room.users.get(0).id;
                                    this.os.write(msg.getBytes());
                                    this.os.flush();

                                    String msg2 = "ID>"+my_room.users.get(1).id;
                                    my_room.users.get(0).os.write(msg2.getBytes());
                                    my_room.users.get(0).os.flush();
                                    sendMainUsers(getRoomInfo());
                                } else {
                                    String msg = "Full";
                                    this.os.write(msg.getBytes());
                                    this.os.flush();
                                }
                            }
                        }
                        break;
                    }
                    case "Ready": {
                        for (int i = 0; i < my_room.users.size(); i++) {
                            if (my_room.users.get(i).id.equals(split[0])) {
                                my_room.ready[i] = !my_room.ready[i];
                            }
                        }
                        int count = 0;
                        for (int i = 0; i < my_room.ready.length; i++) {
                            if (my_room.ready[i]) count++;
                        }
                        if (count == 2) {
                            sendRoomMembers("Start");
                            my_room.users.get(0).os.write("Color>1".getBytes());
                            my_room.users.get(0).os.flush();
                            my_room.users.get(1).os.write("Color>2".getBytes());
                            my_room.users.get(1).os.flush();
                        }
                        break;
                    }
                    case "Put": {
                        if (my_room.users.get(0).id.equals(split[0])) {
                            msg = "Put>" + split[2] + ">" + split[3];
                            my_room.users.get(1).os.write(msg.getBytes());
                            my_room.users.get(1).os.flush();
                        } else {
                            msg = "Put>" + split[2] + ">" + split[3];
                            my_room.users.get(0).os.write(msg.getBytes());
                            my_room.users.get(0).os.flush();
                        }
                        break;
                    }
                    case "Win": {
                        if (my_room.users.get(0).id.equals(split[0])) {
                            msg = "Lose";
                            my_room.users.get(1).os.write(msg.getBytes());
                            my_room.users.get(1).os.flush();
                        } else {
                            msg = "Lose";
                            my_room.users.get(0).os.write(msg.getBytes());
                            my_room.users.get(0).os.flush();
                        }
                        my_room.ready[0] = false;
                        my_room.ready[1] = false;
                        break;
                    }
                    case "Surrender" : {
                        int index1;
                        int index2;
                        if (my_room.users.get(0).id.equals(split[0])) {
                            index1 = 0;
                            index2 = 1;
                        } else {
                            index1 = 1;
                            index2 = 0;
                        }
                        msg = "Lose";
                        my_room.users.get(index1).os.write(msg.getBytes());
                        my_room.users.get(index1).os.flush();
                        String msg2 = "Win";
                        my_room.users.get(index2).os.write(msg2.getBytes());
                        my_room.users.get(index2).os.flush();
                        break;
                    }
                    case "ExitRoom" : {
                        Main.userMain.add(this);
                        this.my_room.users.remove(this);
                        if (this.my_room.users.size() == 0) {
                            System.out.println("0명");
                            Main.rooms.remove(this.my_room);
                            sendMainUsers(getRoomInfo());
                        }
                        else {
                            this.my_room.users.get(0).os.write("ExitRoom".getBytes());
                            this.my_room.users.get(0).os.flush();
                        }
                        this.my_room = null;
                        sendMainUsers(getMainInfo());
                        break;
                    }
                    case "Exit" : {
                        Main.userAll.remove(this);
                        Main.userMain.remove(this);
                        sendMainUsers(getRoomInfo());
                        this.is.close();
                        this.os.close();
                        this.socket.close();
                        break;
                    }
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getAllInfo() {
        StringBuilder room_infos = new StringBuilder("AllInfo>");
        for (User user : Main.userAll) {
            room_infos.append(user.id).append(">");
        }
        String msg_s = room_infos.toString();
        return msg_s;
    }

    private String getMainInfo() {
        StringBuilder room_infos = new StringBuilder("MainInfo>");
        for (User user : Main.userMain) {
            room_infos.append(user.id).append(">");
        }
        String msg_s = room_infos.toString();
        return msg_s;
    }


    private String getRoomInfo() {
        StringBuilder room_infos = new StringBuilder("RoomInfo>");
        for (Room room : Main.rooms) {
            room_infos.append(room.title).append("$").append(room.users.size()).append(">");
        }
        String msg_s = room_infos.toString();
        return msg_s;
    }

    private void sendRoomMembers(String msg) {
        try {
            for (User user : my_room.users) {
                user.os.write(msg.getBytes());
                user.os.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void sendMainUsers(String msg) {
        System.out.println("전체 전송 "+msg);
        try {
            for (User user : Main.userMain) {
                user.os.write(msg.getBytes());
                user.os.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setSocket(Socket socket) {
        this.socket = socket;
    }



}
