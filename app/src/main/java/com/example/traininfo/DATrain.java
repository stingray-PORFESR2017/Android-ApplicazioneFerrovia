package com.example.traininfo;

public class DATrain {
    private String idTrain;
    private String content;
    private String time;
    private String platform;

    public DATrain(String i, String c, String t, String p){
        idTrain = i;
        content = c;
        time = t;
        platform = p;
    }

    public String getIdTrain() {
        return idTrain;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time;
    }

    public String getPlatform() {
        return platform;
    }
}
