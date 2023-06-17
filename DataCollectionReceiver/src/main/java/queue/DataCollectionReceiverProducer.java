package queue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import dto.Customer;

public class DataCollectionReceiverProducer {
    private final static String QUEUE_TO_PDF_GENERATOR = "PDFGeneratorQueue";

    public  void sendToPDFGenerator(Customer customer) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(30003);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_TO_PDF_GENERATOR, false, false, false, null);


        ObjectMapper objectMapper = new ObjectMapper();

        String jsonCustomer = objectMapper.writeValueAsString(customer);

        byte[] CustomerBytes = jsonCustomer.getBytes();

        channel.basicPublish("", QUEUE_TO_PDF_GENERATOR, null, CustomerBytes);
        System.out.println(" [x] Sent Data of customer with id:'"
                + customer.customer_id + "' and KWH: "
                + customer.chargedKWH + "' to " + QUEUE_TO_PDF_GENERATOR);
    }

}