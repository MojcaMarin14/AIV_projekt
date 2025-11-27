package si.um.feri.jee.sample.dao.impl;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import si.um.feri.jee.sample.dao.interfaces.PolnilnicaDAO;
import si.um.feri.jee.sample.vao.Polnilnica;

import java.util.List;

@Stateless
public class JpaPolnilnicaDAO implements PolnilnicaDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void addChargingStation(Polnilnica station) {
        em.persist(station);
    }

    @Override
    public List<Polnilnica> getAllStations() {
        return em.createQuery("SELECT p FROM Polnilnica p", Polnilnica.class).getResultList();
    }

    @Override
    public Polnilnica getStationById(String id) {
        return em.find(Polnilnica.class, id);
    }

    @Override
    public void updateChargingStation(Polnilnica updatedStation) {
        em.merge(updatedStation);
    }

    @Override
    public void deleteStation(String id) {
        Polnilnica station = em.find(Polnilnica.class, id);
        if (station != null) {
            em.remove(station);
        }
    }
}
