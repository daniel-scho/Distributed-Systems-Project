package queue;
/*
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class DataCollectionReceiverProducer {
    private final static String QUEUE_TO_PDF_GENERATOR = "PDFGeneratorQueue";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // Deklarieren der Queue für die Kommunikation mit dem PDF Generator
        channel.queueDeclare(QUEUE_TO_PDF_GENERATOR, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        // Empfangen der gesammelten Daten
        com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws java.io.IOException {
                String message = new String(body, "UTF-8");
                System.out.println("Received collected data: '" + message + "'");
                // Sortieren der Daten gemäß des Erfassungsauftrags
                String sortedData = sortData(message);

                // Überprüfen, ob die Daten vollständig sind
                if (isDataComplete(sortedData)) {
                    // Senden der vollständigen Daten an den PDF Generator
                    sendToPDFGenerator(channel, sortedData);
                } else {
                    System.out.println("Data is incomplete. Waiting for more data.");
                }
            }
        };

        // Registrieren des Consumers, um Nachrichten vom Data Collection Receiver zu empfangen
        channel.basicConsume(QUEUE_TO_PDF_GENERATOR, true, consumer);
    }

    private static String sortData(String data) {
        // Sortieren der Daten gemäß des Erfassungsauftrags
        // Implementiere hier die Logik zum Sortieren der Daten
        return data; // Rückgabe der sortierten Daten
    }

    private static boolean isDataComplete(String data) {
        // Überprüfen, ob die Daten vollständig sind
        // Implementiere hier die Logik zur Überprüfung der Vollständigkeit der Daten
        return true; // Rückgabe des Überprüfungsergebnisses (true, wenn vollständig; false, wenn nicht vollständig)
    }

    private static void sendToPDFGenerator(Channel channel, String data) throws Exception {
        // Senden der Daten an den PDF Generator
        channel.basicPublish("", QUEUE_TO_PDF_GENERATOR, null, data.getBytes());
        System.out.println("Sent data to PDF Generator: '" + data + "'");
    }
}*/