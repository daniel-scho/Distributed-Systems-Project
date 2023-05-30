package com.fh.stationdatacollector;

import queue.StationDataCollectorConsumer;
import queue.StationDataCollectorProducer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class StationDataCollectorApplication {

	public static void main(String[] args) throws Exception {

		StationDataCollectorConsumer consume = new StationDataCollectorConsumer();
		StationDataCollectorProducer produce = new StationDataCollectorProducer();

		consume.executeStationDataCollectorQueue();

		produce.executeDataCollectiontoReceiverQueue();
	}


}
