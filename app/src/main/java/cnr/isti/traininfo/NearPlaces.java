package cnr.isti.traininfo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.cocoahero.android.geojson.GeoJSON;
import com.cocoahero.android.geojson.GeoJSONObject;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

public class NearPlaces {
    private static final ArrayList<Station> nearPlaces = new ArrayList<>();
    private static double latitude;
    private static double longitude;

    public static void setNearPlaces(ArrayList<Station> np) {
        nearPlaces.clear();
        for (int i = 0; i < np.size(); i++) {
            nearPlaces.add(np.get(i));
        }
    }

    public static ArrayList<Station> getNearPlaces() {
        if(nearPlaces.isEmpty()){

        }
        return nearPlaces;
    }

    public static ArrayList<Station> getNearPlaces(Context context, FragmentActivity a) {
        if(nearPlaces.isEmpty()){
            getLocation(context,a);
        }
        return nearPlaces;
    }

    public static void setLatitude(double l) {
        latitude = l;
    }

    public static void setLongitude(double l) {
        longitude = l;
    }

    public static double getLatitude() {
        return latitude;
    }

    public static double getLongitude() {
        return longitude;
    }




    public static void getLocation(Context context, FragmentActivity a) {
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(a, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
            getListastazioni(context,43.72767, 10.392116);
           /* mFusedLocationClient.getLastLocation().addOnSuccessListener(
                    new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                Location mLastLocation = location;
                                Log.d("TAG", "Latitude: " + mLastLocation.getLatitude() + "  Longitude: " + mLastLocation.getLongitude() + "  time:" + mLastLocation.getTime());
                                //NearPlaces.setLatitude(mLastLocation.getLatitude());
                                //NearPlaces.setLongitude(mLastLocation.getLongitude());
                                getListastazioni(context,mLastLocation.getLatitude(), mLastLocation.getLongitude());

                            } else {
                                Log.e("TAG", "error");
                            }
                        }


                    });*/

        }
    }

    private static void getListastazioni( Context context,double latitude, double longitude) {
        try {
            InputStream jsonFileInputStream = context.getResources().openRawResource(R.raw.meteo_stazioni_coord);

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
                if (result[0] < 10000) {
                    Log.d("TAG", "Entro 10km");
                    JSONObject properties =  jsonObj2.getJSONObject("properties");
                    String n = (String) properties.get("name");
                    String ids = (String) properties.get("id_staz");
                    int idr = (int)properties.get("id_reg");

                    nearPlaces.add(new Station(n, 1, ids,idr));
                }
            }
            //Log.e("TAG", "error");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
