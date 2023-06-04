package queue;


import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class StationDataCollectorProducer {
    private final String QUEUE_COLLECTOR_TO_RECEIVER = "DataCollectiontoReceiverQueue";


    public void executeDataCollectiontoReceiverQueue() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // Deklarieren der Queue für die Kommunikation mit dem DataCollectionReceiver
        channel.queueDeclare(QUEUE_COLLECTOR_TO_RECEIVER, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        // Sammeln der Daten für einen bestimmten Kunden von einer bestimmten Ladestation
        String customerId = "123456"; // Kunden-ID
        int chargingStationId = 1; // Ladestation-ID

        String data = gatherData(customerId, chargingStationId);

        // Senden der Daten an den DataCollectionReceiver
        sendToDataCollectionReceiver(channel, data);

        channel.close();
        connection.close();
    }

    private  String gatherData(String customerId, int chargingStationId) {
        // Hier können die Daten für den Kunden von der Ladestation gesammelt werden
        // Dummy-Daten für Beispielzwecke
        String data = "Data for Customer " + customerId + " from Charging Station " + chargingStationId;
        return data;
    }

    private void sendToDataCollectionReceiver(Channel channel, String data) throws Exception {
        // Senden der Daten an den DataCollectionReceiver
        channel.basicPublish("", QUEUE_COLLECTOR_TO_RECEIVER, null, data.getBytes());
        System.out.println(" [x] Sent to DataCollectionReceiver: '" + data + "'");
    }
}

