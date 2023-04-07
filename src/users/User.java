package users;

public abstract class User {
    protected String name;
    protected String pass;

    public boolean cambiarPass(String pass){
        if (pass.length() < 8) {
            return false;
        } else {
            this.pass = pass;
            return true;
        }
    }

    public boolean compruebaPass(String pass){
        return this.pass.equals(pass);
    }

    public abstract boolean permisosAdmin();
}
