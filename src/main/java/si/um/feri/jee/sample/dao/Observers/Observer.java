package si.um.feri.jee.sample.dao.Observers;

public interface Observer {// določa pravila, ki ga morajo implementirati vsi razredi
    void update(String stationLocation, String status, String userEmail, double chargingSpeed, String providerName);
}