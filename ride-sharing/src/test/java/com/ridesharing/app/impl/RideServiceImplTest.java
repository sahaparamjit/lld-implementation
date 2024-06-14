package com.ridesharing.app.impl;

import com.ridesharing.app.datastore.DataStore;
import com.ridesharing.app.enums.DriverStatus;
import com.ridesharing.app.enums.UserType;
import com.ridesharing.app.model.Driver;
import com.ridesharing.app.model.RideData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RideServiceImplTest {
    private RideServiceImpl rideService;
    private RideDaoImpl rideDao;
    private UserDaoImpl userDao;

    @BeforeEach
    void setUp() {
        rideDao = new RideDaoImpl();
        userDao = new UserDaoImpl();
        rideService = new RideServiceImpl(rideDao, userDao);

        // Set up data for testing
        DataStore.getDriverData().clear();
        DataStore.getRiderData().clear();
        DataStore.getDriverLocationData().clear();

        userDao.addUser("D1", 1, 1, UserType.DRIVER);
        userDao.addUser("D2", 2, 2, UserType.DRIVER);
        userDao.addUser("D3", 6, 6, UserType.DRIVER);
        userDao.addUser("R1", 0, 0, UserType.RIDER);
    }

    @Test
    void createRide_valid() {
        List<Driver> driverList = Arrays.asList(
                (Driver) userDao.getUser("D1"),
                (Driver) userDao.getUser("D2")
        );

        RideData rideData = rideService.createRide("RIDE-101", "R1", driverList, 1);
        assertNotNull(rideData);
        assertEquals("R1", rideData.getRiderId());
        assertEquals("D1", rideData.getDriverId());
        assertEquals(0, rideData.getxSource());
        assertEquals(0, rideData.getySource());
    }

    @Test
    void createRide_noAvailableDriver() {
        List<Driver> driverList = Arrays.asList(
                (Driver) userDao.getUser("D1"),
                (Driver) userDao.getUser("D2")
        );

        // Mark all drivers as not available
        driverList.forEach(driver -> driver.setStatus(DriverStatus.IN_TRANSIT));

        RideData rideData = rideService.createRide("RIDE-101", "R1", driverList, 1);
        assertNull(rideData);
    }

    @Test
    void completeRide_valid() {
        List<Driver> driverList = Arrays.asList(
                (Driver) userDao.getUser("D1")
        );

        RideData rideData = rideService.createRide("RIDE-101", "R1", driverList, 1);
        assertNotNull(rideData);

        RideData completedRideData = rideService.completeRide("RIDE-101", 30, 5, 5);
        assertNotNull(completedRideData);
        assertEquals(30, completedRideData.getRideTime());
        assertEquals(5, completedRideData.getxDest());
        assertEquals(5, completedRideData.getyDest());
    }

    @Test
    void completeRide_invalidRideId() {
        RideData rideData = rideService.completeRide("INVALID-RIDE-ID", 30, 5, 5);
        assertNull(rideData);
    }

    @Test
    void getRide_valid() {
        List<Driver> driverList = Arrays.asList(
                (Driver) userDao.getUser("D1")
        );

        rideService.createRide("RIDE-101", "R1", driverList, 1);
        RideData rideData = rideService.getRide("RIDE-101");
        assertNotNull(rideData);
        assertEquals("R1", rideData.getRiderId());
        assertEquals("D1", rideData.getDriverId());
    }

    @Test
    void getRide_invalidRideId() {
        RideData rideData = rideService.getRide("INVALID-RIDE-ID");
        assertNull(rideData);
    }
}
