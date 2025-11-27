package si.um.feri.jee.sample.ChainOfResponsibility;

import si.um.feri.jee.sample.vao.Polnilnica;
import si.um.feri.jee.sample.vao.User;

import java.util.logging.Logger;

public class CompatibilityHandler extends ChargingHandler {
    private static final Logger LOGGER = Logger.getLogger(CompatibilityHandler.class.getName());

    @Override
    public boolean handleRequest(Polnilnica station, User user) {
        String carType = user.getCarType().toLowerCase();
        double chargingPower = station.getHitrostPolnjenja();

        if (carType.equals("small")) {
            if (chargingPower > 22) {
                LOGGER.warning("Polnilnica z ID " + station.getId() + " ni kompatibilna z majhnim vozilom zaradi previsoke moči polnjenja (" + chargingPower + " kW).");
                return false;
            }
            LOGGER.info("Polnilnica z ID " + station.getId() + " je kompatibilna z majhnim vozilom (moč: " + chargingPower + " kW).");
        } else if (carType.equals("medium")) {
            if (chargingPower > 50) {
                LOGGER.warning("Polnilnica z ID " + station.getId() + " ni kompatibilna s srednje zmogljivim vozilom zaradi previsoke moči polnjenja (" + chargingPower + " kW).");
                return false;
            }
            LOGGER.info("Polnilnica z ID " + station.getId() + " je kompatibilna s srednje zmogljivim vozilom (moč: " + chargingPower + " kW).");
        } else if (carType.equals("sport")) {
            if (chargingPower < 50) {
                LOGGER.warning("Polnilnica z ID " + station.getId() + " ni kompatibilna s športnim vozilom zaradi prenizke moči polnjenja (" + chargingPower + " kW).");
                return false;
            }
            LOGGER.info("Polnilnica z ID " + station.getId() + " je kompatibilna s športnim vozilom (moč: " + chargingPower + " kW).");
        } else {
            LOGGER.severe("Neznana vrsta vozila: " + carType + " za uporabnika " + user.getEmail());
            return false;
        }

        return passToNext(station, user);
    }
}