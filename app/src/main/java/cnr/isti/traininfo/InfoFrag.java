package cnr.isti.traininfo;


import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFrag extends Fragment {

    //private final ArrayList<String> mPlace = new ArrayList<>();
    /*private final ArrayList<String> mArrivals = new ArrayList<>();
    private final ArrayList<String> mDepartures = new ArrayList<>();*/
    //ArrayAdapter<String> mAdapterPlaces;
    /*ArrayAdapter<String> mAdapterArrivals;
    ArrayAdapter<String> mAdapterDepartures;*/

    public InfoFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_info, container, false);
        EditText editText=v.findViewById(R.id.info_text);
        editText.setTransformationMethod(null);

        /*for (int i = 0; i < 30; i++) {
            mPlace.add("LOCALITA'" + i + "\nNUMERO TRENO" + i + "ORA" + i + " RITARDO"+ i +
                    " BINARIO " + i + "\nNUMERO TRENO" + i + "ORA" + i + " RITARDO"+ i +
                    " BINARIO " + i);
        }

        /*for (int i = 0; i < 9; i++) {
            mPlace.add("Localita'" + i );
            mArrivals.add("NUMERO TRENO" + i + "ORA" + i + " RITARDO"+ i + " BINARIO " + i );
            mDepartures.add("NUMERO TRENO" + i + "ORA" + i + " RITARDO"+ i + " BINARIO " + i);
        }*/

        /*for(int i = 0; i < 6; i++){
            mPlace.add("\n");
            //mArrivals.add("\n");
            //mDepartures.add("\n");
        }*/

        //mAdapterPlaces = new ArrayAdapter<>(getContext(), R.layout.row, R.id.place_text, mPlace);
        /*mAdapterArrivals = new ArrayAdapter<>(getContext(), R.layout.row, R.id.arrivals_text, mPlace);
        mAdapterDepartures = new ArrayAdapter<>(getContext(), R.layout.row, R.id.departures_text, mPlace);*/
        /*ListView listView = v.findViewById(R.id.list_places);
        listView.setAdapter(mAdapterPlaces);*/

        return v;
    }
}