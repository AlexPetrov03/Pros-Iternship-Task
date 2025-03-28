package com.flightapi.service;

public class Flight{
    public String origin;
    public String destination;
    public int cost;

    public Flight(String origin, String destination, int cost) {
        this.origin = origin;
        this.destination = destination;
        this.cost = cost;
    }
}