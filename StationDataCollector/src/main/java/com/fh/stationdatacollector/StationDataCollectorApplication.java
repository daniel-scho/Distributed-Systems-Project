package com.fh.stationdatacollector;

import com.fh.stationdatacollector.services.DataCollectorService;
import queue.StationDataCollectorConsumer;
import queue.StationDataCollectorProducer;


public class StationDataCollectorApplication {

	public static void main(String[] args) throws Exception {
		DataCollectorService dataCollectorService = new DataCollectorService();

		StationDataCollectorConsumer consume = new StationDataCollectorConsumer(dataCollectorService);

		System.out.println(" [*] Waiting for messages.");

		while(true) {
			consume.executeStationDataCollectorQueue();

		}

	}


}
