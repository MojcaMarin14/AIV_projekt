package si.um.feri.jee.sample.dao.interfaces;

import si.um.feri.jee.sample.vao.Polnilnica;
import jakarta.ejb.Local;
import java.util.List;

@Local
public interface PolnilnicaDAO {
    void addChargingStation(Polnilnica station);
    List<Polnilnica> getAllStations();
    Polnilnica getStationById(String id);

    // Dodane metode
    void updateChargingStation(Polnilnica updatedStation);
    void deleteStation(String id);
}
