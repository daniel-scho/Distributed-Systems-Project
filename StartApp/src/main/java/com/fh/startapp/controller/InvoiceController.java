package com.fh.startapp.controller;


import com.fh.startapp.queue.Send;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@RestController
public class InvoiceController {
    private Send queueSender;


    @Autowired
    public InvoiceController(Send queueSender) {
        this.queueSender = queueSender;
    }


    /**
     * @param customer_id
     * This route checks for the path of the Invoice PDF
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
     * This route starts the data gathering job
     */



    @GetMapping("/invoices/{customer_id}")
    public String getInvoicePDFPath(@PathVariable("customer_id") String customerId) {
        String fileName = "Invoice.pdf"; // Der Name der Datei, deren Pfad zurückgegeben werden soll
        String projectPath = System.getProperty("user.dir"); // Der Pfad zum Projektordner

        String filePath = projectPath + File.separator + fileName;
        File invoiceFile = new File(filePath);

        if (invoiceFile.exists()) {
            return filePath;
        } else {
            return "Die Datei existiert nicht.";
        }
    }
}
