package users;

import java.io.Serializable;

public class Player extends User implements Serializable {

    public Player(String name, String pass) {
        super(name, pass);
    }
    @Override
    public boolean permisosAdmin() {
        return false;
    }

    @Override
    public int compareTo(User o) {
        return 0;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name = '" + name + '\'' +
                '}';
    }
}
