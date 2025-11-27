package si.um.feri.jee.sample.Service;

import jakarta.ejb.Stateless;
import si.um.feri.jee.sample.dao.interfaces.UserDAO;
import si.um.feri.jee.sample.vao.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Optional;


@Stateless
public class UserService {

    @Inject
    private UserDAO userDAO; // CDI za UserDAO

    public boolean isValidEmail(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }

    public boolean hasEnoughBalance(User user, double amount) {
        return user.getBalance() >= amount;
    }

    public boolean isCarCompatible(String carType, String chargerType) {
        return carType != null && carType.equalsIgnoreCase(chargerType);
    }

    public boolean validateUser(User user) {
        return user != null && isValidEmail(user.getEmail()) && user.getBalance() >= 0 && user.getId() != null && !user.getId().trim().isEmpty();
    }

    public void addUser(User user) {
        if (!validateUser(user)) throw new IllegalArgumentException("Invalid user data.");
        if (!user.getId().matches("\\d+")) throw new IllegalArgumentException("ID mora vsebovati samo pozitivne številke.");
        if (user.getName() == null || user.getName().trim().isEmpty()) throw new IllegalArgumentException("Ime ne sme biti prazno.");
        if (!user.getName().chars().anyMatch(Character::isLetter)) throw new IllegalArgumentException("Ime mora vsebovati vsaj eno črko.");
        if (user.getCarType() == null || user.getCarType().trim().isEmpty()) throw new IllegalArgumentException("Vrsta vozila ne sme biti prazna.");
        if (userDAO.getUserByEmail(user.getEmail()).isPresent()) throw new IllegalArgumentException("User with email " + user.getEmail() + " already exists.");
        userDAO.addUser(user);
    }

    public Optional<Optional<User>> getUserById(String id) {
        if (id == null || id.trim().isEmpty()) throw new IllegalArgumentException("ID ne sme biti prazen.");
        return Optional.ofNullable(userDAO.getUserById(id));
    }

    public User getUserByEmail(String email) {
        if (email == null || email.trim().isEmpty()) throw new IllegalArgumentException("Email ne sme biti prazen.");
        return userDAO.getUserByEmail(email).orElseThrow(() -> new IllegalArgumentException("User with email " + email + " not found."));
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public void updateUser(User user) {
        if (!validateUser(user)) throw new IllegalArgumentException("Invalid user data.");
        if (!userDAO.getUserByEmail(user.getEmail()).isPresent()) throw new IllegalArgumentException("User with email " + user.getEmail() + " not found.");
        userDAO.updateUser(user);
    }

    public void deleteUser(String email) {
        if (email == null || email.trim().isEmpty()) throw new IllegalArgumentException("Email ne sme biti prazen.");
        if (!userDAO.getUserByEmail(email).isPresent()) throw new IllegalArgumentException("User with email " + email + " not found.");
        userDAO.deleteUser(email);
    }
}
