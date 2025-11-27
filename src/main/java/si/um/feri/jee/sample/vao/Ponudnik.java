package si.um.feri.jee.sample.vao;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Ponudnik {

    @Id
    private int id;

    private String ime;
    private String lokacija;
    private String email;

    @OneToMany(mappedBy = "ponudnik", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Polnilnica> polnilnice = new ArrayList<>();

    // Prazen konstruktor za JPA
    public Ponudnik() {}

    public Ponudnik(int id, String ime, String lokacija, String email) {
        this.id = id;
        this.ime = ime;
        this.lokacija = lokacija;
        this.email = email;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getIme() { return ime; }
    public void setIme(String ime) { this.ime = ime; }

    public String getLokacija() { return lokacija; }
    public void setLokacija(String lokacija) { this.lokacija = lokacija; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public List<Polnilnica> getPolnilnice() { return polnilnice; }

    public void addChargingStation(Polnilnica station) {
        polnilnice.add(station);
        station.setPonudnik(this); // Poskrbimo za dvosmerno povezavo
    }

    public void removeChargingStation(Polnilnica station) {
        polnilnice.remove(station);
        station.setPonudnik(null);
    }

    @Override
    public String toString() {
        return String.format("Ponudnik [ID: %d, Ime: %s, Lokacija: %s, Email: %s, Št. polnilnic: %d]",
                id, ime, lokacija, email, polnilnice.size());
    }
}
