package com.example.tracker.model;

import java.util.HashMap;
import java.util.Map;

public class UsersData {
    private final Map<String, User> userDirectory;
    private double totalDistance = 0;
    private double totalDuration = 0;
    private double totalElevationGain = 0;
    private int totalRoutes = 0;

    public UsersData() {
        this.userDirectory = new HashMap<>();
    }

    public synchronized void checkUser(String name) {
        if (!userDirectory.containsKey(name)) {
            userDirectory.put(name, new User(name));
        }
    }

    public void addRoute(Result res) {
        User user = userDirectory.get(res.getUsername());
        user.addRoute(res);
        totalRoutes++;
        totalDistance += res.getDistance();
        totalDuration += res.getDuration();
        totalElevationGain += res.getElevationGain();
    }

    public User getUser(String name) {
        return userDirectory.get(name);
    }

    public Statistics getStats(User user) {
        return new Statistics(user, this);
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

    public double getAvgSpeed() {
        return totalDistance / totalDuration;
    }

    public double getAvgDistancePerRoute() {
        return totalDistance / totalRoutes;
    }

    public double getAvgElevationGainPerRoute() {
        return totalElevationGain / totalRoutes;
    }

    public double getAvgDurationPerRoute() {
        return totalDuration / totalRoutes;
    }

    public double getAvgDistancePerUser() {
        return totalDistance / userDirectory.size();
    }

    public double getAvgElevationGainPerUser() {
        return totalElevationGain / userDirectory.size();
    }

    public double getAvgDurationPerUser() {
        return totalDuration / userDirectory.size();
    }

    public int getUserCount() {
        return userDirectory.size();
    }

    public int getRouteCount() {
        return totalRoutes;
    }

}
