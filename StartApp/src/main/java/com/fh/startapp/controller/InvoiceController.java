package com.fh.startapp.controller;

import com.fh.startapp.queue.Send;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
public class InvoiceController {
    private Send queueSender;

    @Autowired
    public InvoiceController(Send queueSender) {
        this.queueSender = queueSender;
    }

    /**
     * @param customer_id
     * This route starts the data gathering job
     * and deletes the existing Invoice.pdf if existing
     */
    @PostMapping("/invoices/{customer_id}")
    public String processInvoice(@PathVariable("customer_id") String customerId) {
        System.out.println("Received Customer ID: " + customerId );
        try {
            queueSender.executeSendQueue(customerId);
            return "Invoice processing initiated for Customer ID: " + customerId;
        } catch (Exception e) {
            return "Error occurred while processing invoice for Customer ID: " + customerId +
                    "/nError: " + e;
        }
    }

    /**
     * @param customer_id
     * This route checks for the path of the Invoice PDF
     * and sends the PDF
     */
    @GetMapping("/invoices/{customer_id}")
    public ResponseEntity<Resource> getInvoicePDFPath(@PathVariable("customer_id") String customerId) {
        String fileName = "Invoice.pdf";

        String filePath = "..\\" + File.separator + fileName;
        File invoiceFile = new File(filePath);

        if (invoiceFile.exists()) {
            try {
                Path path = invoiceFile.toPath();

                ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);

                return ResponseEntity.ok()
                        .headers(headers)
                        .contentLength(invoiceFile.length())
                        .contentType(MediaType.APPLICATION_PDF)
                        .body(resource);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        } else {
            System.out.println("Invoice not found");
            return ResponseEntity.notFound().build();
        }
    }
}
