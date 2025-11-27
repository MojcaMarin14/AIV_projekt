package si.um.feri.jee.sample.Producer;

import si.um.feri.jee.sample.dao.interfaces.PolnilnicaDAO;
import si.um.feri.jee.sample.qualifiers.PolnilnicaList;
import si.um.feri.jee.sample.vao.Polnilnica;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.enterprise.inject.Produces;
import java.util.List;

@ApplicationScoped
public class PolnilnicaProducer {

    private final PolnilnicaDAO polnilnicaDAO;

    // Konstruktor označen z @Inject
    @Inject
    public PolnilnicaProducer(PolnilnicaDAO polnilnicaDAO) {
        this.polnilnicaDAO = polnilnicaDAO;
    }

    @Produces
    @PolnilnicaList
    public List<Polnilnica> producePolnilnice() {
        return polnilnicaDAO.getAllStations();
    }
}
