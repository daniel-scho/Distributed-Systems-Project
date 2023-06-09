package queue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fh.stationdatacollector.dto.Station;
import com.fh.stationdatacollector.services.DataCollectorService;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

public class StationDataCollectorConsumer {
    private final String QUEUE_TO_STATION_DATA_COLLECTOR = "StationDataCollector";
    private Channel channel;
    private DataCollectorService dataCollectorService;
    public StationDataCollectorConsumer(DataCollectorService dataCollectorService) throws IOException, TimeoutException {
        this.dataCollectorService = dataCollectorService;

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(30003);

        Connection connection = factory.newConnection();
        this.channel = connection.createChannel();

        channel.queueDeclare(QUEUE_TO_STATION_DATA_COLLECTOR, true, false, false, null);
    }

    public void executeStationDataCollectorQueue() throws Exception {
        com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws java.io.IOException {
                String message = new String(body, StandardCharsets.UTF_8);
                System.out.println("Received message from Station Data Collector: '" + message + "'");
                ObjectMapper objectMapper = new ObjectMapper();
                Station station = objectMapper.readValue(message, Station.class);

                try {
                    dataCollectorService.processStation(station);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };

        channel.basicConsume(QUEUE_TO_STATION_DATA_COLLECTOR, true, consumer);
    }

}