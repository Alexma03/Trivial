package users;

public class Player extends User{
    @Override
    public boolean permisosAdmin() {
        return false;
    }
}
