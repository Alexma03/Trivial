package users;

public abstract class User implements Comparable<User> {
    protected String name;
    protected String pass;

    public User(String name, String pass) {
        this.name = name;
        this.pass = pass;
    }

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

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(User o) {
        return name.compareTo(o.getName());
    }
}
