package queue;
import com.rabbitmq.client.*;
public class DataCollectionReceiverConsumer {
    private final static String QUEUE_TO_DATA_COLLECTION_RECEIVER = "DataCollectionReceiverQueue";
    private final static String QUEUE_COLLECTOR_TO_RECEIVER = "DataCollectiontoReceiverQueue";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // Deklarieren der Queue für den Data Collection Receiver
        channel.queueDeclare(QUEUE_TO_DATA_COLLECTION_RECEIVER, false, false, false, null);
        // Deklarieren der Queue für die Kommunikation mit dem DataCollectionReceiver
        channel.queueDeclare(QUEUE_COLLECTOR_TO_RECEIVER, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        // Erstellen eines Consumers für QUEUE_TO_DATA_COLLECTION_RECEIVER
        com.rabbitmq.client.Consumer dataCollectionReceiverConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws java.io.IOException {
                String message = new String(body, "UTF-8");
                System.out.println("Received message from Data Collection Receiver: '" + message + "'");
                // Weitere Verarbeitung der Nachricht hier möglich
            }
        };

        // Registrieren des Consumers für QUEUE_TO_DATA_COLLECTION_RECEIVER
        channel.basicConsume(QUEUE_TO_DATA_COLLECTION_RECEIVER, true, dataCollectionReceiverConsumer);

        // Erstellen eines Consumers für QUEUE_COLLECTOR_TO_RECEIVER
        com.rabbitmq.client.Consumer collectorToReceiverConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws java.io.IOException {
                String message = new String(body, "UTF-8");
                System.out.println("Received message from Collector to Receiver: '" + message + "'");
                // Weitere Verarbeitung der Nachricht hier möglich
            }
        };

        // Registrieren des Consumers für QUEUE_COLLECTOR_TO_RECEIVER
        channel.basicConsume(QUEUE_COLLECTOR_TO_RECEIVER, true, collectorToReceiverConsumer);
    }
}
