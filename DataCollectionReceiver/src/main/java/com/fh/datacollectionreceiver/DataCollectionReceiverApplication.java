package com.fh.datacollectionreceiver;

import services.CollectionReceiverService;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class DataCollectionReceiverApplication {

	private static final CollectionReceiverService receiverService;

	static {
		try {
			receiverService = new CollectionReceiverService();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (TimeoutException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {

		try {
			receiverService.executeQueue();
		} catch (Exception e) {
			System.out.println("Error occurred when executing the Queue: " + e.getMessage());
		}
	}

}
