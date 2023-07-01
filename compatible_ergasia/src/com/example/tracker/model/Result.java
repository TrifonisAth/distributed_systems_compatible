package com.example.tracker.model;

import java.io.Serializable;

public class Result implements Serializable {
    private final String filename;
    private final String username;
    private final double duration;
    private final double distance;
    private final double avgSpeed;
    private final double elevationGain;

    public Result(String username, String filename, double distance, double duration, double elevationGain, double avgSpeed) {
        this.username = username;
        this.filename = filename;
        this.distance = distance;
        this.duration = duration;
        this.elevationGain = elevationGain;
        this.avgSpeed = avgSpeed;
    }

    public Result(Result result) {
        this.username = result.username;
        this.filename = result.filename;
        this.distance = result.distance;
        this.duration = result.duration;
        this.elevationGain = result.elevationGain;
        this.avgSpeed = result.avgSpeed;
    }

    public String getUsername() {
        return username;
    }

    public String getFilename() {
        return filename;
    }

    public double getElevationGain() {
        return elevationGain;
    }

    public String strElevationGain() {
        return Math.round(elevationGain*100)/100.0 +"m";
    }

    public double getDistance() {
        return distance;
    }

    public String strDistance() {
        return Math.round(distance*100)/100.0 +"km";
    }

    public double getDuration() {
        return duration;
    }

    public String strDuration() {
        return (int)duration/3600+"h "+(int)(duration%3600)/60+"m "+(int)duration%60+"s";
    }

    public double getAvgSpeed() {
        return avgSpeed;
    }

    public String strAvgSpeed() {
        return Math.round(avgSpeed*100.0)/100.0 +"km/h";
    }

    @Override
    public String toString() {
        return "Result(" +
                "username: " + username +
                ", route: " + filename +
                ", distance: " + strDistance() +
                ", duration: " + strDuration() +
                ", elevation gain: " + strElevationGain() +
                ", average speed: " + strAvgSpeed() +
                ')';
    }
}
