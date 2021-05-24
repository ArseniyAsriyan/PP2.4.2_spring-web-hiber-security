package web.service;

import web.model.User;

import javax.management.relation.Role;
import java.util.List;

public interface UserService {
    void saveUser(User user);
    List<User> findAll();
    User findById(long id);
    void update(long id, User user);
    void deleteById(long id);
    User findByLogin(String login);

}
