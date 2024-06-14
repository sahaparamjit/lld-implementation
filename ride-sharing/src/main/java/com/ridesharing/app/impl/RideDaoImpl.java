package com.ridesharing.app.impl;

import com.ridesharing.app.dao.RideDao;
import com.ridesharing.app.datastore.DataStore;
import com.ridesharing.app.enums.DriverStatus;
import com.ridesharing.app.enums.RideStatus;
import com.ridesharing.app.model.Driver;
import com.ridesharing.app.model.RideData;
import com.ridesharing.app.model.Rider;

import java.util.Map;

public class RideDaoImpl implements RideDao {
    private final Map<String, RideData> rideDataMap;
    public RideDaoImpl() {
        this.rideDataMap = DataStore.rideDataMap();
    }

    @Override
    public RideData createRide(String rideId, Rider rider, Driver driver) {
        driver.setStatus(DriverStatus.IN_TRANSIT);
        RideData rideData = new RideData(rideId, rider.getUserId(), driver.getUserId(),
                rider.getxCoordinateLocation(), rider.getyCoordinateLocation());
        rideDataMap.put(rideId, rideData);
        return rideData;
    }

    @Override
    public RideData stopRide(String rideId, int timeTaken, int destX, int destY) {
        RideData rideData = rideDataMap.get(rideId);
        if (rideData != null) {
            if (rideData.getRideStatus() == RideStatus.RIDE_STOPPED) {
                return null;
            }
            rideData.stopRide(timeTaken, destX, destY);
            return rideData;
        }
        return rideData;
    }

    @Override
    public RideData getRide(String rideId) {
        return rideDataMap.get(rideId);
    }
}
