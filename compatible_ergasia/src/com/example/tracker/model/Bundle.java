package com.example.tracker.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Bundle implements Serializable {
    private final String username;
    private final List<Route> routes;

    public Bundle(List<Route> routes, String username) {
        this.routes = new ArrayList<>(routes);
        this.username = username;
    }

    public Bundle(Bundle other){
        this.routes = new ArrayList<>(other.routes);
        this.username = other.username;
    }

    public String getUsername() {
        return username;
    }

    public List<Route> getRoutes() {
        return routes;
    }
}
