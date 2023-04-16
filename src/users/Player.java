package users;

public class Player extends User{

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
}
