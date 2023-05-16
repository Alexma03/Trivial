package users;

import java.io.Serializable;

public abstract class User implements Comparable<User>, Serializable {
    protected String name;
    protected String pass;

    public User(String name, String pass) {
        this.name = name;
        this.pass = pass;
    }

    /**
     * Cambia la contraseña del usuario
     * @param pass contraseña nueva
     * @return true si la contraseña es válida y se cambia, false si no es válida
     */
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

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(User o) {
        return name.compareTo(o.getName());
    }

    public String getPass() {
        return pass;
    }
}
