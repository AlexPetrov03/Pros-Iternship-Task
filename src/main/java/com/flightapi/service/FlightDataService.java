package com.flightapi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class FlightDataService {

    @Value("classpath:flights.txt")
    private Resource flightFile;

    public List<Flight> loadFlightsFromFile() throws IOException {
        List<Flight> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(flightFile.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length != 3) {
                    throw new IllegalArgumentException("Invalid flight data: " + line);
                }

                String origin = tokens[0].trim();
                String destination = tokens[1].trim();

                if(origin.length() != 3 || destination.length() != 3) {
                    throw new IllegalArgumentException("Invalid city data: " + line);
                }

                int cost = Integer.parseInt(tokens[2].trim());

                if(cost <= 0){
                    throw new IllegalArgumentException("Invalid flight cost: " + line);
                }

                result.add(new Flight(origin, destination, cost));
            }
        } catch (IOException e) {
            throw new IOException("Error reading flight file", e);
        }
        return result;
    }
}
