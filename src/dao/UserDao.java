package dao;

import users.User;

/**
 * Interfaz que contiene los métodos CRUD para la clase User
 */
public interface UserDao {
    void create(users.User usuario);

    void update(users.User usuario);

    void delete(users.User usuario);

    users.User findByName(String name);

    users.User read(User usuario);

    java.util.List findAll();
}
