package cnr.isti.traininfo;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

       /* mAdapterNearPlacesArrivals = new StationListAdapter(getActivity(), mnearPlaces, 1);
        RecyclerView recyclerViewArrivals = viewF.findViewById(R.id.near_places_arrival);
        recyclerViewArrivals.setAdapter(mAdapterNearPlacesArrivals);
        recyclerViewArrivals.setLayoutManager(new LinearLayoutManager(getActivity()));*/

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        viewF = inflater.inflate(R.layout.fragment_arrivals, container, false);

        WebView myWebView = (WebView) viewF.findViewById(R.id.webview);
        myWebView.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                //view.loadUrl(url);
                System.out.println("hello");
                return false;
            }
        });
        myWebView.loadUrl("https://stingray.isti.cnr.it:8443/serviziosupervisionestazione/pis/viaggiatreno/site/mobile/");//("http://www.viaggiatreno.it/vt_pax_internet/mobile/");

        return viewF;
    }

}
