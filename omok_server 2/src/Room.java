import java.util.ArrayList;

public class Room {
    ArrayList<User> users;
    String title;
    boolean[] ready;
    public Room(String title, User user) {
        users = new ArrayList<>();
        users.add(user);
        ready = new boolean[]{false,false};
        this.title = title;
    }

    public Room(User user1, User user2) {
        users = new ArrayList<>();
        ready = new boolean[]{false, false};
        users.add(user1);
        users.add(user2);
    }
}
