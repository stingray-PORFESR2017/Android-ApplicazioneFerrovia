package com.example.traininfo;

public class Station {
    private String station;
    private int rank;

    public Station(String s, int r){
        station = s;
        rank = r;
    }

    public String getStation() {
        return station;
    }

    public int getRank() {
        return rank;
    }
}
