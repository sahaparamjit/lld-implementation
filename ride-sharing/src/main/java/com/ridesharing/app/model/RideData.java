package com.ridesharing.app.model;

import com.ridesharing.app.enums.BillConfig;
import com.ridesharing.app.enums.RideMessage;
import com.ridesharing.app.enums.RideStatus;
import com.ridesharing.app.util.Util;

public class RideData {
    private String rideId;
    private String riderId;
    private String driverId;
    private int rideTime;
    private RideStatus rideStatus;
    private int xDest;
    private int yDest;
    private int xSource;
    private int ySource;
    private double bill;
    private final static double ZERO = 0.0;

    public RideData(String id, String riderId, String driverId, int xSource, int ySource) {
        this.setRideId(id);
        this.setRiderId(riderId);
        this.setDriverId(driverId);
        this.setxSource(xSource);
        this.setySource(ySource);
        this.setRideStatus(RideStatus.RIDE_STARTED);
    }

    public void stopRide(int timeTaken, int destX, int destY) {
        this.setRideTime(timeTaken);
        this.setxDest(destX);
        this.setyDest(destY);
        this.setRideStatus(RideStatus.RIDE_STOPPED);
        this.setBill(calculateBill());
    }

    public String getRideId() {
        return rideId;
    }

    public void setRideId(String rideId) {
        this.rideId = rideId;
    }

    public String getRiderId() {
        return riderId;
    }

    public void setRiderId(String riderId) {
        this.riderId = riderId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public int getRideTime() {
        return rideTime;
    }

    public void setRideTime(int rideTime) {
        this.rideTime = rideTime;
    }

    public RideStatus getRideStatus() {
        return rideStatus;
    }

    public void setRideStatus(RideStatus rideStatus) {
        this.rideStatus = rideStatus;
    }

    public double getBill() {
        if (this.getRideStatus() == RideStatus.RIDE_STOPPED) {
            return bill;
        }
        Util.print(RideMessage.RIDE_NOT_COMPLETED.name());
        return ZERO;
    }

    public void setBill(double bill) {
        this.bill = bill;
    }

    public int getxDest() {
        return xDest;
    }

    public void setxDest(int xDest) {
        this.xDest = xDest;
    }

    public int getyDest() {
        return yDest;
    }

    public void setyDest(int yDest) {
        this.yDest = yDest;
    }

    public int getxSource() {
        return xSource;
    }

    public void setxSource(int xSource) {
        this.xSource = xSource;
    }

    public int getySource() {
        return ySource;
    }

    public void setySource(int ySource) {
        this.ySource = ySource;
    }

    private double calculateBill() {
        double val = Util.distance(this.getxDest(), this.getyDest(), this.getxSource(), this.getySource());
        val = Util.makeTwoDecimalPrecision(val);
        double bill = (BillConfig.BASE_FARE_CHARGE.getValue()
                + Util.makeTwoDecimalPrecision((double) this.rideTime * BillConfig.PER_MINUTE_CHARGE.getValue())
                + Util.makeTwoDecimalPrecision(val * BillConfig.PER_KILOMETRE_CHARGE.getValue()));
        bill += bill * BillConfig.SERVICE_TAX_CHARGE.getValue();
        return bill;
    }
}
