package web.dao;

import web.model.Role;
import web.model.User;

import java.util.List;
import java.util.Set;

public interface RoleDao {
    Set<Role> findAll();
    void saveRole(Role role);
    void update(Role role);
    void deleteRole(Role role);
    Role findByRole(String role);
}
