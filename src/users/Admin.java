package users;

public class Admin extends User{

    public Admin(String name, String pass) {
        super(name, pass);
    }

    @Override
    public boolean permisosAdmin() {
        return true;
    }

    @Override
    public int compareTo(User o) {
        return 0;
    }
}
