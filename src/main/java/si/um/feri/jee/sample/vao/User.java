package si.um.feri.jee.sample.vao;

import jakarta.persistence.*;

@Entity
public class User {

    @Id
    private String id;

    private String email;
    private String name;
    private double balance;
    private String carType;

    // Prazni konstruktor je potreben za JPA
    public User() {}

    public User(String id, String email, String name, double balance, String carType) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.balance = balance;
        this.carType = carType;
    }

    // Get & Set metode
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

    @Override
    public String toString() {
        return "User{id=" + id + ", email=" + email + ", name=" + name + ", balance=" + balance + ", carType=" + carType + "}";
    }
}
