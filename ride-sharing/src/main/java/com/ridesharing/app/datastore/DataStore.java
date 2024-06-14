package com.ridesharing.app.datastore;

import com.ridesharing.app.model.Driver;
import com.ridesharing.app.model.RideData;
import com.ridesharing.app.model.Rider;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DataStore {
    private volatile static Map<String, Rider> riderMap;
    private volatile static Map<String, Driver> driverMap;
    private volatile static Map<String, Set<String>> driverLocationMap;
    private volatile static Map<String, RideData> rideDataMap;

    public static Map<String, Rider> getRiderData() {
        if (riderMap == null) {
            synchronized (DataStore.class) {
                if (riderMap == null) {
                    riderMap = new HashMap<>();
                }
            }
        }
        return riderMap;
    }

    public static Map<String, Driver> getDriverData() {
        if (driverMap == null) {
            synchronized (DataStore.class) {
                if (driverMap == null) {
                    driverMap = new HashMap<>();
                }
            }
        }
        return driverMap;
    }

    public static Map<String, Set<String>> getDriverLocationData() {
        if (driverLocationMap == null) {
            synchronized (DataStore.class) {
                if (driverLocationMap == null) {
                    driverLocationMap = new HashMap<>();
                }
            }
        }
        return driverLocationMap;
    }

    public static Map<String, RideData> rideDataMap() {
        if (rideDataMap == null) {
            synchronized (DataStore.class) {
                if (rideDataMap == null) {
                    rideDataMap = new HashMap<>();
                }
            }
        }
        return rideDataMap;
    }

    public static void flush() {
        if (riderMap != null) riderMap.clear();
        if (driverMap != null) driverMap.clear();
        if (driverLocationMap != null) driverLocationMap.clear();
        if (rideDataMap != null) rideDataMap.clear();
    }
}
