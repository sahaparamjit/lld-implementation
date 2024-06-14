package com.ridesharing.app.impl;

import com.ridesharing.app.enums.RideStatus;
import com.ridesharing.app.model.Driver;
import com.ridesharing.app.model.RideData;
import com.ridesharing.app.service.MatchService;
import com.ridesharing.app.service.RideManager;
import com.ridesharing.app.service.RideService;
import com.ridesharing.app.util.Util;

import java.util.Collections;
import java.util.List;

public class RideManagerImpl implements RideManager {
    private final RideService rideService;
    private final MatchService matchService;

    public RideManagerImpl(RideService rideService, MatchService matchService) {
        this.rideService = rideService;
        this.matchService = matchService;
    }

    @Override
    public void matchRider(String riderId) {
        List<Driver> list = getMatchedDrivers(riderId);
        if (list.isEmpty()) {
            Util.print("NO_DRIVERS_AVAILABLE");
            return;
        }
        Util.printList(list, "DRIVERS_MATCHED");
    }

    private List<Driver> getMatchedDrivers(String riderId) {
        List<Driver> driverList = matchService.getMatchedDrivers(riderId);
        return driverList.isEmpty() ? Collections.emptyList() : driverList;
    }

    @Override
    public void startRide(String rideId, String riderId, int n) {
        RideData rideData = rideService.getRide(rideId);
        if (rideData != null) {
            Util.print(RideStatus.INVALID_RIDE.name());
            return;
        }
        List<Driver> driverIdList = matchService.getMatchedDrivers(riderId);
        if (driverIdList.size()<n) {
            Util.print(RideStatus.INVALID_RIDE.name());
            return;
        }
        rideData = rideService.createRide(rideId, riderId, driverIdList, n);
        Util.print(rideData);
    }

    @Override
    public void stopRide(String rideId, int x, int y, int time) {
        RideData rideData = rideService.completeRide(rideId, time, x, y);
        Util.print(rideData);
    }

    @Override
    public void getBill(String rideId) {
        RideData rideData = rideService.getRide(rideId);
        Util.printBill(rideData);
    }
}
