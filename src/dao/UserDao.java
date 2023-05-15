package dao;

import users.User;

public interface UserDao {
    void create(users.User usuario);

    void update(users.User usuario);

    void delete(users.User usuario);

    users.User findByName(String name);

    users.User read(User usuario);

    java.util.List findAll();
}
