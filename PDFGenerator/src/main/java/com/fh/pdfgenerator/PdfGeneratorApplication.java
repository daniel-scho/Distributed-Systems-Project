package com.fh.pdfgenerator;

import queue.PDFGeneratorConsumer;
import service.Database;
import service.DatabaseQueryExecuter;
import service.PDFGeneratorService;

public class PdfGeneratorApplication {

	public static void main(String[] args) throws Exception {
		Database db = new Database();
		DatabaseQueryExecuter dbClient = new DatabaseQueryExecuter(db);
		PDFGeneratorService service = new PDFGeneratorService(dbClient);
		PDFGeneratorConsumer consumer = new PDFGeneratorConsumer(service);


		consumer.executeQueue();
	}
}
