package com.ridesharing.app.service;

import com.ridesharing.app.model.Driver;

import java.util.List;

public interface MatchService {
    List<Driver> getMatchedDrivers(String riderId);
}
