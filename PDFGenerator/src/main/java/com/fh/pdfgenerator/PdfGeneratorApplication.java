package com.fh.pdfgenerator;

import queue.PDFGeneratorConsumer;
import service.PDFGeneratorService;

public class PdfGeneratorApplication {

	public static void main(String[] args) throws Exception {
		PDFGeneratorService service = new PDFGeneratorService();
		PDFGeneratorConsumer consumer = new PDFGeneratorConsumer(service);

		consumer.executeQueue();
	}
}
