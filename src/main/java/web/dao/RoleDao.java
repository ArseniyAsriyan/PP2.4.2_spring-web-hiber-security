package web.dao;

import web.model.Role;
import web.model.User;

import java.util.List;

public interface RoleDao {
    List<Role> findAll();
    void saveRole(Role role);
    void update(Role role);
    void deleteRole(Role role);
    Role findByRole(String role);
}
