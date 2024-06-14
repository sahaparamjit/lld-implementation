package com.ridesharing.app.dao;

import com.ridesharing.app.enums.UserType;
import com.ridesharing.app.model.Driver;
import com.ridesharing.app.model.User;

import java.util.List;

public interface UserDao {
    boolean addUser(String id, int x, int y, UserType userType);
    User getUser(String id);
    List<Driver> getDriversByIds(List<String> ids);
    void updateLocation(String userId, int x, int y);
}
