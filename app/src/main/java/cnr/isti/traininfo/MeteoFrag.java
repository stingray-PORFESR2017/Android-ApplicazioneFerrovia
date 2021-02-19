package cnr.isti.traininfo;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MeteoFrag extends Fragment {



    private View viewF;

    private final ArrayList<Station> mnearPlaces = new ArrayList<>();
    MeteoListAdapter mAdapterNearMeteo;
    private Context context;

    public MeteoFrag(){

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayList<Station> nearPlaces = NearPlaces.getNearPlaces(context, getActivity());

        for (int i = 0; i < nearPlaces.size(); i++) {
            mnearPlaces.add(nearPlaces.get(i));
        }

        mAdapterNearMeteo = new MeteoListAdapter(getActivity(), mnearPlaces);
        RecyclerView recyclerViewDepartures = viewF.findViewById(R.id.near_places_meteo);
        recyclerViewDepartures.setAdapter(mAdapterNearMeteo);
        recyclerViewDepartures.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_meteo, container, false);
        context  = container.getContext();

        viewF = v;
        Log.d("TAG", "MeteoFrag created");

        return v;
    }
}
