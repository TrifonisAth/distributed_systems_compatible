package com.example.tracker.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Chunk implements Serializable {
    private final List<Waypoint> waypoints;
    private final int index;
    private final String fileName;

    public Chunk(List<Waypoint> wpts, int index) {
        this.waypoints = new ArrayList<>(wpts);
        this.fileName = wpts.get(0).getFileName();
        this.index = index;
    }

    public Chunk(Chunk other) {
        this.index = other.index;
        this.fileName = other.fileName;
        this.waypoints = new ArrayList<>(other.waypoints);
    }

    public int getIndex() {
        return index;
    }

    public String getFileName() {
        return fileName;
    }

    public List<Waypoint> getWaypoints() {
        return waypoints;
    }

}
