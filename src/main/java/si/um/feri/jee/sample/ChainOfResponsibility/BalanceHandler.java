package si.um.feri.jee.sample.ChainOfResponsibility;

import si.um.feri.jee.sample.vao.Polnilnica;
import si.um.feri.jee.sample.vao.User;
import java.util.logging.Logger;

public class BalanceHandler extends ChargingHandler {
    private static final Logger LOGGER = Logger.getLogger(BalanceHandler.class.getName());
    private static final double MINIMUM_BALANCE = 10.0;

    @Override
    public boolean handleRequest(Polnilnica station, User user) {
        if (user.getBalance() < MINIMUM_BALANCE) {
            LOGGER.warning("Uporabnik " + user.getEmail() + " nima dovolj sredstev na računu. Potrebnih je vsaj " + MINIMUM_BALANCE + "€.");
            return false;
        }
        LOGGER.info("Uporabnik " + user.getEmail() + " ima dovolj sredstev (" + user.getBalance() + "€).");
        return passToNext(station, user);
    }
}