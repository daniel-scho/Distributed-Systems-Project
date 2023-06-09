package com.fh.datacollectiondispatcher.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Station {

    public Station(int id, String dbURL, double lat, double lng) {
        this.id = id;
        this.dbURL = dbURL;
        this.lat = lat;
        this.lng = lng;
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

