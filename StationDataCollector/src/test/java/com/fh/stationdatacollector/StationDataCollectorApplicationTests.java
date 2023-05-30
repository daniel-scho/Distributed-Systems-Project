package com.fh.stationdatacollector;
/*
import com.fh.stationdatacollector.dto.StationData;
import com.fh.stationdatacollector.services.Database;
import com.fh.stationdatacollector.services.Queue;*/
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import queue.StationDataCollectorProducer;

import java.util.List;

import static org.mockito.Mockito.mock;

@SpringBootTest
class StationDataCollectorApplicationTests {

	@Test
	void contextLoads() {
	}
/*
	@Test
	public void shouldReturnCumulatedCustomerKwh() {
		StationDataCollectorProducer queue = mock(StationDataCollectorProducer.class);
		Database database = mock(Database.class);
		when(database.getAllStationData()).thenReturn(
				List.of(
						new StationData(1, 1),
						new StationData(2, 3),
				)

		))
		StationDataCollector stationDataCollector =
			= new StationDataCollector(database, queue);

		int total = stationDataCollector.getTotatlKwhForCustomer(1);

		assertEquals(3, total);
	}
*/
}
