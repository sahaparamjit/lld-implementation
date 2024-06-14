package com.ridesharing.app.impl;

import com.ridesharing.app.dao.UserDao;
import com.ridesharing.app.enums.UserType;
import com.ridesharing.app.service.UserService;

public class UserServiceImpl implements UserService {
    UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public boolean addRider(String id, int x, int y) {
        return userDao.addUser(id, x, y, UserType.RIDER);
    }

    @Override
    public boolean addDriver(String id, int x, int y) {
        return userDao.addUser(id, x, y, UserType.DRIVER);
    }
}
