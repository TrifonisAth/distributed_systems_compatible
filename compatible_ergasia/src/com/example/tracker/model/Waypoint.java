package com.example.tracker.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Waypoint implements Serializable {
    private static final long serialVersionUID = 12345678922L;
    private final double latitude;
    private final double longitude;
    private final double elevation;
    private final String creator;
    private final String fileName;
    private final LocalDateTime time;

    public Waypoint(double latitude, double longitude, double elevation, LocalDateTime time, String creator, String fileName) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.elevation = elevation;
        this.time = time;
        this.creator = creator;
        this.fileName = fileName;
    }

    public Waypoint(Waypoint other) {
        this.latitude = other.latitude;
        this.longitude = other.longitude;
        this.elevation = other.elevation;
        this.time = other.time;
        this.creator = other.creator;
        this.fileName = other.fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getElevation() {
        return elevation;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getCreator() {
        return creator;
    }

    @Override
    public String toString() {
        return "Waypoint{" +
                "fileName='" + fileName + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", elevation=" + elevation +
                ", time=" + time +
                ", creator='" + creator + '\'' + '}';
    }
}
