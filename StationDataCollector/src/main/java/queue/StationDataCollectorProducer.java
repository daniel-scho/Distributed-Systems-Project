package queue;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fh.stationdatacollector.dto.Customer;
import com.fh.stationdatacollector.services.DataCollectorService;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class StationDataCollectorProducer {
    private final String QUEUE_COLLECTOR_TO_RECEIVER = "DataCollectionReceiverFromCollector";
    private Channel channel;
    private Connection connection;
    public StationDataCollectorProducer() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(30003);

        connection = factory.newConnection();
        channel = connection.createChannel();

        channel.queueDeclare(QUEUE_COLLECTOR_TO_RECEIVER, false, false, false, null);
        System.out.println(" [*] Waiting for messages.");
    }

    public void sendToDataCollectionReceiver(Customer customer) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        String jsonCustomer = objectMapper.writeValueAsString(customer);

        byte[] CustomerBytes = jsonCustomer.getBytes();

        channel.basicPublish("", QUEUE_COLLECTOR_TO_RECEIVER, null, CustomerBytes);
        System.out.println(" [x] Sent Data of CustomerID '" + customer.customer_id + "' to DataCollectionReceiver");

        channel.close();
        connection.close();
    }

}