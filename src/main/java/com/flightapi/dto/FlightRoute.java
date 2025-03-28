package com.flightapi.dto;

import java.util.List;

public class FlightRoute {
    private List<String> route;
    private int cost;

    public FlightRoute(List<String> route, int cost) {
        this.route = route;
        this.cost = cost;
    }
    public List<String> getRoute() {
        return route;
    }
    public int getCost() {
        return cost;
    }
}
