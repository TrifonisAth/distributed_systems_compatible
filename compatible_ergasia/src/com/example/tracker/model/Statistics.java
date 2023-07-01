package com.example.tracker.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Statistics implements Serializable {
    private final LocalDateTime date;
    private final String username;
    private final int existingUsers;
    private final int totalRoutes;
    private final int userRoutesCompleted;
    private final double totalDistance;
    private final double userDistance;
    private final double totalDuration;
    private final double userDuration;
    private final double totalElevationGain;
    private final double userElevationGain;

    public Statistics(User user, UsersData board) {
        totalDistance = board.getTotalDistance();
        userDistance = user.getTotalDistance();
        totalDuration = board.getTotalDuration();
        userDuration = user.getTotalDuration();
        totalElevationGain = board.getTotalElevationGain();
        userElevationGain = user.getTotalElevationGain();
        totalRoutes = board.getRouteCount();
        userRoutesCompleted = user.getRoutesCompleted();
        existingUsers = board.getUserCount();
        date = LocalDateTime.now();
        username = user.getName();
    }

    public Statistics(Statistics other) {
        totalDistance = other.totalDistance;
        userDistance = other.userDistance;
        totalDuration = other.totalDuration;
        userDuration = other.userDuration;
        totalElevationGain = other.totalElevationGain;
        userElevationGain = other.userElevationGain;
        totalRoutes = other.totalRoutes;
        userRoutesCompleted = other.userRoutesCompleted;
        existingUsers = other.existingUsers;
        date = other.date;
        username = other.username;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public double getUserDistance() {
        return userDistance;
    }

    public double getTotalDuration() {
        return totalDuration;
    }

    public double getUserDuration() {
        return userDuration;
    }

    public double getTotalElevationGain() {
        return totalElevationGain;
    }

    public double getUserElevationGain() {
        return userElevationGain;
    }

    public int getTotalRoutes() {
        return totalRoutes;
    }

    public int getUserRoutesCompleted() {
        return userRoutesCompleted;
    }

    public int getExistingUsers() {
        return existingUsers;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getUsername() {
        return username;
    }

    public String strUserDuration() {
        return (int)userDuration/3600+"h "+(int)(userDuration%3600)/60+"m "+(int)userDuration%60+"s";
    }

    public String strTotalDuration() {
        return (int)totalDuration/3600+"h "+(int)(totalDuration%3600)/60+"m "+(int)totalDuration%60+"s";
    }

    public String strTotalDistance() {
        return Math.round(totalDistance*100)/100.0 +"km";
    }

    public String strUserDistance() {
        return Math.round(userDistance*100)/100.0 +"km";
    }

    public String strTotalElevationGain() {
        return Math.round(totalElevationGain*100)/100.0 +"m";
    }

    public String strUserElevationGain() {
        return Math.round(userElevationGain*100)/100.0 +"m";
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return "Statistics( " +
                "username: " + username +
                ", total distance: " + strTotalDistance() +
                ", user distance: " + strUserDistance() +
                ", total duration: " + strTotalDuration() +
                ", user duration: " + strUserDuration() +
                ", total elevation gain: " + strTotalElevationGain() +
                ", user elevation gain: " + strUserElevationGain() +
                ", existing users: " + existingUsers +
                ", total activities for all users: " + totalRoutes +
                ", activities completed by the user: " + userRoutesCompleted +
                ", current date: " + date.format(formatter) +
                " )";
    }
}
