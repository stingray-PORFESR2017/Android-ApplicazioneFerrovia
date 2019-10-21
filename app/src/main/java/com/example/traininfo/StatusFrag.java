package com.example.traininfo;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.LinkedList;


/**
 * A simple {@link Fragment} subclass.
 */
public class StatusFrag extends Fragment {

    private View viewF;

    private final ArrayList<Station> mnearPlaces = new ArrayList<>();
    StationListAdapter mAdapterNearPlacesStatus;

    public StatusFrag() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayList<Station> nearPlaces= NearPlaces.getNearPlaces();

        for (int i = 0; i < nearPlaces.size(); i++) {
            mnearPlaces.add(nearPlaces.get(i));
        }
/*
        mAdapterNearPlacesStatus = new StationListAdapter(getActivity(), mnearPlaces, 2);
        RecyclerView recyclerViewStatus = viewF.findViewById(R.id.near_places_status);
        recyclerViewStatus.setAdapter(mAdapterNearPlacesStatus);
        recyclerViewStatus.setLayoutManager(new LinearLayoutManager(getActivity()));*/
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewF = inflater.inflate(R.layout.fragment_status, container, false);

        /*ArrayList<Station> output = NearPlaces.getNearPlaces();
        for (int i = 0; i < output.size(); i++) {
            mnearPlaces.add(output.get(i));
        }

        mAdapterNearPlacesStatus = new StationListAdapter(getActivity(), mnearPlaces, 2);
        RecyclerView recyclerViewStatus = viewF.findViewById(R.id.near_places_status);
        recyclerViewStatus.setAdapter(mAdapterNearPlacesStatus);
        recyclerViewStatus.setLayoutManager(new LinearLayoutManager(getActivity()));

        /*AsyncTaskTrain aTask = new AsyncTaskTrain(getActivity(), NearPlaces.getLatitude(), NearPlaces.getLongitude());

        aTask.delegate = this;
        aTask.execute(5);*/

        return viewF;
    }

    /*@Override
    public void processFinish(LinkedList<String> output, int t) {
        for (int i = 0; i < output.size(); i++) {
            mnearPlaces.add(output.get(i));
        }

        mAdapterNearPlacesStatus = new ArrayAdapter<>(getActivity(), R.layout.row, R.id.place_text, mnearPlaces);
        ListView listViewStatus = viewF.findViewById(R.id.near_places_status);
        listViewStatus.setAdapter(mAdapterNearPlacesStatus);

        listViewStatus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EditText statusText = viewF.findViewById(R.id.status_text);
                statusText.setText(mnearPlaces.get(position));
            }
        });
    }*/

}
