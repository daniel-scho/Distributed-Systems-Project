package queue;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import dto.Customer;
import services.CollectionReceiverService;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class DataCollectionReceiverConsumer {
    CollectionReceiverService receiverService;
    Channel channel;
    private final String QUEUE_FROM_DISPATCHER = "DataCollectionReceiverFromDispatcher";
    private final String QUEUE_FROM_COLLECTOR = "DataCollectionReceiverFromCollector";

    int expectedMessagesAmount = 0;
    int receivedMessagesAmount = 0;

    Customer customer = null;

    public DataCollectionReceiverConsumer(CollectionReceiverService receiverService) throws IOException, TimeoutException {
        this.receiverService = receiverService;

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(30003);
        Connection connection = factory.newConnection();
        channel = connection.createChannel();

        channel.queueDeclare(QUEUE_FROM_DISPATCHER, false, false, false, null);
        channel.queueDeclare(QUEUE_FROM_COLLECTOR, false, false, false, null);
    }

    // consumes messages
    public void consumeMessagesJob() throws Exception {
        System.out.println(" [*] Waiting for messages.");

        com.rabbitmq.client.Consumer handleDispatcherMessage = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws java.io.IOException {
                String message = new String(body, "UTF-8");
                System.out.println("Received message from Data Collection Dispatcher: '" + message + "'");

                expectedMessagesAmount = Integer.parseInt(message);
            }
        };
        channel.basicConsume(QUEUE_FROM_DISPATCHER, true, handleDispatcherMessage);

        com.rabbitmq.client.Consumer handleStationMessage = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                processCustomerData(body);

                try {
                    isDataComplete();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
        channel.basicConsume(QUEUE_FROM_COLLECTOR, true, handleStationMessage);
    }

    private void processCustomerData(byte[] body) throws java.io.IOException {
        receivedMessagesAmount++;

        String message = new String(body, "UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        Customer customer = objectMapper.readValue(message, Customer.class);
        System.out.println("Received: " + receivedMessagesAmount + " Message");
        System.out.println("Received message from Collector to Receiver: '" + customer.customer_id + "'");

        // KWH from customer are combined into a single customer to be sent to the PDF generator
        if (this.customer == null) {
            this.customer = customer;
        } else {
            // append KWH to the existing customer
            this.customer.chargedKWH.addAll(customer.chargedKWH);
        }
    }
    private void isDataComplete() throws Exception {
        if (expectedMessagesAmount == receivedMessagesAmount) {
            this.receiverService.sendToPDFGenerator(this.customer);
            resetJob();
        }
    }
    private void resetJob() {
        expectedMessagesAmount = 0;
        receivedMessagesAmount = 0;
        this.customer = null;
    }
}