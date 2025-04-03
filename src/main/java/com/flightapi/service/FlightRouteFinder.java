package com.flightapi.service;

import java.util.*;
import com.flightapi.dto.FlightRoute;
import org.springframework.stereotype.Service;

@Service
public class FlightRouteFinder {
    static class Node {
        int data;
        int cost;

        public Node(int data, int cost) {
            this.data = data;
            this.cost = cost;
        }
    }

    private final List<List<Node>> adj = new ArrayList<List<Node>>();
    private final Map<Integer, String> indexToCity = new HashMap<>();
    private final Map<String, Integer> cityToIndex =  new HashMap<>();
    private int cityCount = 0;

    private int getCityIndex(String city) {
        if (!cityToIndex.containsKey(city)) {
            cityToIndex.put(city, cityCount);
            indexToCity.put(cityCount, city);
            adj.add(new ArrayList<>());
            cityCount++;
        }
        return cityToIndex.get(city);
    }

    private void findRoutes(int start, int end, List<FlightRoute> routes, int maxFlights) {
        PriorityQueue<FlightRoute> pq = new PriorityQueue<>(
                Comparator.comparingInt(FlightRoute::getCost).thenComparingInt(route -> route.getRoute().size()));
        Set<String> visitedRoutes = new HashSet<>();
        pq.add(new FlightRoute(new ArrayList<>(List.of(indexToCity.get(start))), 0));

        while (!pq.isEmpty()) {
            FlightRoute currentRoute = pq.poll();
            String lastCity = currentRoute.getRoute().get(currentRoute.getRoute().size() - 1);
            int lastIndex = cityToIndex.get(lastCity);
            int currentCost = currentRoute.getCost();
            int currentFlights = currentRoute.getRoute().size() - 1;

            String pathKey = String.join("" ,currentRoute.getRoute());

            if (lastIndex == end) {
                if (!visitedRoutes.contains(pathKey)) {
                    routes.add(currentRoute);
                    visitedRoutes.add(pathKey);
                }
                continue;
            }

            if (currentFlights >= maxFlights) {
                continue;
            }

            for (Node neighbor : adj.get(lastIndex)) {
                String nextCity = indexToCity.get(neighbor.data);
                if (!currentRoute.getRoute().contains(nextCity)) {
                    List<String> newPath = new ArrayList<>(currentRoute.getRoute());
                    newPath.add(nextCity);
                    pq.add(new FlightRoute(newPath, currentCost + neighbor.cost));
                }
            }
        }
    }

    public List<FlightRoute> getAllRoutesSorted(List<Flight> flights, String startCity, String endCity, int maxFlights) {
        for (Flight flight : flights) {
            addEdge(flight.origin, flight.destination, flight.cost);
        }

        if (!cityToIndex.containsKey(startCity) || !cityToIndex.containsKey(endCity)) {
            throw new IllegalArgumentException("Invalid city code");
        }

        int start = cityToIndex.get(startCity);
        int end = cityToIndex.get(endCity);
        List<FlightRoute> routes = new ArrayList<>();

        findRoutes(start, end, routes, maxFlights);

        return routes;
    }

    private void addEdge(String origin, String destination, int cost) {
        int srcIndex = getCityIndex(origin);
        int destIndex = getCityIndex(destination);
        adj.get(srcIndex).add(new Node(destIndex, cost));
    }
}