package si.um.feri.jee.sample.dao.interfaces;

import jakarta.ejb.Local;
import jakarta.ejb.Lock;
import jakarta.ejb.LockType;
import si.um.feri.jee.sample.vao.Polnilnica;
import si.um.feri.jee.sample.vao.Ponudnik;


import java.util.List;


@Local
public interface PonudnikDAO {
    void add(Ponudnik ponudnik);
    List<Ponudnik> getAll();
    Ponudnik findById(int id);
    void delete(int id);

    // Dodane manjkajoče metode
    void update(Ponudnik ponudnik);
    String findProviderByStationId(int stationId);

    @Lock(LockType.READ)
    String findProviderNameByChargingStation(Polnilnica station);
}