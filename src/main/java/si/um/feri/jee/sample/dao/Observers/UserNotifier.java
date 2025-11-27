package si.um.feri.jee.sample.dao.Observers;

import jakarta.ejb.Stateless;

@Stateless
public class UserNotifier implements Observer {

    @Override
    public void update(String stationLocation, String status, String userEmail, double chargingSpeed, String providerName) {
        if (userEmail == null || userEmail.trim().isEmpty()) {
            userEmail = "default@example.com";
        }

        System.out.println("📩 [EMAIL] Za: " + userEmail);
        System.out.println("📩 Zadeva: " + (status.equals("zasedena") ? "Polnjenje se je začelo! ⚡" : "Polnjenje končano! ✅"));
        System.out.println();
        System.out.println("Vaše polnjenje na polnilnici **" + stationLocation + "** je " + (status.equals("zasedena") ? "začelo" : "končano") + ".");
        System.out.println("🚗 Moč polnjenja: " + chargingSpeed + " kW");
        System.out.println("Lep pozdrav, Polnilnica " + stationLocation);
    }
}
