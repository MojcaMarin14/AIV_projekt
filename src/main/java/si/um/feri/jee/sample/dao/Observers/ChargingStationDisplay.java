package si.um.feri.jee.sample.dao.Observers;

import si.um.feri.jee.sample.Service.PolnilnicaService;
import si.um.feri.jee.sample.vao.Polnilnica;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class ChargingStationDisplay implements Observer {

    @Inject
    private PolnilnicaService polnilnicaService;

    @Override
    public void update(String stationLocation, String status, String userEmail, double chargingSpeed, String providerName) {
        System.out.println("📟 [Zaslon polnilne postaje] Polnilnica " + stationLocation + " je zdaj " + status + ".");
    }

    public void displayStationStatus() {
        List<Polnilnica> allStations = polnilnicaService.getAllChargingStations();
        if (allStations.isEmpty()) {
            System.out.println("📟 [Zaslon polnilne postaje] Trenutno ni polnilnic za prikaz.");
            return;
        }

        List<String> freeStations = new ArrayList<>();
        List<String> occupiedStations = new ArrayList<>();

        for (Polnilnica station : allStations) {
            String location = station.getLokacija();
            if (station.isAvailable()) {
                freeStations.add(location);
            } else {
                occupiedStations.add(location);
            }
        }

        System.out.println("📟 [Zaslon polnilne postaje] Trenutno stanje polnilnic:");
        if (!freeStations.isEmpty()) {
            System.out.println("✅ Proste polnilnice: " + String.join(", ", freeStations));
        }
        if (!occupiedStations.isEmpty()) {
            System.out.println("⛔ Zasedene polnilnice: " + String.join(", ", occupiedStations));
        }
    }

    public void updateStationStatus(Polnilnica station) {
        String location = station.getLokacija();
        String status = station.isAvailable() ? "prosta" : "zasedena";
        System.out.println("📟 [Zaslon polnilne postaje] Polnilnica " + location + " je zdaj " + status + ".");
    }
}