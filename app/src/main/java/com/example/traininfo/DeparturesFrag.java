package com.example.traininfo;


import android.content.pm.PackageManager;
import android.location.Location;
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
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;

import java.util.ArrayList;
import java.util.LinkedList;


/**
 * A simple {@link Fragment} subclass.
 */
public class DeparturesFrag extends Fragment {

    private View viewF;

    private final ArrayList<String> mnearPlaces = new ArrayList<>();
    ArrayAdapter<String> mAdapterNearPlacesDepartures;

    public DeparturesFrag() {
        // Required empty public constructor
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        final ArrayList<String> nearPlaces = new ArrayList<>();

        ListView listView = viewF.findViewById(R.id.near_places_departures);
        for (int i = 0; i < listView.getAdapter().getCount(); i++) {
            nearPlaces.add(listView.getAdapter().getItem(i).toString());
        }

        Log.d("TAG", nearPlaces.toString());
        outState.putStringArrayList("nearPlace", nearPlaces);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            ArrayList<String> nearPlaces = savedInstanceState.getStringArrayList("nearPlace");

            for (int i = 0; i < nearPlaces.size(); i++) {
                mnearPlaces.add(nearPlaces.get(i));
            }

            mAdapterNearPlacesDepartures = new ArrayAdapter<>(getActivity(), R.layout.row, R.id.place_text, mnearPlaces);
            ListView listViewDepartures = viewF.findViewById(R.id.near_places_departures);
            listViewDepartures.setAdapter(mAdapterNearPlacesDepartures);

            listViewDepartures.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.d("TAG", mnearPlaces.get(position));
                    EditText departuresText = viewF.findViewById(R.id.departures_text);
                    departuresText.setText(mnearPlaces.get(position));
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_departures, container, false);

        viewF = v;
        Log.d("TAG", "DeparturesFrag created");

        return v;
    }
}
