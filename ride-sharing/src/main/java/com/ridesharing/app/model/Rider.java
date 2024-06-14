package com.ridesharing.app.model;

import com.ridesharing.app.datastore.DataStore;
import com.ridesharing.app.util.Util;

import java.util.*;

public class Rider extends User {
    private final static int[][] POSSIBLE_LOCATIONS =
            {{-1,-1}, {1,1}, {1,-1}, {-1, 1}, {1, 0}, {0, 1}, {-1, 0}, {0, -1}};
    private final static double MAX_KMS_RANGE = 5.0;
    public Rider(String riderId, int x, int y) {
        super(riderId, x, y);
    }

    public List<String> getDriverIds() {
        int x = this.getxCoordinateLocation();
        int y = this.getyCoordinateLocation();
        Queue<int[]> queue = new LinkedList<>();
        List<String> list = new ArrayList<>();
        Map<String, Set<String>> driverLocationMap = DataStore.getDriverLocationData();
        queue.add(new int[]{x, y});
        Set<String> visited = new HashSet<>();
        while(!queue.isEmpty()) {
            int[] curr = queue.poll();
            String key = curr[0] + "-" + curr[1];
            if (driverLocationMap.containsKey(key)) {
                list.addAll(driverLocationMap.get(key));
            }
            for (int[] possibleLocation : POSSIBLE_LOCATIONS) {
                int newX = curr[0] + possibleLocation[0];
                int newY = curr[1] + possibleLocation[1];
                String newKey = newX+"-"+newY;
                if (!visited.contains(newKey) && Util.distance(newX, newY, x, y) <= MAX_KMS_RANGE ) {
                    visited.add(newKey);
                    queue.add(new int[]{newX, newY});
                }
            }
        }
        return list;
    }
}
