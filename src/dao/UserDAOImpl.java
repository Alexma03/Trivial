package dao;

import users.User;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;



public class UserDAOImpl implements UserDao{

    private static final String PERSISTENCE_UNIT_NAME = "objectdb:./usersTrivial.odb";
    private static EntityManagerFactory factory;

    public User read(String username) {
        EntityManager manager = getEntityManager();
        try {
            return manager.find(User.class, username);
        } finally {
            manager.close();
        }
    }

    public void create(User user) {
        EntityManager manager = getEntityManager();
        EntityTransaction tx = null;
        try {
            tx = manager.getTransaction();
            tx.begin();
            manager.persist(user);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive())
                tx.rollback();
            e.printStackTrace();
        } finally {
            manager.close();
        }
    }

    public void update(User user) {
        EntityManager manager = getEntityManager();
        EntityTransaction tx = null;
        try {
            tx = manager.getTransaction();
            tx.begin();
            manager.merge(user);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive())
                tx.rollback();
            e.printStackTrace();
        } finally {
            manager.close();
        }
    }

    public void delete(User name) {
        EntityManager manager = getEntityManager();
        EntityTransaction tx = null;
        try {
            tx = manager.getTransaction();
            tx.begin();
            User user = read(String.valueOf(name));
            if (user != null)
                manager.remove(user);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive())
                tx.rollback();
            e.printStackTrace();
        } finally {
            manager.close();
        }
    }

    @SuppressWarnings("unchecked")
    public List<User> findAll() {
        EntityManager manager = getEntityManager();
        try {
            Query query = manager.createQuery("SELECT u FROM User u");
            return query.getResultList();
        } finally {
            manager.close();
        }
    }

    @Override
    public List<User> findByName(String name) {
        return null;
    }

    public EntityManager getEntityManager() {
        if (factory == null) {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        }
        return factory.createEntityManager();
    }
}