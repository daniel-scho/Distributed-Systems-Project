package com.fh.pdfgenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.util.concurrent.TimeoutException;

@SpringBootApplication
public class PdfGeneratorApplication {

	private final static String QUEUE_NAME = "QUEUE_5";
	private final static String QUEUE_NAME_OUT = "QUEUE_6";

	public static void main(String[] args) throws IOException, TimeoutException {
		//SpringApplication.run(PdfGeneratorApplication.class, args);

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		factory.setPort(30003);

		Connection connection = factory.newConnection();
		Channel consumeChannel = connection.createChannel();
		Channel publishChannel = connection.createChannel();

		consumeChannel.queueDeclare(QUEUE_NAME, false, false, false, null);
		consumeChannel.queueDeclare(QUEUE_NAME_OUT, false, false, false, null);

		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			String message = new String(delivery.getBody(), "UTF-8");
			int messageLength = message.length();
			System.out.println(" [x] Received '" + message + "'");

			String modifiedString = message.replace(" ", "");

			System.out.println(" [x] Remove spaces: " + modifiedString);
			try {
				System.out.println(" [Processing] Waiting: " + messageLength * 1000);
				Thread.sleep(messageLength * 1000);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			try {
				publishMessage(publishChannel, QUEUE_NAME_OUT, modifiedString);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		};

		consumeChannel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
		});
	}
	private static void publishMessage(Channel channel, String queue, String message) throws IOException {
		channel.basicPublish("", queue, null, message.getBytes(StandardCharsets.UTF_8));
		System.out.println(" [x] Sent '" + message + "' to " + queue);
	}
}