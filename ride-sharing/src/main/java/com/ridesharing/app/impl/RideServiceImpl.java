package com.ridesharing.app.impl;

import com.ridesharing.app.dao.RideDao;
import com.ridesharing.app.dao.UserDao;
import com.ridesharing.app.enums.DriverStatus;
import com.ridesharing.app.model.Driver;
import com.ridesharing.app.model.RideData;
import com.ridesharing.app.model.Rider;
import com.ridesharing.app.service.RideService;

import java.util.List;

public class RideServiceImpl implements RideService {
    private final RideDao rideDao;
    private final UserDao userDao;

    public RideServiceImpl(RideDao rideDao, UserDao userDao) {
        this.rideDao = rideDao;
        this.userDao = userDao;
    }

    @Override
    public RideData createRide(String rideId, String riderId, List<Driver> driverList, int n) {
        RideData rideData = null;
        if (!driverList.isEmpty()) {
            Driver driver =  driverList.get(n-1);
            Rider rider = (Rider) userDao.getUser(riderId);
            if (rider != null && driver.getStatus() == DriverStatus.AVAILABLE) {
                rideData = rideDao.createRide(rideId, rider, driver);
            }
        }
        return rideData;
    }

    @Override
    public RideData completeRide(String rideId, int timeTaken, int destX, int destY) {
        RideData rideData = rideDao.stopRide(rideId, timeTaken, destX, destY);
        if (rideData != null) {
            userDao.updateLocation(rideData.getDriverId(), destX, destY);
            userDao.updateLocation(rideData.getRiderId(), destX, destY);
        }
        return rideData;
    }

    @Override
    public RideData getRide(String rideId) {
        return rideDao.getRide(rideId);
    }
}
