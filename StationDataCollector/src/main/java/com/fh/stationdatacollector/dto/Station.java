package com.fh.stationdatacollector.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Station {
    public Station(@JsonProperty("id") int id,
                   @JsonProperty("dbURL") String dbURL,
                   @JsonProperty("lat") double lat,
                   @JsonProperty("lng") double lng) {
        this.id = id;
        this.dbURL = dbURL;
        this.lat = lat;
        this.lng = lng;
    }
    public Station() {

    }

    @JsonProperty("id")
    public int id;
    @JsonProperty("dbURL")
    public String dbURL;
    @JsonProperty("lat")
    public double lat;
    @JsonProperty("lng")
    public double lng;
}
