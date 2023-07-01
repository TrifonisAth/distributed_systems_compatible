package com.example.tracker.model;

import java.io.Serializable;

public class IntermediateResult implements Serializable {
    private final String filename;
    private final int index;
    private final double distance;
    private final double duration;
    private final double elevationGain;

    public IntermediateResult(String filename, int index, double distance, double duration, double elevationGain) {
        this.filename = filename;
        this.index = index;
        this.distance = distance;
        this.duration = duration;
        this.elevationGain = elevationGain;
    }

    public IntermediateResult(IntermediateResult other) {
        this.filename = other.filename;
        this.index = other.index;
        this.distance = other.distance;
        this.duration = other.duration;
        this.elevationGain = other.elevationGain;
    }

    public int getIndex() {
        return index;
    }

    public String getFilename() {
        return filename;
    }

    public double getElevationGain() {
        return elevationGain;
    }

    public double getDuration() {
        return duration;
    }

    public double getDistance() {
        return distance;
    }

}
