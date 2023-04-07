package users;

public class Admin extends User{
    @Override
    public boolean permisosAdmin() {
        return true;
    }
}
