package si.um.feri.jee.sample.exception;

public class ChargingStationException extends Exception {

    // Default constructor
    public ChargingStationException() {
        super();
    }

    // Constructor with message
    public ChargingStationException(String message) {
        super(message);
    }

    // Constructor with message and cause
    public ChargingStationException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor with cause
    public ChargingStationException(Throwable cause) {
        super(cause);
    }
}