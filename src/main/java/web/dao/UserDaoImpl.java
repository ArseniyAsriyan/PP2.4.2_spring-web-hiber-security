package web.dao;

import org.springframework.stereotype.Repository;
import web.model.User;
import javax.persistence.*;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void saveUser(User user) {
        em.persist(user);
    }

    @Override
    public void update(long id, User user) {
        User userForUpdate = findById(id);
        userForUpdate.setName(user.getName());
        userForUpdate.setSurname(user.getSurname());
        userForUpdate.setAge(user.getAge());
    }

    @Override
    public List<User> findAll() {
        String hql = "from User";
        Query query = em.createQuery(hql, User.class);
        return query.getResultList();
    }

    @Override
    public void deleteById(long id) {
        em.remove(findById(id));
    }

    @Override
    public User findById(long id) {
        return em.find(User.class, id);
    }
}