package com.ridesharing.app.impl;

import com.ridesharing.app.dao.UserDao;
import com.ridesharing.app.datastore.DataStore;
import com.ridesharing.app.model.Driver;
import com.ridesharing.app.model.Rider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MatchServiceImplTest {

    private UserDao userDao;
    private MatchServiceImpl matchService;

    private Map<String, Rider> riderData;
    private Map<String, Driver> driverData;
    private Map<String, Set<String>> driverLocationData;

    @BeforeEach
    void setUp() {
        userDao = new UserDaoImpl(); // Using UserDaoImpl directly
        matchService = new MatchServiceImpl(userDao);

        riderData = DataStore.getRiderData();
        driverData = DataStore.getDriverData();
        driverLocationData = DataStore.getDriverLocationData();
        riderData.clear();
        driverData.clear();
        driverLocationData.clear();
        // Initialize test data
        riderData.put("R1", new Rider("R1", 1, 1));
        driverData.put("D1", new Driver("D1", 2, 2));
        driverData.put("D2", new Driver("D2", 3, 3));

        Set<String> driverSet1 = new HashSet<>();
        driverSet1.add("D1");
        driverLocationData.put("2-2", driverSet1);
//4,1  = 4+1 = root(5)
        // 1+4
        Set<String> driverSet2 = new HashSet<>();
        driverSet2.add("D2");
        driverLocationData.put("3-3", driverSet2);
    }

    @Test
    void getMatchedDrivers_success() {
        List<Driver> matchedDrivers = matchService.getMatchedDrivers("R1");
        assertNotNull(matchedDrivers);
        assertEquals(2, matchedDrivers.size());
        assertTrue(matchedDrivers.contains(driverData.get("D1")));
        assertTrue(matchedDrivers.contains(driverData.get("D2")));
    }

    @Test
    void getMatchedDrivers_noRiderFound() {
        List<Driver> matchedDrivers = matchService.getMatchedDrivers("R2");
        assertNotNull(matchedDrivers);
        assertEquals(0, matchedDrivers.size());
    }

    @Test
    void getMatchedDrivers_noDriversAvailable() {
        riderData.put("R2", new Rider("R2", 10, 10)); // Rider far away from all drivers

        List<Driver> matchedDrivers = matchService.getMatchedDrivers("R2");
        assertNotNull(matchedDrivers);
        assertEquals(0, matchedDrivers.size());
    }

    @Test
    void getMatchedDrivers_multipleMatchesWithinRange() {
        riderData.put("R3", new Rider("R3", 1, 2)); // Rider near both drivers

        List<Driver> matchedDrivers = matchService.getMatchedDrivers("R3");
        assertNotNull(matchedDrivers);
        assertEquals(2, matchedDrivers.size());
        assertTrue(matchedDrivers.contains(driverData.get("D1")));
        assertTrue(matchedDrivers.contains(driverData.get("D2")));
    }

    @Test
    void getMatchedDrivers_sameDistanceLexicographicalOrder() {
        riderData.put("R4", new Rider("R4", 4, 1)); // Rider equidistant from D1 and D2

        List<Driver> matchedDrivers = matchService.getMatchedDrivers("R4");
        assertNotNull(matchedDrivers);
        assertEquals(2, matchedDrivers.size());
        assertEquals(driverData.get("D1"), matchedDrivers.get(0));
        assertEquals(driverData.get("D2"), matchedDrivers.get(1));
    }

    @Test
    void getMatchedDrivers_count() {
        driverLocationData.clear();
        setupRiderAndDriver("R5", 0, 0, new String[]{"D1", "D2", "D3", "D4"}, new int[][]{{1, 1}, {2, 2}, {6, 6}, {7, 7}});

        List<Driver> matchedDrivers = matchService.getMatchedDrivers("R5");
        assertNotNull(matchedDrivers);
        assertEquals(2, matchedDrivers.size()); // Only D1 and D2 are within 5 km range of R5 (0, 0)
    }


    // Helper method to set up rider and driver data
    private void setupRiderAndDriver(String riderId, int rx, int ry, String[] driverIds, int[][] driverLocations) {
        riderData.put(riderId, new Rider(riderId, rx, ry));
        for (int i = 0; i < driverIds.length; i++) {
            String driverId = driverIds[i];
            int[] location = driverLocations[i];
            driverData.put(driverId, new Driver(driverId, location[0], location[1]));
            Set<String> driverSet = driverLocationData.computeIfAbsent(location[0] + "-" + location[1], k -> new HashSet<>());
            driverSet.add(driverId);
        }
    }
}
