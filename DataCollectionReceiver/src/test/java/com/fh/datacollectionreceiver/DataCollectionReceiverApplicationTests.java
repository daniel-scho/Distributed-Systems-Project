package com.fh.datacollectionreceiver;

import dto.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import queue.DataCollectionReceiverConsumer;
import services.CollectionReceiverService;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;

@SpringBootTest
@ContextConfiguration(classes = DataCollectionReceiverApplication.class)
class DataCollectionReceiverApplicationTests {
	@Test
	public void testResetJob() throws IOException, TimeoutException {
		// Set initial values
		CollectionReceiverService service = mock(CollectionReceiverService.class);
		DataCollectionReceiverConsumer consumer = new DataCollectionReceiverConsumer(service);
		consumer.expectedMessagesAmount = 3;
		consumer.receivedMessagesAmount = 1;
		consumer.customer = new Customer();
		// Call the resetJob() method
		consumer.resetJob();

		int expectedMessages = consumer.expectedMessagesAmount;
		int receivedMessages = consumer.receivedMessagesAmount;

		// Assert the values are reset to their initial states
		assertEquals(0, expectedMessages);
		assertEquals(0, receivedMessages);
		assertNull(consumer.customer);
	}

}
