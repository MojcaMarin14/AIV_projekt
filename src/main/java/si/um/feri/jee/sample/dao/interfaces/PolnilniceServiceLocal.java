package si.um.feri.jee.sample.dao.interfaces;

import jakarta.ejb.Local;
import si.um.feri.jee.sample.vao.Polnilnica;

import java.util.List;

@Local
public interface PolnilniceServiceLocal {
    boolean preveriMoznostPolnjenja(String polnilnicaId, String uporabnikEmail);
    void zakljuciPolnjenje(String polnilnicaId);
    List<Polnilnica> vsePolnilnice();
    List<Polnilnica> aktivnePolnilnice();
    Polnilnica getActiveChargingStation();
    String getCurrentUserEmail();
}
