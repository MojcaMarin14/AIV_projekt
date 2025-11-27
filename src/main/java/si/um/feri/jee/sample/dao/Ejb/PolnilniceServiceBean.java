package si.um.feri.jee.sample.dao.Ejb;

import jakarta.ejb.Local;
import jakarta.ejb.Remote;
import jakarta.ejb.Stateful;
import jakarta.inject.Inject;
import si.um.feri.jee.sample.dao.interfaces.PolnilniceServiceLocal;
import si.um.feri.jee.sample.dao.interfaces.PolnilniceServiceRemote;
import si.um.feri.jee.sample.Service.PolnilnicaService;
import si.um.feri.jee.sample.vao.Polnilnica;

import java.util.List;
@Stateful
@Remote(PolnilniceServiceRemote.class)
@Local(PolnilniceServiceLocal.class)
public class PolnilniceServiceBean implements PolnilniceServiceRemote, PolnilniceServiceLocal {

    @Inject
    private PolnilnicaService polnilnicaService;

    private Polnilnica activeChargingStation;
    private String currentUserEmail;

    @Override
    public boolean preveriMoznostPolnjenja(String polnilnicaId, String uporabnikEmail) {
        try {
            this.activeChargingStation = polnilnicaService.getChargingStationById(polnilnicaId);
            this.currentUserEmail = uporabnikEmail;
            return polnilnicaService.startCharging(polnilnicaId, uporabnikEmail);
        } catch (Exception e) {
            throw new RuntimeException("Napaka pri preverjanju možnosti polnjenja", e);
        }
    }

    @Override
    public void zakljuciPolnjenje(String polnilnicaId) {
        polnilnicaService.endCharging(polnilnicaId);
        this.activeChargingStation = null;
        this.currentUserEmail = null;
    }

    @Override
    public List<Polnilnica> vsePolnilnice() {
        return polnilnicaService.getAllChargingStations();
    }

    @Override
    public List<Polnilnica> aktivnePolnilnice() {
        return polnilnicaService.getActiveChargingStations();
    }


    public Polnilnica getActiveChargingStation() {
        return activeChargingStation;
    }


    public String getCurrentUserEmail() {
        return currentUserEmail;
    }
}