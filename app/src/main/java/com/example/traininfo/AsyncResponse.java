package com.example.traininfo;

import java.util.ArrayList;
import java.util.LinkedList;

public interface AsyncResponse {
    void processFinish(ArrayList<DATrain> output);
    void processFinish(LinkedList<String> output, int t);
    void processFinish(ArrayList<Status> output, int t);
}
