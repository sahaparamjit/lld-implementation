package com.ridesharing.app.impl;

import com.ridesharing.app.datastore.DataStore;
import com.ridesharing.app.enums.UserType;
import com.ridesharing.app.model.Driver;
import com.ridesharing.app.model.Rider;
import com.ridesharing.app.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoImplTest {

    private UserDaoImpl userDao;

    private Map<String, Driver> driverData;
    private Map<String, Rider> riderData;
    private Map<String, Set<String>> driverLocationData;

    @BeforeEach
    void setUp() {
        userDao = new UserDaoImpl();
        driverData = DataStore.getDriverData();
        riderData = DataStore.getRiderData();
        driverLocationData = DataStore.getDriverLocationData();
    }

    @Test
    void addUser_driver_success() {
        boolean result = userDao.addUser("D1", 1, 1, UserType.DRIVER);
        assertTrue(result);
        assertTrue(driverData.containsKey("D1"));
        assertEquals("D1", driverData.get("D1").getUserId());
        assertTrue(driverLocationData.containsKey("1-1"));
        assertTrue(driverLocationData.get("1-1").contains("D1"));
    }

    @Test
    void addUser_rider_success() {
        boolean result = userDao.addUser("R1", 2, 2, UserType.RIDER);
        assertTrue(result);
        assertTrue(riderData.containsKey("R1"));
        assertEquals("R1", riderData.get("R1").getUserId());
    }

    @Test
    void addUser_invalidType() {
        boolean result = userDao.addUser("U1", 3, 3, null);
        assertFalse(result);
    }

    @Test
    void getUser_driver() {
        Driver driver = new Driver("D1", 1, 1);
        driverData.put("D1", driver);
        User user = userDao.getUser("D1");
        assertNotNull(user);
        assertEquals("D1", user.getUserId());
        assertTrue(user instanceof Driver);
    }

    @Test
    void getUser_rider() {
        Rider rider = new Rider("R1", 2, 2);
        riderData.put("R1", rider);
        User user = userDao.getUser("R1");
        assertNotNull(user);
        assertEquals("R1", user.getUserId());
        assertTrue(user instanceof Rider);
    }

    @Test
    void getUser_nonExistent() {
        User user = userDao.getUser("U1");
        assertNull(user);
    }

    @Test
    void updateLocation_driver() {
        Driver driver = new Driver("D1", 1, 1);
        driverData.put("D1", driver);
        userDao.updateLocation("D1", 5, 5);
        assertEquals(5, driver.getxCoordinateLocation());
        assertEquals(5, driver.getyCoordinateLocation());
    }

    @Test
    void updateLocation_rider() {
        Rider rider = new Rider("R1", 2, 2);
        riderData.put("R1", rider);
        userDao.updateLocation("R1", 6, 6);
        assertEquals(6, rider.getxCoordinateLocation());
        assertEquals(6, rider.getyCoordinateLocation());
    }

    @Test
    void updateLocation_nonExistentUser() {
        assertDoesNotThrow(() -> userDao.updateLocation("U1", 3, 3));
    }

    @Test
    void getDriversByIds() {
        Driver driver1 = new Driver("D1", 1, 1);
        Driver driver2 = new Driver("D2", 2, 2);
        driverData.put("D1", driver1);
        driverData.put("D2", driver2);

        List<Driver> drivers = userDao.getDriversByIds(Arrays.asList("D1", "D2", "D3"));
        assertEquals(2, drivers.size());
        assertTrue(drivers.contains(driver1));
        assertTrue(drivers.contains(driver2));
    }
}
