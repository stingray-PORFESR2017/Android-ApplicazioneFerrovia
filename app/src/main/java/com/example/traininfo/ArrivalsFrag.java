package com.example.traininfo;


import android.app.Activity;
import android.content.Intent;
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
public class ArrivalsFrag extends Fragment {

    private View viewF;

    private final ArrayList<Station> mnearPlaces = new ArrayList<>();
    StationListAdapter mAdapterNearPlacesArrivals;

    public ArrivalsFrag() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayList<Station> nearPlaces = NearPlaces.getNearPlaces();

        for (int i = 0; i < nearPlaces.size(); i++) {
            mnearPlaces.add(nearPlaces.get(i));
        }

        mAdapterNearPlacesArrivals = new StationListAdapter(getActivity(), mnearPlaces, 1);
        RecyclerView recyclerViewArrivals = viewF.findViewById(R.id.near_places_arrival);
        recyclerViewArrivals.setAdapter(mAdapterNearPlacesArrivals);
        recyclerViewArrivals.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        viewF = inflater.inflate(R.layout.fragment_arrivals, container, false);

        return viewF;
    }

}
