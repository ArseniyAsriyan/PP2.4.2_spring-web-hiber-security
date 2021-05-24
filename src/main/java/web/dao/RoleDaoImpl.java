package web.dao;

import org.springframework.stereotype.Repository;
import web.model.Role;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Role> findAll() {
        String hql = "from Role";
        Query query = em.createQuery(hql, Role.class);
        return query.getResultList();
    }

    @Override
    public void saveRole(Role role) {
        em.persist(role);
    }

    @Override
    public void update(Role role) {
        em.merge(role);

    }

    @Override
    public void deleteRole(Role role) {
        em.remove(em.find(Role.class, role.getId()));
    }

    @Override
    public Role findByRole(String role) {
        return em.find(Role.class, role);
    }
}
