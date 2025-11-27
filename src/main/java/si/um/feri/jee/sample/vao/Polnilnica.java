package si.um.feri.jee.sample.vao;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import si.um.feri.jee.sample.dao.Observers.Observer;
import si.um.feri.jee.sample.dao.Observers.Subject;

@Entity
public class Polnilnica implements Subject {

    @Id
    private String id;

    private String lokacija;
    private boolean isAvailable;
    private double hitrostPolnjenja;
    private String regija;
    private String currentUserEmail;
    private String email;
    private boolean isActive;

    // Povezava z entiteto Ponudnik
    @ManyToOne
    private Ponudnik ponudnik;

    // Seznam opazovalcev ni za bazo, zato označimo kot @Transient
    @Transient
    private List<Observer> observers = new ArrayList<>();

    // ====== KONSTRUKTORJI ======

    public Polnilnica() {
        this.observers = new ArrayList<>();
    }

    public Polnilnica(String id, String lokacija, boolean isAvailable, double hitrostPolnjenja, String regija, Ponudnik ponudnik) {
        this.id = id;
        this.lokacija = lokacija;
        this.isAvailable = isAvailable;
        this.hitrostPolnjenja = hitrostPolnjenja;
        this.regija = regija;
        this.ponudnik = ponudnik;
        this.currentUserEmail = null;
        this.observers = new ArrayList<>();
        this.email = id + "@chargingstation.com";
        this.isActive = true;
    }

    public Polnilnica(String id, String lokacija, boolean isAvailable, double hitrostPolnjenja, String regija, String email) {
        this.id = id;
        this.lokacija = lokacija;
        this.isAvailable = isAvailable;
        this.hitrostPolnjenja = hitrostPolnjenja;
        this.regija = regija;
        this.email = email;
        this.currentUserEmail = null;
        this.observers = new ArrayList<>();
        this.isActive = true;
    }

    // ====== GETTERJI & SETTERJI ======

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getLokacija() { return lokacija; }
    public void setLokacija(String lokacija) { this.lokacija = lokacija; }

    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) {
        if (this.isAvailable != available) {
            this.isAvailable = available;
            notifyObservers();
        }
    }

    public double getHitrostPolnjenja() { return hitrostPolnjenja; }
    public void setHitrostPolnjenja(double hitrostPolnjenja) { this.hitrostPolnjenja = hitrostPolnjenja; }

    public String getRegija() { return regija; }
    public void setRegija(String regija) { this.regija = regija; }

    public String getCurrentUserEmail() { return currentUserEmail; }
    public void setCurrentUserEmail(String currentUserEmail) { this.currentUserEmail = currentUserEmail; }

    public Ponudnik getPonudnik() { return ponudnik; }
    public void setPonudnik(Ponudnik ponudnik) { this.ponudnik = ponudnik; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean isActive) { this.isActive = isActive; }

    // ====== OBSERVER LOGIKA ======

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        String status = isAvailable ? "prosta" : "zasedena";
        String providerName = (ponudnik != null) ? ponudnik.getIme() : "Neznan ponudnik";

        System.out.println("📢 [Obveščanje] Polnilnica " + lokacija + " je zdaj " + status + ".");

        for (Observer observer : observers) {
            observer.update(lokacija, status, currentUserEmail, hitrostPolnjenja, providerName);
        }
    }

    public void startCharging(String userEmail) {
        this.currentUserEmail = userEmail;
        this.setAvailable(false);
    }

    public void stopCharging() {
        this.currentUserEmail = null;
        this.setAvailable(true);
    }

    @Override
    public String toString() {
        return "Polnilnica{" +
                "id='" + id + '\'' +
                ", lokacija='" + lokacija + '\'' +
                ", isAvailable=" + isAvailable +
                ", hitrostPolnjenja=" + hitrostPolnjenja +
                ", regija='" + regija + '\'' +
                ", currentUserEmail='" + currentUserEmail + '\'' +
                '}';
    }
}
