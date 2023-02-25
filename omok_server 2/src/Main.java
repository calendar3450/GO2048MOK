import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Main {
    static ServerSocket serverSocket;

    public static ArrayList<User> userAll;
    public static ArrayList<Room> rooms;
    public static ArrayList<User> userMain;

    public static void main(String[] args) {

        userMain = new ArrayList<User>();
        userAll = new ArrayList<User>();
        rooms = new ArrayList<Room>();

        try {
            serverSocket = new ServerSocket( 8888);

            while (true) {
                Socket socket = serverSocket.accept();
                User user = new User();
                user.setSocket(socket);
                userAll.add(user);
                user.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}