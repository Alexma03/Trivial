package users;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class Admin extends User implements Serializable {

    public Admin(String name, String pass) {
        super(name, pass);
    }

    public Admin() {

    }

    @Override
    public boolean permisosAdmin() {
        return true;
    }

    @Override
    public int compareTo(User o) {
        return 0;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "name='" + name + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }
}
