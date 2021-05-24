package web.service;

import web.model.Role;

import java.util.List;

public interface RoleService {
    boolean add(Role role);
    Role getById(Long id);
    Role getByName(String name);
    boolean update(Role role);
    List<Role> findAll();
}
