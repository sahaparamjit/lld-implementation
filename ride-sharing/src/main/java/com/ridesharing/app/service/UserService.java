package com.ridesharing.app.service;

public interface UserService {
    boolean addRider(String id, int x, int y);
    boolean addDriver(String id, int x, int y);
}
