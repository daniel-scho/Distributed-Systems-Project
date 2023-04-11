package com.fh.startapp.controller;

import com.fh.startapp.dto.InvoiceData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class InvoiceController {

    /**
     *
     * @param customer_id
     * @return
     */
    @GetMapping("/invoice/{customer_id}")
    public String currentTemperature(@PathVariable String customer_id) {
        return "It is 25 degrees in " + customer_id;
    }

    /**
     *
     * @param invoice
     * This route take an input in their body in json format
     * with the attributes of the class WheaterData
     * return: A string is outputed summarizing the inputed data
     * (Tested with Insomnia)
     */
    @PostMapping("/invoice")
    public String receiveData(@RequestBody InvoiceData invoice) {
        return "Id: " + invoice.id;
    }
}
