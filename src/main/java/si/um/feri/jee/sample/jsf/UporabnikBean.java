package si.um.feri.jee.sample.jsf;

import jakarta.inject.Inject;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import si.um.feri.jee.sample.Service.UserService;
import si.um.feri.jee.sample.vao.User;

import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class UporabnikBean implements Serializable {

    private String id;
    private String email;
    private String name;
    private double balance;
    private String carType;
    private User selectedUser;

    @Inject
    private UserService userService;

    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    public void addUser() {
        User user = new User(id, email, name, balance, carType);
        userService.addUser(user);
        // Clear the form after adding
        id = "";
        email = "";
        name = "";
        balance = 0.0;
        carType = "";
    }

    public void updateUser() {
        if (selectedUser != null) {
            userService.updateUser(selectedUser);
            // Clear the selected user after update
            selectedUser = null;
        }
    }

    public void deleteUser(String email) {
        userService.deleteUser(email);
    }

    // Getter and setter for selectedUser
    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
        // When a user is selected, populate the form fields
        if (selectedUser != null) {
            this.id = selectedUser.getId();
            this.email = selectedUser.getEmail();
            this.name = selectedUser.getName();
            this.balance = selectedUser.getBalance();
            this.carType = selectedUser.getCarType();
        }
    }

    // Other getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    public String getCarType() { return carType; }
    public void setCarType(String carType) { this.carType = carType; }
}