package com.ridesharing.app.service;

import com.ridesharing.app.model.Driver;
import com.ridesharing.app.model.RideData;

import java.util.List;

public interface RideService {
    RideData createRide(String rideId, String riderId, List<Driver> driverList, int n);
    RideData completeRide(String rideId, int timeTaken, int destX, int destY);
    RideData getRide(String rideId);
}
