package si.um.feri.jee.sample.ChainOfResponsibility;

import si.um.feri.jee.sample.vao.Polnilnica;
import si.um.feri.jee.sample.vao.User;

public abstract class ChargingHandler {
    protected ChargingHandler nextHandler;

    // Nastavi naslednji handler v verigi
    public void setNextHandler(ChargingHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    // Abstraktna metoda, ki jo morajo implementirati podrazredi
    public abstract boolean handleRequest(Polnilnica station, User user);

    // Posreduje zahtevo naslednjemu handlerju, če obstaja
    protected boolean passToNext(Polnilnica station, User user) {
        if (nextHandler != null) {
            return nextHandler.handleRequest(station, user);
        }
        return true; // Če ni naslednjega handlerja, vrne true (uspešno)
    }
}