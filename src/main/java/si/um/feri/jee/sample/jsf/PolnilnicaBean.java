package si.um.feri.jee.sample.jsf;

import si.um.feri.jee.sample.vao.Polnilnica;
import si.um.feri.jee.sample.Service.PolnilnicaService;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class PolnilnicaBean implements Serializable {

    private String id;
    private String lokacija;
    private boolean available;
    private double hitrostPolnjenja;
    private String regija;
    private String email;
    private Polnilnica selectedPolnilnica;

    @Inject
    private PolnilnicaService polnilnicaService;
    private void clearForm() {
        id = "";
        lokacija = "";
        available = false;
        hitrostPolnjenja = 0.0;
        regija = "";
        email = "";
    }
    public void dodajPolnilnico() {
        Polnilnica novaPolnilnica = new Polnilnica(id, lokacija, available, hitrostPolnjenja, regija, email);
        polnilnicaService.addChargingStation(novaPolnilnica);
        clearForm();

    }

    public void updatePolnilnico() {
        if (selectedPolnilnica != null) {
            polnilnicaService.updateChargingStation(
                    selectedPolnilnica.getId(),
                    selectedPolnilnica.getLokacija(),
                    selectedPolnilnica.isAvailable()
            );
            selectedPolnilnica = null;
        }
    }
    public void deletePolnilnico(String id) {
        polnilnicaService.deleteChargingStation(id);
    }



    public List<Polnilnica> getPolnilnice() {
        return polnilnicaService.getAllChargingStations();
    }

    public void izpisPolnilnicVKonzolo() {
        List<Polnilnica> polnilnice = polnilnicaService.getAllChargingStations();
        System.out.println("===== Izpis vseh polnilnih postaj =====");
        for (Polnilnica p : polnilnice) {
            System.out.println(p);
        }
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getLokacija() { return lokacija; }
    public void setLokacija(String lokacija) { this.lokacija = lokacija; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
    public double getHitrostPolnjenja() { return hitrostPolnjenja; }
    public void setHitrostPolnjenja(double hitrostPolnjenja) { this.hitrostPolnjenja = hitrostPolnjenja; }
    public String getRegija() { return regija; }
    public void setRegija(String regija) { this.regija = regija; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Polnilnica getSelectedPolnilnica() { return selectedPolnilnica; }
    public void setSelectedPolnilnica(Polnilnica selectedPolnilnica) {
        this.selectedPolnilnica = selectedPolnilnica;
        if (selectedPolnilnica != null) {
            // Only populate fields that will be edited
            this.id = selectedPolnilnica.getId();
            this.lokacija = selectedPolnilnica.getLokacija();
            this.available = selectedPolnilnica.isAvailable();
        }
    }
}