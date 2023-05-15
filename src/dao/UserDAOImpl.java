package dao;

import users.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class UserDAOImpl implements UserDao {
    public final EntityManager em;

    public UserDAOImpl() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("db/trivial.odb");
        this.em = emf.createEntityManager();
    }

    public void create(User usuario) {
        em.getTransaction().begin();
        em.persist(usuario);
        em.getTransaction().commit();
        em.close();
    }

    public void update(User usuario) {
        em.getTransaction().begin();
        em.merge(usuario);
        em.getTransaction().commit();
        em.close();
    }

    public void delete(User usuario) {
        em.getTransaction().begin();
        em.remove(usuario);
        em.getTransaction().commit();
        em.close();
    }

    public User read(User usuario) {
        return em.find(User.class, usuario);
    }

    public User findByName(String name) {
        return em.find(User.class, name);
    }

    public List<User> findAll() {
        return em.createQuery("SELECT u FROM User u").getResultList();
    }
}