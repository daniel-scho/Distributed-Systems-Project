package queue;

import com.rabbitmq.client.*;

public class StationDataCollectorConsumer {
    private final static String QUEUE_TO_STATION_DATA_COLLECTOR = "StationDataCollectorQueue";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // Deklarieren der Queue für den Station Data Collector
        channel.queueDeclare(QUEUE_TO_STATION_DATA_COLLECTOR, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        // Erstellen eines Consumers, um Nachrichten zu empfangen
        com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws java.io.IOException {
                String message = new String(body, "UTF-8");
                System.out.println("Received message from Station Data Collector: '" + message + "'");
                // Weitere Verarbeitung der Nachricht hier möglich
            }
        };

        // Registrieren des Consumers, um Nachrichten von der Queue zu empfangen
        channel.basicConsume(QUEUE_TO_STATION_DATA_COLLECTOR, true, consumer);
    }
}