package si.um.feri.jee.sample.dao.Observers;
import si.um.feri.jee.sample.vao.Polnilnica;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import si.um.feri.jee.sample.Service.PonudnikService;


@Stateless
public class ProviderNotifier implements Observer {

    @Inject
    private PonudnikService ponudnikService;

    @Override
    public void update(String stationLocation, String status, String userEmail, double chargingSpeed, String providerName) {
        System.out.println("🏢 Ponudnik " + providerName + " obveščen: Polnilnica " + stationLocation + " je zdaj " + status + ".");
        System.out.println(status.equals("zasedena") ? "Polnjenje se je začelo!" : "Polnjenje končano!");
    }

    // Metoda za obveščanje ponudnika
    public void notifyProvider(Polnilnica station, boolean isCharging) {
        String providerName = ponudnikService.findProviderNameByChargingStation(station);
        String status = station.isAvailable() ? "prosta" : "zasedena";
        String chargingStatus = isCharging ? "Polnjenje se je začelo!" : "Polnjenje končano!";

        System.out.println("🏢 Ponudnik " + providerName + " obveščen: Polnilnica " + station.getLokacija() + " je zdaj " + status + ".");
        System.out.println(chargingStatus);
    }
}