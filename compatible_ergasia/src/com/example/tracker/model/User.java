package com.example.tracker.model;

import java.util.HashMap;
import java.util.Map;

public class User {
    private final String name;
    private double totalDistance;
    private double totalDuration;
    private double totalElevationGain;
    private final Map<String, Result> routes;

    public User(String name) {
        this.name = name;
        this.totalDistance = 0;
        this.totalDuration = 0;
        this.totalElevationGain = 0;
        this.routes = new HashMap<>();
    }

    public void addRoute(Result res) {
        this.totalDistance += res.getDistance();
        this.totalDuration += res.getDuration();
        this.totalElevationGain += res.getElevationGain();
        routes.put(res.getFilename(), new Result(res));
    }

    public String getName() {
        return name;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public double getTotalDuration() {
        return totalDuration;
    }

    public double getTotalElevationGain() {
        return totalElevationGain;
    }

    public int getRoutesCompleted() {
        return routes.size();
    }

}
