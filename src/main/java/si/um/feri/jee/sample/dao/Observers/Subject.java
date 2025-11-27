package si.um.feri.jee.sample.dao.Observers;

public interface Subject {
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();//nad vsemi registriranimi poklicemo notify
}