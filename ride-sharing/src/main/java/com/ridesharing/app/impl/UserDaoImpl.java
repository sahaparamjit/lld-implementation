package com.ridesharing.app.impl;

import com.ridesharing.app.dao.UserDao;
import com.ridesharing.app.datastore.DataStore;
import com.ridesharing.app.enums.UserType;
import com.ridesharing.app.model.Driver;
import com.ridesharing.app.model.Rider;
import com.ridesharing.app.model.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class UserDaoImpl implements UserDao {

    @Override
    public boolean addUser(String id, int x, int y, UserType type) {
        if (type == UserType.DRIVER) {
            Map<String, Driver> map = DataStore.getDriverData();
            Driver driver = new Driver(id, x, y);
            DataStore.getDriverLocationData().computeIfAbsent(x+"-"+y, k -> new HashSet<>()).add(id);
            map.put(id, driver);
            return true;
        } else if (type == UserType.RIDER) {
            Map<String, Rider> map = DataStore.getRiderData();
            Rider rider = new Rider(id, x, y);
            map.put(id, rider);
            return true;
        }
        return false;
    }

    @Override
    public User getUser(String id) {
        if (DataStore.getRiderData().containsKey(id)) {
            return DataStore.getRiderData().get(id);
        } else if (DataStore.getDriverData().containsKey(id)) {
            return DataStore.getDriverData().get(id);
        }
        return null;
    }

    public List<Driver> getDriversByIds(List<String> ids) {
        Map<String, Driver> map = DataStore.getDriverData();
        List<Driver> driverList = new ArrayList<>();
        for (String s : ids) {
            if (map.containsKey(s)) {
                driverList.add(map.get(s));
            }
        }
        return driverList;
    }

    @Override
    public void updateLocation(String userId, int x, int y) {
        User user = getUser(userId);
        if (user != null ) user.setLocation(x, y);
    }
}
