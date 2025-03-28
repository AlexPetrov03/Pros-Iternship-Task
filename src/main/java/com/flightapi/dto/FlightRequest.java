package com.flightapi.dto;

public class FlightRequest {
    private String origin;
    private String destination;
    private int maxFlights = Integer.MAX_VALUE;

    public FlightRequest() {}

    public FlightRequest(String origin, String destination) {
        this.origin = origin;
        this.destination = destination;
    }

    public FlightRequest(String origin, String destination, int maxFlights) {
        this.origin = origin;
        this.destination = destination;
        this.maxFlights = maxFlights;
    }

    public String getOrigin() {
        return origin;
    }


    public String getDestination() {
        return destination;
    }


    public int getMaxFlights() {
        return maxFlights;
    }
}
