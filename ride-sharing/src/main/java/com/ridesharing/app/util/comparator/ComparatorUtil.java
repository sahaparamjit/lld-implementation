package com.ridesharing.app.util.comparator;

import com.ridesharing.app.model.Driver;
import com.ridesharing.app.model.Rider;
import com.ridesharing.app.util.Util;

import java.util.Comparator;

public class ComparatorUtil {
    public static Comparator<Driver> getDriverComparator(Rider rider) {
        Comparator<Driver> comparator  = (a, b) -> {
            double distanceFromRiderA = Util.distance(a.getxCoordinateLocation(),
                    a.getyCoordinateLocation(), rider.getxCoordinateLocation(), rider.getyCoordinateLocation());
            double distanceFromRiderB =
                    Util.distance(b.getxCoordinateLocation(), b.getyCoordinateLocation(),
                            rider.getxCoordinateLocation(), rider.getyCoordinateLocation());
            if (distanceFromRiderA == distanceFromRiderB) return a.getUserId().compareTo(b.getUserId());
            return Double.compare(distanceFromRiderA, distanceFromRiderB);
        };
        return comparator;
    }
}
