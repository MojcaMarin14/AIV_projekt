package si.um.feri.jee.sample.jsf;

import si.um.feri.jee.sample.vao.Ponudnik;
import si.um.feri.jee.sample.Service.PonudnikService;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class PonudnikBean implements Serializable {

    private int id;
    private String ime;
    private String lokacija;
    private String email;
    private Ponudnik selectedPonudnik;

    @Inject
    private PonudnikService ponudnikService;
    private void clearForm() {
        id = 0;
        ime = "";
        lokacija = "";
        email = "";
    }
    public void addProvider() {
        Ponudnik newProvider = new Ponudnik(id, ime, lokacija, email);
        ponudnikService.addProvider(newProvider);
        clearForm();
    }

    public void updateProvider() {
        if (selectedPonudnik != null) {
            ponudnikService.updateProvider(
                    selectedPonudnik.getId(),
                    selectedPonudnik.getIme(),
                    selectedPonudnik.getLokacija(),
                    selectedPonudnik.getEmail()
            );
            selectedPonudnik = null;
        }
    }

    public void deleteProvider(int id) {
        ponudnikService.deleteProvider(id);
    }



    public List<Ponudnik> getAllProviders() {
        return ponudnikService.getAllProviders();
    }

    public void izpisPonudnikovVKonzolo() {
        List<Ponudnik> ponudniki = ponudnikService.getAllProviders();
        System.out.println("===== Izpis vseh ponudnikov =====");
        for (Ponudnik p : ponudniki) {
            System.out.println(p);
        }
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getIme() { return ime; }
    public void setIme(String ime) { this.ime = ime; }
    public String getLokacija() { return lokacija; }
    public void setLokacija(String lokacija) { this.lokacija = lokacija; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Ponudnik getSelectedPonudnik() { return selectedPonudnik; }
    public void setSelectedPonudnik(Ponudnik selectedPonudnik) {
        this.selectedPonudnik = selectedPonudnik;
        if (selectedPonudnik != null) {
            this.id = selectedPonudnik.getId();
            this.ime = selectedPonudnik.getIme();
            this.lokacija = selectedPonudnik.getLokacija();
            this.email = selectedPonudnik.getEmail();
        }
    }
}