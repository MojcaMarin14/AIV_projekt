package si.um.feri.jee.sample.dao.interfaces;

import jakarta.ejb.Local;

import si.um.feri.jee.sample.vao.User;

import java.util.List;
import java.util.Optional;

@Local
public interface UserDAO {
    void addUser(User user);
    Optional<User> getUserByEmail(String email);  // Spremenjeno iz User na Optional<User>
    void updateUser(User user);
    Optional<User> getUserById(String id);
    void deleteUser(String email);
    List<User> getAllUsers();
}