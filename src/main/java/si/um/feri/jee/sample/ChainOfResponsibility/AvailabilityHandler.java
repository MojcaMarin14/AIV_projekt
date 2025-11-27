package si.um.feri.jee.sample.ChainOfResponsibility;

import si.um.feri.jee.sample.vao.Polnilnica;
import si.um.feri.jee.sample.vao.User;

import java.util.logging.Logger;

public class AvailabilityHandler extends ChargingHandler {
    private static final Logger LOGGER = Logger.getLogger(AvailabilityHandler.class.getName());

    @Override
    public boolean handleRequest(Polnilnica station, User user) {
        if (!station.isAvailable()) {
            LOGGER.warning("Polnilnica z ID " + station.getId() + " ni na voljo.");
            return false;
        }
        LOGGER.info("Polnilnica z ID " + station.getId() + " je na voljo.");
        return passToNext(station, user);
    }
}