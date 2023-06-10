package queue;

import com.rabbitmq.client.*;

import service.PDFGeneratorService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PDFGeneratorConsumer {
    private final static String QUEUE_TO_PDF_GENERATOR = "PDFGeneratorQueue";

    public PDFGeneratorConsumer(PDFGeneratorService pdfGeneratorService) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(30003);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_TO_PDF_GENERATOR, false, false, false, null);



        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String json = new String(body, StandardCharsets.UTF_8);
                System.out.println("Received JSON: " + json);

                // Aufruf des PDFGeneratorService, um PDF zu generieren und zu speichern
                pdfGeneratorService.generateAndSavePDF(json);

                // Bestätigung der erfolgreichen Verarbeitung der Nachricht
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };

        channel.basicConsume(QUEUE_TO_PDF_GENERATOR, false, consumer);
        System.out.println("PDFGeneratorConsumer is listening for messages...");
    }
}
