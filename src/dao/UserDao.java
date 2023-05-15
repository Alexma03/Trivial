package dao;

import users.User;

import java.util.List;


public interface UserDao {
    User read(String username);
    void create(User user);
    void update(User user);
    void delete(User name);
    List<User> findAll();
    List<User> findByName(String name);
}
