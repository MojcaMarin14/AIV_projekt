package si.um.feri.jee.mdb;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.TextMessage;

@MessageDriven(
        activationConfig = {
                @ActivationConfigProperty(propertyName = "destinationType",
                        propertyValue = "jakarta.jms.Queue"),
                @ActivationConfigProperty(propertyName = "destination",
                        propertyValue = "java:/jboss/ChargingEventsQueue")
        }
)
public class ChargingEventListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage textMessage) {
                String payload = textMessage.getText();
                System.out.println("⚡ Polnjenje zabeleženo: " + payload);
                // Tukaj dodajte logiko za obdelavo podatkov o polnjenju
                // (čas, tip vozila, lastnik)
            }
        } catch (Exception e) {
            System.err.println("Napaka pri obdelavi sporočila:");
            e.printStackTrace();
        }
    }
}