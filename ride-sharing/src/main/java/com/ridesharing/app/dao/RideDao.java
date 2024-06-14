package com.ridesharing.app.dao;

import com.ridesharing.app.model.Driver;
import com.ridesharing.app.model.RideData;
import com.ridesharing.app.model.Rider;

public interface RideDao {
    RideData createRide(String rideId, Rider rider, Driver driver);
    RideData stopRide(String rideId, int timeTaken, int destX, int destY);
    RideData getRide(String rideId);
}
