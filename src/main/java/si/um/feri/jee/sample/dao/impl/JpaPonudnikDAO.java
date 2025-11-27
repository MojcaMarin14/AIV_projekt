package si.um.feri.jee.sample.dao.impl;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import si.um.feri.jee.sample.dao.interfaces.PonudnikDAO;
import si.um.feri.jee.sample.vao.Ponudnik;
import si.um.feri.jee.sample.vao.Polnilnica;

import java.util.List;
import java.util.Optional;

@Stateless
public class JpaPonudnikDAO implements PonudnikDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void add(Ponudnik ponudnik) {
        em.persist(ponudnik);
    }

    @Override
    public List<Ponudnik> getAll() {
        return em.createQuery("SELECT p FROM Ponudnik p", Ponudnik.class).getResultList();
    }

    @Override
    public Ponudnik findById(int id) {
        return em.find(Ponudnik.class, id);
    }

    @Override
    public void delete(int id) {
        Ponudnik ponudnik = findById(id);
        if (ponudnik != null) {
            em.remove(ponudnik);
        }
    }

    @Override
    public void update(Ponudnik ponudnik) {
        em.merge(ponudnik);
    }

    @Override
    public String findProviderByStationId(int stationId) {
        return em.createQuery(
                        "SELECT p.ime FROM Ponudnik p JOIN p.polnilnice s WHERE s.id = :stationId", String.class)
                .setParameter("stationId", stationId)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public String findProviderNameByChargingStation(Polnilnica station) {
        return em.createQuery(
                        "SELECT p.ime FROM Ponudnik p JOIN p.polnilnice s WHERE s.id = :stationId", String.class)
                .setParameter("stationId", station.getId())
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
    }
}
