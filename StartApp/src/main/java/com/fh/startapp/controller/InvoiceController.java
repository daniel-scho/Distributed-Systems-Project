package com.fh.startapp.controller;


import com.fh.startapp.queue.Send;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class InvoiceController {
    private Send queueSender;

private Send queSender;
    @Autowired
    public InvoiceController(Send queueSender) {
        this.queueSender = queueSender;
    }

    @GetMapping("/invoice/{customer_id}")
    public String processInvoice(@PathVariable("customer_id") String customerId) {
        try {
            queueSender.executeSendQueue(customerId);
            return "Invoice processing initiated for Customer ID: " + customerId;
        } catch (Exception e) {
            return "Error occurred while processing invoice for Customer ID: " + customerId;
        }
    }


    /**
     *
     * @param invoice
     * This route take an input in their body in json format
     * with the attributes of the class WheaterData
     * return: A string is outputed summarizing the inputed data
     * (Tested with Insomnia)

    @PostMapping("/invoice")
    public String receiveData(@RequestBody InvoiceData invoice) {
        return "Id: " + invoice.id;
    }*/
}
