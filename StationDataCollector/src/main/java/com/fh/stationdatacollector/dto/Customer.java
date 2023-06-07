package com.fh.stationdatacollector.dto;

import java.util.ArrayList;
import java.util.List;

public class Customer {

    public Customer(int customer_id) {
        this.customer_id = customer_id;
    }

    int customer_id;
    public List<Double> chargedKWH = new ArrayList<>();
}
