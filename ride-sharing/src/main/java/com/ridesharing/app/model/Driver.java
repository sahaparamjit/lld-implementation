package com.ridesharing.app.model;

import com.ridesharing.app.enums.DriverStatus;

public class Driver extends User{
    private DriverStatus status;

    public DriverStatus getStatus() {
        return status;
    }

    public void setStatus(DriverStatus status) {
        this.status = status;
    }

    public Driver(String id, int x, int y) {
        super(id, x, y);
        this.status = DriverStatus.AVAILABLE;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "driverId=" + this.getUserId() +
                '}';
    }
}
