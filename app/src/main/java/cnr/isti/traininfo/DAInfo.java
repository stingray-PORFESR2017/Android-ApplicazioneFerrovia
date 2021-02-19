package cnr.isti.traininfo;

import android.content.Intent;


import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.LinkedList;

public class DAInfo extends AppCompatActivity implements AsyncResponse {

    public static final String TYPE = "TYPE";
    public static final String PLACE = "PLACE";
    public static final String STATION = "STATION";

    private final ArrayList<DATrain> mTrainList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private TrainListAdapter tAdapter;

    AsyncTaskTrain aTask = new AsyncTaskTrain(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dainfo);

        aTask.delegate = this;

        initializeData();
    }

    public void initializeData() {
        Intent intent = getIntent();
        String type = intent.getStringExtra(TYPE);
        String placeId = intent.getStringExtra(PLACE);
        Log.d("TAG", placeId);
        Integer pId = Integer.parseInt(placeId);
        Log.d("TAG", "" + pId);
        String station = intent.getStringExtra(STATION);

        if (type.equals(getString(R.string.arrivals_intent))) {
            setTitle("Arrivi in "+station);
            aTask.execute(3, pId);
        } else {
            setTitle("Partenze da "+station);
            aTask.execute(2, pId);
        }
    }

    @Override
    public void processFinish(ArrayList<DATrain> output) {
        for (int i = 0; i < output.size(); i++) {
            mTrainList.add(output.get(i));
        }

        mRecyclerView = findViewById(R.id.recycler_view_da);
        tAdapter = new TrainListAdapter(this, mTrainList);
        mRecyclerView.setAdapter(tAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void processFinish(LinkedList<String> output, int t) {

    }

    @Override
    public void processFinish(ArrayList<Cmad> output, int t) {

    }

    @Override
    public void processFinish(Station output, int t) {

    }
}
