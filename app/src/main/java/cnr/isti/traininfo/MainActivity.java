package cnr.isti.traininfo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


import com.cocoahero.android.geojson.GeoJSON;
import com.cocoahero.android.geojson.GeoJSONObject;
import com.google.android.gms.common.util.IOUtils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity implements AsyncResponse {

    private final ArrayList<String> mPlace = new ArrayList<>();
    ArrayAdapter mAdapterPlaces;
    public static final float NEAR_KM = 10000;
    //Location
    private final int REQUEST_LOCATION_PERMISSION = 1;
    Location mLastLocation;
    FusedLocationProviderClient mFusedLocationClient;
    private final ArrayList<Station> mnearPlace = new ArrayList<>();

    StationListAdapter mAdapterNearPlacesDepartures;
    StationListAdapter mAdapterNearPlacesArrivals;

    MeteoListAdapter mAdapterNearPlacesMeteo;

    public Context context = this;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION:
                // If the permission is granted, get the location,
                // otherwise, show a Toast
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    Toast.makeText(this,
                            "Permesso negato",
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mnearPlace.clear();
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = findViewById(R.id.tab_loyout);

        tabLayout.addTab(tabLayout.newTab().setText(R.string.meteo_tab));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.datitreno_tab));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.status_tab));
       // tabLayout.addTab(tabLayout.newTab().setText(R.string.info_tab));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.login_tab));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.about_tab));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = findViewById(R.id.pager);
        final PagerAdapter pAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        FirebaseMessaging.getInstance().subscribeToTopic("Stingray")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "sottoscritto";
                        if (!task.isSuccessful()) {
                            msg = "nonsottoscritto";
                        }
                        Log.d("FirebaseInstance", msg);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("FirebaseInstanceId", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                       // String msg = getString(R.string.msg_token_fmt, token);
                        Log.d("FirebaseInstanceId", token);
                       // Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });

        //inizializzazione del login manager
        new LoginManager(getApplicationContext());
        getLocation();
    }

    public void searchA(View view) {
       // EditText arrivalsText = findViewById(R.id.arrivals_text);
       // String station = arrivalsText.getText().toString();
       // Log.d("TAG", station);
       // new AsyncTaskTrain(this, station).execute(1, 1);
        WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView.loadUrl("http://www.viaggiatreno.it/vt_pax_internet/mobile/");

    }

    public void searchD(View view) {
        EditText departuresText = findViewById(R.id.departures_text);
        String station = departuresText.getText().toString();
        Log.d("TAG", station);
        new AsyncTaskTrain(this, station).execute(1, 0);
    }

    public void searchS(View view) {
        EditText statusText = findViewById(R.id.status_text);
        String entity = statusText.getText().toString();
        Log.d("TAG", entity);

        //       new AsyncTaskTrain(this, entity).execute(6, 2);
        Intent intent = new Intent(this, StatusActivity.class);
        intent.putExtra("STATION", entity);
        startActivity(intent);
    }
    public void searchAll(View view) {
        Intent intent = new Intent(this, StatusActivity.class);
        intent.putExtra("STATION", "All");
        startActivity(intent);
    }

    public void searchInfo(View view) {
        EditText info = findViewById(R.id.info_text);
        String tNumber = info.getText().toString();
        Integer numTrain;
        try {
            numTrain = Integer.parseInt(tNumber);
        } catch (NumberFormatException e) {
            Toast.makeText(this, R.string.info_train_name_not_valid,Toast.LENGTH_SHORT).show();
            return;
        }
        AsyncTaskTrain aTask = new AsyncTaskTrain(this);

        aTask.delegate = this;
        aTask.execute(4, numTrain);
    }

    @Override
    public void processFinish(ArrayList<DATrain> output) {

    }

    @Override
    public void processFinish(LinkedList<String> output, int t) {
        //fix per ricerca treno 11879, esistente (visibile dall'app stessa) ma che fa crashare l'app
        if(output.isEmpty()) {
            Toast.makeText(this,
                    R.string.error_loading_train_information,
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (t == 0) {
            mPlace.clear();
            Log.d("TAG", "processFinish");
            TextView trainNumberInfo = findViewById(R.id.number_train_info);
            trainNumberInfo.setText(R.string.number_train_string);
            TextView trainNumber = findViewById(R.id.number_train_text);
            trainNumber.setText(output.get(0));
            TextView customerInfo = findViewById(R.id.customer_info);
            customerInfo.setText(R.string.customer_string);
            TextView customer = findViewById(R.id.customer_text);
            customer.setText(output.get(1));
            TextView categoryInfo = findViewById(R.id.category_info);
            categoryInfo.setText(R.string.category_string);
            TextView category = findViewById(R.id.category_text);
            category.setText(output.get(2));
            TextView lastCheck = findViewById(R.id.last_check_info);
            lastCheck.setText(R.string.last_check_string);
            TextView placeInfo = findViewById(R.id.last_place_info);
            placeInfo.setText(R.string.place_string);
            TextView place = findViewById(R.id.last_place_text);
            place.setText(output.get(3));
            TextView timeInfo = findViewById(R.id.time_info);
            timeInfo.setText(R.string.time_string);
            TextView time = findViewById(R.id.time_text);
            if(!output.get(4).isEmpty())
                time.setText(output.get(4).substring(11,16));
            TextView delayInfo = findViewById(R.id.delay_info);
            delayInfo.setText(R.string.delay_string);
            TextView delay = findViewById(R.id.delay_text);
            delay.setText(output.get(5));

            for (int i = 6; i < output.size(); i++) {
                mPlace.add(output.get(i));
            }

            mAdapterPlaces = new ArrayAdapter<>(this, R.layout.row, R.id.place_text, mPlace);
            ListView listView = findViewById(R.id.list_places);
            listView.setAdapter(mAdapterPlaces);
        }

        if (t == 1) {
           /* for (int i = 0; i < output.size(); i++) {
                String station = output.get(i);
                int rank = Integer.parseInt(station.substring(0,1));
                Log.d("RANK", ""+rank+"d");
                String stationName = station.substring(1);
                //mnearPlace.add(stationName/*new Station(stationName, rank));
                mnearPlace.add(new Station(stationName, rank));
            }*/

            //Log.d("nearPlaces",mnearPlace.toString());

            mAdapterNearPlacesDepartures = new StationListAdapter(this, mnearPlace, 0);
            RecyclerView recyclerViewDepartures = findViewById(R.id.near_places_departures);
            if(recyclerViewDepartures!=null) {
                recyclerViewDepartures.setAdapter(mAdapterNearPlacesDepartures);
                recyclerViewDepartures.setLayoutManager(new LinearLayoutManager(this));
            }
            mAdapterNearPlacesArrivals = new StationListAdapter(this, mnearPlace, 1);

            mAdapterNearPlacesMeteo = new MeteoListAdapter(this, mnearPlace);
            RecyclerView recyclerViewMeteo = findViewById(R.id.near_places_meteo);
            if(recyclerViewMeteo!=null) {
                recyclerViewMeteo.setAdapter(mAdapterNearPlacesMeteo);
                recyclerViewMeteo.setLayoutManager(new LinearLayoutManager(this));
            }
            /*RecyclerView recyclerViewArrival = findViewById(R.id.near_places_arrival);
            if(recyclerViewArrival!=null) {
                recyclerViewArrival.setAdapter(mAdapterNearPlacesArrivals);
                recyclerViewArrival.setLayoutManager(new LinearLayoutManager(this));
            }
            NearPlaces.setNearPlaces(mnearPlace);*/
        }
    }

    @Override
    public void processFinish(ArrayList<Cmad> output, int t) {

    }

    @Override
    public void processFinish(Station output, int t) {

    }


    public void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        } else {
            mFusedLocationClient.getLastLocation().addOnSuccessListener(
                    new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                mLastLocation = location;
                                Log.d("TAG", "Latitude: " + mLastLocation.getLatitude() + "  Longitude: " + mLastLocation.getLongitude() + "  time:" + mLastLocation.getTime());
                                //NearPlaces.setLatitude(mLastLocation.getLatitude());
                                //NearPlaces.setLongitude(mLastLocation.getLongitude());
                                getListastazioni(context, mLastLocation.getLatitude(), mLastLocation.getLongitude());

                                AsyncTaskTrain aTask = new AsyncTaskTrain(context, mLastLocation.getLatitude(), mLastLocation.getLongitude());

                                aTask.delegate = (AsyncResponse) context;
                                aTask.execute(5);
                            } else {
                                Log.e("TAG", "error");
                            }
                        }

                        private void getListastazioni(Context context, double latitude, double longitude) {
                            try {
                            InputStream jsonFileInputStream = getResources().openRawResource(R.raw.meteo_stazioni_coord);

                           /* BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(jsonFileInputStream));
                            String message = org.apache.commons.io.IOUtils.toString(bufferedReader);
                            JSONTokener tokener = new JSONTokener(message);

                            JSONObject json = new JSONObject(tokener);

                                GeoJSONObject geoJSON = GeoJSON.parse(json);*/
                                GeoJSONObject geoJSON2 = GeoJSON.parse(jsonFileInputStream);

                                JSONObject jsonObj = geoJSON2.toJSON();
                                JSONArray ja_data = jsonObj.getJSONArray("features");
                                int length = ja_data.length();
                                for(int i=0; i<length; i++) {
                                    JSONObject jsonObj2 = ja_data.getJSONObject(i);
                                    JSONArray coordinate = jsonObj2.getJSONObject("geometry").getJSONArray("coordinates");
                                    double logi = (double) coordinate.get(0);
                                    double lati = (double) coordinate.get(1);
                                    float[] result = new float[1];
                                    Location.distanceBetween(latitude, longitude, lati, logi, result);
                                    if (result[0] < NEAR_KM) {
                                        Log.d("TAG", "Entro 10km");
                                        JSONObject properties =  jsonObj2.getJSONObject("properties");
                                        String n = (String) properties.get("name");
                                        String ids = (String) properties.get("id_staz");
                                        int idr = (int)properties.get("id_reg");

                                        mnearPlace.add(new Station(n, 1, ids,idr));
                                    }
                                }
                                //Log.e("TAG", "error");

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    });
        }
    }

}