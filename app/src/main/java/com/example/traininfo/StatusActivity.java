package com.example.traininfo;

import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;

public class StatusActivity extends AppCompatActivity implements AsyncResponse{

    private final ArrayList<Status> mStatusList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private StatusListAdapter sAdapter;

    AsyncTaskTrain aTask = new AsyncTaskTrain(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        Intent intent = getIntent();
        String station = intent.getStringExtra("STATION");
        setTitle("Stato enti "+ station);

        aTask.delegate = this;

        aTask.execute(6);
    }

    @Override
    public void processFinish(ArrayList<DATrain> output) {

    }

    @Override
    public void processFinish(LinkedList<String> output, int t) {

    }

    @Override
    public void processFinish(ArrayList<Status> output, int t) {
        for (int i = 0; i < output.size(); i++) {
            mStatusList.add(output.get(i));
        }

        mRecyclerView = findViewById(R.id.recycler_view_status);
        sAdapter = new StatusListAdapter(this, mStatusList);
        mRecyclerView.setAdapter(sAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
