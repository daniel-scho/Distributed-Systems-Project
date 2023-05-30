package queue;

import com.rabbitmq.client.*;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class DataCollectionDispatcherConsumer {
    private final String QUEUE_toDispatcher = "DataCollectionDispatcherQueue";

    public void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_toDispatcher, false, false, false, null);

        System.out.println("Waiting for messages...");

        // Erstellen eines Consumer-Objekts, um Nachrichten zu empfangen
        com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws java.io.IOException {
                String message = new String(body, "UTF-8");
                System.out.println("Received message: '" + message + "'");
                // Weitere Verarbeitung der Nachricht hier möglich
            }
        };

        // Registrieren des Consumers, um Nachrichten von der Warteschlange zu empfangen
        channel.basicConsume(QUEUE_toDispatcher, true, consumer);
    }
}
