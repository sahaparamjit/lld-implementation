package com.ridesharing.app.impl;

import com.ridesharing.app.datastore.DataStore;
import com.ridesharing.app.enums.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class RideManagerImplTest {
    private RideServiceImpl rideService;
    private MatchServiceImpl matchService;
    private RideManagerImpl rideManager;

    @BeforeEach
    void setUp() {
        rideService = new RideServiceImpl(new RideDaoImpl(), new UserDaoImpl());
        matchService = new MatchServiceImpl(new UserDaoImpl());
        rideManager = new RideManagerImpl(rideService, matchService);

        // Set up data for testing
        DataStore.getDriverData().clear();
        DataStore.getRiderData().clear();
        DataStore.getDriverLocationData().clear();

        new UserDaoImpl().addUser("D1", 1, 1, UserType.DRIVER);
        new UserDaoImpl().addUser("D2", 2, 2, UserType.DRIVER);
        new UserDaoImpl().addUser("D3", 6, 6, UserType.DRIVER);
        new UserDaoImpl().addUser("R1", 0, 0, UserType.RIDER);
    }

    @Test
    void matchRider_noDriversAvailable() {
        DataStore.getDriverData().clear();
        DataStore.getDriverLocationData().clear();
        assertDoesNotThrow(() -> rideManager.matchRider("R1"));
    }

    @Test
    void matchRider_driversAvailable() {
        assertDoesNotThrow(() -> rideManager.matchRider("R1"));
    }

    @Test
    void startRide_invalidRideId() {
        new UserDaoImpl().addUser("R2", 0, 0, UserType.RIDER);
        rideManager.startRide("RIDE-101", "R2", 1);
        rideManager.startRide("RIDE-101", "R2", 1);
    }

    @Test
    void startRide_noDriverAvailable() {
        new UserDaoImpl().addUser("R2", 0, 0, UserType.RIDER);
        rideManager.startRide("RIDE-102", "R2", 4);
    }

    @Test
    void startRide_valid() {
        new UserDaoImpl().addUser("R2", 0, 0, UserType.RIDER);
        assertDoesNotThrow(() -> rideManager.startRide("RIDE-103", "R2", 1));
    }

    @Test
    void stopRide_invalidRideId() {
        rideManager.stopRide("RIDE-999", 10, 10, 50);
    }

    @Test
    void stopRide_valid() {
        new UserDaoImpl().addUser("R2", 0, 0, UserType.RIDER);
        rideManager.startRide("RIDE-104", "R2", 1);
        rideManager.stopRide("RIDE-104", 10, 10, 50);
    }

    @Test
    void getBill_invalidRideId() {
        rideManager.getBill("RIDE-999");
    }

    @Test
    void getBill_valid() {
        new UserDaoImpl().addUser("R2", 0, 0, UserType.RIDER);
        rideManager.startRide("RIDE-105", "R2", 1);
        rideManager.stopRide("RIDE-105", 10, 10, 50);
        rideManager.getBill("RIDE-105");
    }
}
