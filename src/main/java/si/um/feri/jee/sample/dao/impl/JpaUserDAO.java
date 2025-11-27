package si.um.feri.jee.sample.dao.impl;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import si.um.feri.jee.sample.dao.interfaces.UserDAO;
import si.um.feri.jee.sample.vao.User;

import java.util.List;
import java.util.Optional;

@Stateless
public class JpaUserDAO implements UserDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void addUser(User user) {
        em.persist(user);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        List<User> results = em.createQuery(
                        "SELECT u FROM User u WHERE u.email = :email", User.class)
                .setParameter("email", email)
                .getResultList();

        return results.stream().findFirst();
    }

    @Override
    public Optional<User> getUserById(String id) {
        return Optional.ofNullable(em.find(User.class, id));
    }

    @Override
    public void updateUser(User updatedUser) {
        em.merge(updatedUser);
    }

    @Override
    public void deleteUser(String email) {
        Optional<User> userOpt = getUserByEmail(email);
        userOpt.ifPresent(user -> em.remove(em.contains(user) ? user : em.merge(user)));
    }

    @Override
    public List<User> getAllUsers() {
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }
}
