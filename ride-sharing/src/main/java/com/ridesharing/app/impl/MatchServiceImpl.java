package com.ridesharing.app.impl;

import com.ridesharing.app.dao.UserDao;
import com.ridesharing.app.enums.DriverStatus;
import com.ridesharing.app.model.Driver;
import com.ridesharing.app.model.Rider;
import com.ridesharing.app.service.MatchService;
import com.ridesharing.app.util.comparator.ComparatorUtil;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MatchServiceImpl implements MatchService {
    private final UserDao userDao;
    private static final int DRIVER_LIMITATIONS = 5;
    public MatchServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<Driver> getMatchedDrivers(String riderId) {
        Rider rider = getRider(riderId);
        List<Driver> driverList = Collections.emptyList();
        if (rider != null) {
            List<String> driverIds = rider.getDriverIds();
            driverList = userDao.getDriversByIds(driverIds);
            if (driverList != null) {
                driverList.sort(ComparatorUtil.getDriverComparator(rider));
            }
        }

        return driverList == null || driverList.isEmpty() ? driverList : getFilteredDrivers(driverList);
    }

    private List<Driver> getFilteredDrivers(List<Driver> driverList) {
        return driverList.stream().filter(driver -> driver.getStatus() != DriverStatus.IN_TRANSIT)
                .limit(DRIVER_LIMITATIONS).collect(Collectors.toList());
    }

    private Rider getRider(String riderId) {
        return (Rider) userDao.getUser(riderId);
    }
}
