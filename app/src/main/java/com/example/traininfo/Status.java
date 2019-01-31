package com.example.traininfo;

public class Status {
    private String entity;
    private String position;
    private String currentValue;
    private String status;

    public Status(String e, String p, String c, String s){
        entity = e;
        position = p;
        currentValue = c;
        status = s;
    }

    public String getEntity() {
        return entity;
    }

    public String getPosition() {
        return position;
    }

    public String getCurrentValue() {
        return currentValue;
    }

    public String getStatus() {
        return status;
    }
}
