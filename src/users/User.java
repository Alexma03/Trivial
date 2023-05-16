package users;

import jdk.jfr.Name;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="users") // Nombre de la tabla en la base de datos
@NamedQuery(name = "User.findByName", query = "SELECT u FROM User u WHERE u.name = :name") // Consulta JPQL para buscar un usuario por su nombre
public abstract class User implements Comparable<User>, Serializable {

    @Id // Indica que es la clave primaria
    @Column(name="name")
    protected String name;
    @Column(name="pass")
    protected String pass;

    public User(String name, String pass) {
        this.name = name;
        this.pass = pass;
    }

    public User() {} // Constructor vacío para que funcione JPA

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
