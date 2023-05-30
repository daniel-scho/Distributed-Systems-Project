package queue;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class DataCollectionDispatcherProducer {
    private final String QUEUE_TO_STATION_DATA_COLLECTOR = "StationDataCollectorQueue";
    private final String QUEUE_TO_DATA_COLLECTION_RECEIVER = "DataCollectionReceiverQueue";

    public void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // Deklarieren der Queue für den Station Data Collector
        channel.queueDeclare(QUEUE_TO_STATION_DATA_COLLECTOR, false, false, false, null);
        // Deklarieren der Queue für den Data Collection Receiver
        channel.queueDeclare(QUEUE_TO_DATA_COLLECTION_RECEIVER, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        // Startet den Datenerfassungsauftrag
        startDataGatheringJob(channel);

        channel.close();
        connection.close();
    }

    private void startDataGatheringJob(Channel channel) throws Exception {
        int numberOfDatabases = 5;

        // Sendet eine Nachricht für jede Ladestation an den Station Data Collector
        for (int i = 1; i <= numberOfDatabases; i++) {
            String message = "Start Data Gathering Job for Charging Station " + i;
            channel.basicPublish("", QUEUE_TO_STATION_DATA_COLLECTOR, null, message.getBytes());
            System.out.println(" [x] Sent to Station Data Collector: '" + message + "'");
        }

        // Sendet eine Nachricht an den Data Collection Receiver über die Anzahl der Nachrichten
        String message = "New data gathering job started. Number of messages: " + numberOfDatabases;
        channel.basicPublish("", QUEUE_TO_DATA_COLLECTION_RECEIVER, null, message.getBytes());
        System.out.println(" [x] Sent to Data Collection Receiver: '" + message + "'");
    }
}

