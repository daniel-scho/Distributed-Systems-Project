package com.fh.stationdatacollector.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Customer {

    public Customer(int customer_id) {
        this.customer_id = customer_id;
    }
    @JsonProperty("customer_id")
    public int customer_id;
    @JsonProperty("chargedKWH")
    public List<Double> chargedKWH = new ArrayList<>();
}