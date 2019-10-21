package com.example.traininfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.LinkedList;

public class StatusActivity extends AppCompatActivity implements AsyncResponse, CmadListAdapter.OnStatusListener {

    private final ArrayList<Cmad> mCmadList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private CmadListAdapter sAdapter;

    AsyncTaskTrain aTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        Intent intent = getIntent();
        String station = intent.getStringExtra("STATION");
        if(station.equals("All"))
            setTitle("Info CMADs");
        else
            setTitle("Info CMAD "+ station);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        aTask = new AsyncTaskTrain(this,station);
        aTask.delegate = this;

        aTask.execute(6);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return true;
    }
    @Override
    public void processFinish(ArrayList<DATrain> output) {

    }

    @Override
    public void processFinish(LinkedList<String> output, int t) {

    }

    @Override
    public void processFinish(ArrayList<Cmad> output, int t) {
        if (t==0){
            this.finish();
        }
        for (int i = 0; i < output.size(); i++) {
            mCmadList.add(output.get(i));
        }
        mRecyclerView = findViewById(R.id.recycler_view_status);
        sAdapter = new CmadListAdapter(this, mCmadList, this);
        mRecyclerView.setAdapter(sAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    @Override
    public void OnCmadClick(int position){
      //  mCmadList.get(position);
        Intent intent = new Intent(this, CmadExtendedInfoActivity.class);
        Log.d("TAGg",Integer.toString(position));
        Log.d("TAGg", mCmadList.get(position).getCmadDescription());
        intent.putExtra("cmad", mCmadList.get(position));
        ArrayList<Madred> mr= mCmadList.get(position).getMadred();
        intent.putParcelableArrayListExtra("madred", mr);
        ArrayList<Madill> ml= mCmadList.get(position).getMadill();
        intent.putParcelableArrayListExtra("madill", ml);

        startActivity(intent);
    }
}


