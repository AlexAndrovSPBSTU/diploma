package com.example.util;

import com.example.models.Coordinate;
import com.example.models.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Component
public class BurnerNumberDetermination {
    private static final HashMap<Integer, double[][]> map = new HashMap<>();

    static {
        map.put(4, new double[][]{
                {13.5, 16.5, 19.5, 21.2},
                {2.75, 3.05, 3.25, 3.38},
                {30.5, 34., 36., 38.1}
        });
        map.put(5, new double[][]{
                {14.5, 16., 19.8},
                {3.62, 3.78, 4.25},
                {41., 42.5, 46.}
        });
        map.put(6, new double[][]{
                {15.2, 16.5},
                {4.45, 4.62},
                {48., 50.}
        });
    }


    public List<Coordinate> getCoordinates(Iterable<Data> dataIterable) {
        List<Coordinate> coordinates = new ArrayList<>();
        for (Data data : dataIterable) {
            double minDistance = Double.MAX_VALUE;
            double currentDistance;
            int burnersNum = -1;

            Set<Integer> keySet = map.keySet();
            for (int burnersNumByTable : keySet) {
                double[][] array = map.get(burnersNumByTable);
                for (int i = 0; i < array[0].length; i++) {
                    currentDistance = Math.sqrt(Math.pow(data.getMasutPresure() - array[0][i], 2)
                            + Math.pow(data.getMasutConsumtion() - array[1][i], 2)
                            + Math.pow(data.getSteamCapacity() - array[2][i], 2));
                    if (currentDistance < minDistance) {
                        minDistance = currentDistance;
                        burnersNum = burnersNumByTable;
                    }
                }
            }
            coordinates.add(Coordinate.builder().time(data.getTime()).y(burnersNum).build());
        }
        return coordinates;
    }
}
