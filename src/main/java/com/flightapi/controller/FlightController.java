package com.flightapi.controller;
import com.flightapi.dto.FlightRequest;
import com.flightapi.service.Flight;
import com.flightapi.service.FlightDataService;
import com.flightapi.service.FlightRouteFinder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.flightapi.dto.FlightRoute;

import java.io.IOException;
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
    public ResponseEntity<?> addRequest(@RequestBody FlightRequest flightRequest) {
        try{
            List<Flight> flights = flightDataService.loadFlightsFromFile();

            if(flights.isEmpty()){
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No flights found");
            }

            List<FlightRoute> routes = flightRouteFinder.getAllRoutesSorted(flights, flightRequest.getOrigin(),
                    flightRequest.getDestination(), flightRequest.getMaxFlights());

            if(routes.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No routes found");
            }

            return ResponseEntity.ok(routes);
        }
        catch (IOException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error Reading flight file" + e.getMessage());
        }
        catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid flight input: " + e.getMessage());
        }
    }

}