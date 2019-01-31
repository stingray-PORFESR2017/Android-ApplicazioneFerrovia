package com.example.traininfo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class StatusFrag extends Fragment {

    View v;
    private final ArrayList<String> mnearPlace = new ArrayList<>();
    ArrayAdapter<String> mAdapterNearPlaces;

    public StatusFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_status, container, false);

        mnearPlace.add("PISA C.LE");
        mnearPlace.add("ROMA C.LE");
        mnearPlace.add("MILANO C.LE");

        mAdapterNearPlaces = new ArrayAdapter<>(getActivity(), R.layout.row, R.id.place_text, mnearPlace);
        ListView listViewDepartures = v.findViewById(R.id.near_places_status);
        listViewDepartures.setAdapter(mAdapterNearPlaces);

        listViewDepartures.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EditText departuresText = v.findViewById(R.id.status_text);
                departuresText.setText(mnearPlace.get(position));
            }
        });

        return v;
    }

}
