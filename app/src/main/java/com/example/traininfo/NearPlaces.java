package com.example.traininfo;

import java.util.ArrayList;

public class NearPlaces {
    private static final ArrayList<Station> nearPlaces = new ArrayList<>();
    private static double latitude;
    private static double longitude;

    public static void setNearPlaces(ArrayList<Station> np) {
        nearPlaces.clear();
        for (int i = 0; i < np.size(); i++) {
            nearPlaces.add(np.get(i));
        }
    }

    public static ArrayList<Station> getNearPlaces() {
        return nearPlaces;
    }

    public static void setLatitude(double l) {
        latitude = l;
    }

    public static void setLongitude(double l) {
        longitude = l;
    }

    public static double getLatitude() {
        return latitude;
    }

    public static double getLongitude() {
        return longitude;
    }
}
