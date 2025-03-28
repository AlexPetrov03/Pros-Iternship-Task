package com.flightapi.controller;
import com.flightapi.dto.FlightRequest;
import com.flightapi.service.Flight;
import com.flightapi.service.FlightDataService;
import com.flightapi.service.FlightRouteFinder;
import org.springframework.web.bind.annotation.*;
import com.flightapi.dto.FlightRoute;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/flights")
public class FlightController {
    private final FlightDataService flightDataService;
    private final FlightRouteFinder flightRouteFinder;

    public FlightController(FlightDataService flightDataService, FlightRouteFinder flightRouteFinder) {
        this.flightDataService = flightDataService;
        this.flightRouteFinder = flightRouteFinder;
    }

    @PostMapping("/routes")
    public List<FlightRoute> addRequest(@RequestBody FlightRequest flightRequest) throws IOException {
        List<Flight> flights = flightDataService.loadFlightsFromFile();
        //Explain the contradiction in the task in the documentation
        return flightRouteFinder.getAllRoutesSorted(flights, flightRequest.getOrigin(), flightRequest.getDestination(), flightRequest.getMaxFlights());
    }

}