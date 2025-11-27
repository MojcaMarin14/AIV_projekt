package si.um.feri.jee.sample.Service;

import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import si.um.feri.jee.sample.dao.interfaces.PolnilnicaDAO;
import si.um.feri.jee.sample.vao.Polnilnica;
import si.um.feri.jee.sample.vao.Ponudnik;
import si.um.feri.jee.sample.Iterators.AktivnaPolnilnicaIterator;
import si.um.feri.jee.sample.Iterators.PolnilnicaPoHitrostiIterator;
import si.um.feri.jee.sample.Iterators.PolnilnicaPoRegijiIterator;
import si.um.feri.jee.sample.Iterators.VsePolnilnicePoAbecediIterator;
import si.um.feri.jee.sample.vao.User;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Stateless
public class PolnilnicaService implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private PolnilnicaDAO chargingStationDAO; // Dependency Injection

    public void addChargingStation(Polnilnica station) {
        if (station == null) throw new IllegalArgumentException("Polnilna postaja ne sme biti null.");
        if (station.getId() == null || station.getId().trim().isEmpty()) throw new IllegalArgumentException("ID polnilne postaje ne sme biti prazen.");
        if (!station.getId().matches("\\d+")) throw new IllegalArgumentException("ID mora vsebovati samo številke.");
        if (station.getLokacija() == null || station.getLokacija().trim().isEmpty()) throw new IllegalArgumentException("Lokacija ne sme biti prazna.");
        if (station.getRegija() == null || station.getRegija().trim().isEmpty()) throw new IllegalArgumentException("Regija ne sme biti prazna.");
        if (station.getEmail() == null || station.getEmail().trim().isEmpty()) throw new IllegalArgumentException("Email ne sme biti prazen.");
        if (station.getHitrostPolnjenja() <= 0) throw new IllegalArgumentException("Hitrost polnjenja mora biti večja od 0.");
        if (chargingStationDAO.getStationById(station.getId()) != null) throw new IllegalStateException("Polnilna postaja z ID " + station.getId() + " že obstaja.");

        station.setCurrentUserEmail(null); // Na začetku ni aktivnega uporabnika
        chargingStationDAO.addChargingStation(station);
    }

    public Polnilnica getChargingStationById(String id) {
        if (id == null || id.trim().isEmpty()) throw new IllegalArgumentException("ID ne sme biti prazen.");
        Polnilnica station = chargingStationDAO.getStationById(id);
        if (station == null) throw new NoSuchElementException("Polnilna postaja z ID " + id + " ni bila najdena.");
        return station;
    }

    public List<Polnilnica> getAllChargingStations() {
        return new ArrayList<>(chargingStationDAO.getAllStations());
    }

    public void updateChargingStation(String stationId, String location, boolean isAvailable) {
        Polnilnica station = getChargingStationById(stationId);
        station.setLokacija(location);
        station.setAvailable(isAvailable);
        chargingStationDAO.updateChargingStation(station);
    }

    public void deleteChargingStation(String deleteStationId) {
        getChargingStationById(deleteStationId); // Preveri, ali obstaja
        chargingStationDAO.deleteStation(deleteStationId);
    }

    public boolean startCharging(String stationId, String userEmail) {
        if (userEmail == null || userEmail.trim().isEmpty()) throw new IllegalArgumentException("Email ne sme biti prazen.");
        Polnilnica station = getChargingStationById(stationId);
        if (!station.isAvailable()) return false;

        station.setAvailable(false);
        station.setActive(true);
        station.setCurrentUserEmail(userEmail);
        chargingStationDAO.updateChargingStation(station);
        return true;
    }

    public void endCharging(String stationId) {
        Polnilnica station = getChargingStationById(stationId);
        if (station.isAvailable()) throw new IllegalStateException("Polnilnica " + stationId + " je že prosta.");
        if (station.getCurrentUserEmail() == null) throw new IllegalStateException("Na postaji " + stationId + " ni nobenega uporabnika.");

        station.setAvailable(true);
        station.setActive(false);
        station.setCurrentUserEmail(null);
        chargingStationDAO.updateChargingStation(station);
    }

    //Iteratorji za filtriranje polnilnic**
    public List<Polnilnica> getActiveChargingStations() {
        List<Polnilnica> activeStations = new ArrayList<>();
        AktivnaPolnilnicaIterator iterator = new AktivnaPolnilnicaIterator(getAllChargingStations());
        while (iterator.hasNext()) {
            activeStations.add(iterator.next());
        }
        return activeStations;
    }

    public List<Polnilnica> getStationsByMinSpeed(double minSpeed) {
        if (minSpeed < 0) throw new IllegalArgumentException("Hitrost ne sme biti negativna.");
        List<Polnilnica> filteredStations = new ArrayList<>();
        PolnilnicaPoHitrostiIterator iterator = new PolnilnicaPoHitrostiIterator(getAllChargingStations(), minSpeed);
        while (iterator.hasNext()) {
            filteredStations.add(iterator.next());
        }
        return filteredStations;
    }

    public List<Polnilnica> getStationsByRegion(String region, int providerId, PonudnikService ponudnikService) {
        if (region == null || region.trim().isEmpty()) throw new IllegalArgumentException("Regija ne sme biti prazna.");
        Ponudnik provider = ponudnikService.getProviderById(providerId);
        List<Polnilnica> filteredStations = new ArrayList<>();
        PolnilnicaPoRegijiIterator iterator = new PolnilnicaPoRegijiIterator(getAllChargingStations(), region, provider);
        while (iterator.hasNext()) {
            filteredStations.add(iterator.next());
        }
        return filteredStations;
    }



}
