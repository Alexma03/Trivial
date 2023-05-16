package dao;

import users.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Clase que implementa los métodos CRUD para la clase User
 */
public class UserDAOImpl implements UserDao {
    @PersistenceContext
    public final EntityManager em;

    /**
     * Constructor de la clase, en el que se pone la ruta de la base de datos y se crea el EntityManager
     * para poder acceder a la base de datos y realizar las operaciones CRUD de la clase User
     */
    public UserDAOImpl() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("db/trivial.odb");
        this.em = emf.createEntityManager();
        em.getMetamodel().entity(User.class);
    }

    /**
     * Método que crea un usuario en la base de datos
     *
     * @param usuario usuario a crear
     */
    public void create(User usuario) {
        em.getTransaction().begin(); // Inicia la transacción
        em.persist(usuario); // Almacena el usuario en la base de datos
        em.getTransaction().commit(); // Confirma la transacción
    }

    /**
     * Método que actualiza un usuario en la base de datos
     *
     * @param usuario usuario a actualizar
     */
    public void update(User usuario) {
        em.getTransaction().begin();
        em.merge(usuario); // Actualiza el usuario en la base de datos
        em.getTransaction().commit();
    }

    /**
     * Método que elimina un usuario de la base de datos
     *
     * @param usuario usuario a eliminar
     */
    public void delete(User usuario) {
        em.getTransaction().begin();
        em.remove(usuario); // Elimina el usuario de la base de datos
        em.getTransaction().commit();
    }

    /**
     * Método que lee un usuario de la base de datos
     *
     * @param usuario usuario a leer
     * @return usuario leído
     */
    public User read(User usuario) {
        return em.find(User.class, usuario);
    }

    /**
     * Método que busca un usuario por su nombre
     *
     * @param name nombre del usuario a buscar
     * @return usuario encontrado
     */
    public User findByName(String name) {
        return em.find(User.class, name);
    }

    /**
     * Método que devuelve todos los usuarios de la base de datos
     *
     * @return lista de usuarios
     */
    public List<User> findAll() {
        return em.createQuery("SELECT u FROM User u").getResultList(); // Usa JPQL para buscar todos los usuarios
    }

}