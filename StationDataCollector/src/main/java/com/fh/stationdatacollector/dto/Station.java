package com.fh.stationdatacollector.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Station {
    public Station(@JsonProperty("customer_id") int customer_id,
                   @JsonProperty("id") int id,
                   @JsonProperty("dbURL") String dbURL,
                   @JsonProperty("lat") double lat,
                   @JsonProperty("lng") double lng) {
        this.customer_id = customer_id;
        this.id = id;
        this.dbURL = dbURL;
        this.lat = lat;
        this.lng = lng;
    }
    public Station() {

    }
    @JsonProperty("customer_id")
    public int customer_id;
    @JsonProperty("id")
    public int id;
    @JsonProperty("dbURL")
    public String dbURL;
    @JsonProperty("lat")
    public double lat;
    @JsonProperty("lng")
    public double lng;
}
