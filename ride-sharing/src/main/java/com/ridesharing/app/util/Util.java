package com.ridesharing.app.util;

import com.ridesharing.app.enums.RideStatus;
import com.ridesharing.app.model.Driver;
import com.ridesharing.app.model.RideData;

import java.util.List;

public class Util {
    private static final int TWO_DECIMAL_PRECISION = 100;
    private static final int SQUARE_POWER = 2;
    private static final int DELETE_CHAR_AT_LAST_INDEX = 1;
    private Util() {}
    public static void print(double amount, String... msg) {
        StringBuilder stringBuilder = new StringBuilder();
        for(String s : msg) stringBuilder.append(s).append(" ");
        System.out.printf(stringBuilder+"%.2f%n", amount);
    }
    public static void print(String... msg) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : msg) {
            stringBuilder.append(s).append(" ");
        }
        System.out.println(stringBuilder.deleteCharAt(stringBuilder.length()-DELETE_CHAR_AT_LAST_INDEX));
    }

    public static void print(RideData rideData) {
        if (rideData == null) {
            Util.print(RideStatus.INVALID_RIDE.name());
            return;
        }
        Util.print(rideData.getRideStatus().name(), rideData.getRideId());
    }

    public static void printBill(RideData rideData) {
        if (rideData == null) {
            Util.print(RideStatus.INVALID_RIDE.name());
            return;
        }
        Util.print(rideData.getBill(), "BILL", rideData.getRideId(), rideData.getDriverId());
    }

    public static void printList(List<Driver> list, String... msg) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : msg) stringBuilder.append(s).append(" ");
        for (Driver s : list) {
            stringBuilder.append(s.getUserId()).append(" ");
        }
        System.out.println(stringBuilder.deleteCharAt(stringBuilder.length()-DELETE_CHAR_AT_LAST_INDEX));
    }

    public static double distance(int x, int y, int orgX, int orgY) {
        double xDistance = Math.pow((orgX - x), SQUARE_POWER);
        double yDistance = Math.pow((orgY - y), SQUARE_POWER);
        return Math.sqrt(xDistance + yDistance);
    }

    public static double makeTwoDecimalPrecision(final double value) {
        double result = value * TWO_DECIMAL_PRECISION;
        result = Math.round(result);
        result = result / TWO_DECIMAL_PRECISION;
        return result;
    }
}
