package si.um.feri.jee.sample.Service;

import jakarta.ejb.Stateless;
import si.um.feri.jee.sample.dao.interfaces.PolnilnicaDAO;
import si.um.feri.jee.sample.dao.interfaces.PonudnikDAO;
import si.um.feri.jee.sample.vao.Ponudnik;
import si.um.feri.jee.sample.vao.Polnilnica;
import si.um.feri.jee.sample.Iterators.VsePolnilnicePoAbecediIterator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


@Stateless
public class PonudnikService {

    @Inject
    private PonudnikDAO ponudnikDAO; // Dependency Injection

    @Inject
    private PolnilnicaDAO polnilnicaDAO; // DAO za polnilnice

    public void addProvider(Ponudnik ponudnik) {
        if (ponudnik == null) throw new IllegalArgumentException("Ponudnik ne sme biti null.");
        if (ponudnik.getIme() == null || ponudnik.getIme().trim().isEmpty()) throw new IllegalArgumentException("Ponudnikovo ime ne sme biti prazno.");
        if (!ponudnik.getIme().chars().anyMatch(Character::isLetter)) throw new IllegalArgumentException("Ime mora vsebovati vsaj eno črko.");
        if (ponudnik.getLokacija() == null || ponudnik.getLokacija().trim().isEmpty()) throw new IllegalArgumentException("Lokacija ne sme biti prazna.");
        if (ponudnik.getEmail() == null || ponudnik.getEmail().trim().isEmpty()) throw new IllegalArgumentException("Email ne sme biti prazen.");
        if (ponudnik.getId() < 0) throw new IllegalArgumentException("Ponudnikov ID ne sme biti negativen.");
        if (ponudnikDAO.findById(ponudnik.getId()) != null) throw new IllegalStateException("Ponudnik z ID " + ponudnik.getId() + " že obstaja.");

        ponudnikDAO.add(ponudnik);
    }

    public Ponudnik getProviderById(int id) {
        if (id < 0) throw new IllegalArgumentException("ID ne sme biti negativen.");
        Ponudnik ponudnik = ponudnikDAO.findById(id);
        if (ponudnik == null) throw new NoSuchElementException("Ponudnik z ID " + id + " ni bil najden.");
        return ponudnik;
    }

    public List<Ponudnik> getAllProviders() {
        return new ArrayList<>(ponudnikDAO.getAll());
    }

    public void updateProvider(int id, String name, String location, String email) {
        Ponudnik ponudnik = getProviderById(id); // Preverimo, ali ponudnik obstaja
        if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Ime ne sme biti prazno.");
        if (!name.chars().anyMatch(Character::isLetter)) throw new IllegalArgumentException("Ime mora vsebovati vsaj eno črko.");
        if (location == null || location.trim().isEmpty()) throw new IllegalArgumentException("Lokacija ne sme biti prazna.");
        if (email == null || email.trim().isEmpty()) throw new IllegalArgumentException("Email ne sme biti prazen.");

        ponudnik.setIme(name);
        ponudnik.setLokacija(location);
        ponudnik.setEmail(email);
        ponudnikDAO.update(ponudnik); // Posodobimo v bazi
    }

    public void deleteProvider(int id) {
        getProviderById(id); // Preverimo, ali obstaja
        ponudnikDAO.delete(id);
    }

    public String findProviderNameByChargingStation(Polnilnica station) {
        if (station == null || station.getId() == null || station.getId().trim().isEmpty()) {
            throw new IllegalArgumentException("Polnilna postaja ali ID ne more biti null/prazen.");
        }

        String ponudnikIme = ponudnikDAO.findProviderByStationId(Integer.parseInt(station.getId()));
        if (ponudnikIme == null) {
            throw new NoSuchElementException("Ni najdenega ponudnika za to polnilno postajo.");
        }
        return ponudnikIme;
    }


    public void addChargingStationToProvider(int providerId, String stationId) {
        Ponudnik ponudnik = getProviderById(providerId);
        Polnilnica station = polnilnicaDAO.getStationById(stationId);

        if (station == null) throw new NoSuchElementException("Polnilna postaja z ID " + stationId + " ne obstaja.");

        ponudnik.addChargingStation(station);
        ponudnikDAO.update(ponudnik);
    }

    public List<Polnilnica> getAllStationsAlphabetically() {
        List<Ponudnik> allProviders = getAllProviders();
        List<Polnilnica> sortedStations = new ArrayList<>();
        VsePolnilnicePoAbecediIterator iterator = new VsePolnilnicePoAbecediIterator(allProviders);
        while (iterator.hasNext()) {
            sortedStations.add(iterator.next());
        }
        return sortedStations;
    }
}
