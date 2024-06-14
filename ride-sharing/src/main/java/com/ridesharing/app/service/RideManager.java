package com.ridesharing.app.service;

public interface RideManager {
    void matchRider(String riderId);
    void startRide(String rideId, String riderId, int n);
    void stopRide(String rideId, int x, int y, int time);
    void getBill(String rideId);
}