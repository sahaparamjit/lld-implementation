package com.ridesharing.app.model;

public abstract class User {
    private String userId;
    private int xCoordinateLocation;
    private int yCoordinateLocation;

    public User(String userId, int xCoordinateLocation, int yCoordinateLocation) {
        this.userId = userId;
        this.xCoordinateLocation = xCoordinateLocation;
        this.yCoordinateLocation = yCoordinateLocation;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getxCoordinateLocation() {
        return xCoordinateLocation;
    }

    public void setxCoordinateLocation(int xCoordinateLocation) {
        this.xCoordinateLocation = xCoordinateLocation;
    }

    public int getyCoordinateLocation() {
        return yCoordinateLocation;
    }

    public void setyCoordinateLocation(int yCoordinateLocation) {
        this.yCoordinateLocation = yCoordinateLocation;
    }

    public void setLocation(int x, int y) {
        this.xCoordinateLocation = x;
        this.yCoordinateLocation = y;
    }
}
