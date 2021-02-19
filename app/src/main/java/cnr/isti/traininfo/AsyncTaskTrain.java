package cnr.isti.traininfo;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.cocoahero.android.geojson.GeoJSON;
import com.cocoahero.android.geojson.GeoJSONObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class AsyncTaskTrain extends AsyncTask<Integer, String, String> {

    public AsyncResponse delegate = null;

    public static final String TYPE = "TYPE";
    public static final String PLACE = "PLACE";
    public static final String STATION = "STATION";

    private Context context;
    private String station;
    private Station s;

    private final ArrayList<DATrain> mTrainList = new ArrayList<>();
    private final LinkedList<String> mdetail = new LinkedList<>();
    private final LinkedList<String> mnearPlaces = new LinkedList<>();
    private final ArrayList<Cmad> mEntity = new ArrayList<>();

    private double latitude;
    private double longitude;

    public static final float NEAR_KM = 10000;

    public AsyncTaskTrain(Context context, String station) {
        this.context = context;
        this.station = station;
    }

    public AsyncTaskTrain(Context context, Station station) {
        this.context = context;
        this.s = station;
        this.station = s.getStation();
    }

    public AsyncTaskTrain(Context context) {
        this.context = context;
    }

    public AsyncTaskTrain(Context context, double latitude, double longitude) {
        this.context = context;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    protected String doInBackground(Integer... integers) {
        if(integers[0] == 100){
            JSONObject meteo = Parser.getinfoMeteo(context, s);
            if(meteo!=null){
                Intent intent = new Intent(context, MeteoActivity.class);
                intent.putExtra(STATION, station);
                s.put(meteo);
                intent.putExtra("STATIONDATA", s);
                context.startActivity(intent);

            }

            return "meteo";
        }

        if (integers[0] == 1) {

            //parser.getXML(context, "https://stingray.isti.cnr.it:8443/serviziosupervisionestazione/rfi/FrontEnd/Train/GetPlaces");

            ArrayList<HashMap<String, String>> places = null;
            try {
                places = Parser.getStation(context);
            } catch (Exception e) {
                return "timeout";
            }/* catch (InterruptedException e) {
                return "interrupt";
            } catch (ExecutionException e) {
                return "execute";
            }*/

            int i;
            for (i = 0; i < places.size(); i++) {
                if (places.get(i).containsValue(station)) {
                    Intent intent;
                    if (integers[1] == 2) {
                        intent = new Intent(context, StatusActivity.class);
                    } else {
                        intent = new Intent(context, DAInfo.class);
                        if (integers[1] == 1) {
                            intent.putExtra(TYPE, context.getString(R.string.arrivals_intent));
                        } else {
                            intent.putExtra(TYPE, context.getString(R.string.departures_intent));
                        }
                    }

                    intent.putExtra(STATION, station);
                    intent.putExtra(PLACE, places.get(i).get("ID"));
                    context.startActivity(intent);
                    break;
                }
            }

            if (i == places.size()) {
                return "nonL";
            }
        }

        if (integers[0] == 2) {
            ArrayList<HashMap<String, String>> departures = null;
            try {
                departures = Parser.getDA(context, String.valueOf(integers[1]), 0);
            } catch (TimeoutException e) {
                return "timeout";
            } catch (InterruptedException e) {
                return "interrupt";
            } catch (ExecutionException e) {
                return "execute";
            }

            for (int i = 0; i < departures.size(); i++) {
                String tNumber = departures.get(i).get("Number");
                String time = departures.get(i).get("Time").substring(11, 16);
                String platform = departures.get(i).get("Platform");
                if (platform.isEmpty()) {
                    platform = "n.d.";
                }
                String content = departures.get(i).get("Content");
                mTrainList.add(new DATrain(tNumber, content, time, platform));
            }

            return "DA";
        }

        if (integers[0] == 3) {
            ArrayList<HashMap<String, String>> departures = null;
            try {
                departures = Parser.getDA(context, String.valueOf(integers[1]), 1);
            } catch (TimeoutException e) {
                return "timeout";
            } catch (InterruptedException e) {
                return "interrupt";
            } catch (ExecutionException e) {
                return "execute";
            }

            for (int i = 0; i < departures.size(); i++) {
                String tNumber = departures.get(i).get("Number");
                String time = departures.get(i).get("Time");
                String platform = departures.get(i).get("Platform");
                String content = departures.get(i).get("Content");
                mTrainList.add(new DATrain(tNumber, content, time, platform));
            }

            return "DA";
        }

        if (integers[0] == 4) {
            LinkedList<String> detail = null;
            try {
                detail = Parser.getTrainDetail(context, String.valueOf(integers[1]));
            } catch (TimeoutException e) {
                return "timeout";
            } catch (InterruptedException e) {
                return "interrupt";
            } catch (ExecutionException e) {
                return "execute";
            }

            for (int i = 0; i < detail.size(); i++) {
                mdetail.addLast(detail.get(i));
            }

            return "detail";
        }

        if (integers[0] == 5) {


            Log.d("TAG", "" + latitude);
            Log.d("TAg", "" + longitude);
        try{
            InputStream jsonFileInputStream = context.getResources().openRawResource(R.raw.stazioni_coord);

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

                    mnearPlaces.add(n);
                }
            }
           // Log.e("TAG", "error");

        } catch (Exception e) {
            e.printStackTrace();
        }

            return "places";
        }

        if (integers[0] == 6) {
            ArrayList<Cmad> entity;

            try {
                entity= Parser.getEntity(context,station);
            } catch (TimeoutException e) {
                return "timeout";
            } catch (InterruptedException e) {
                return "interrupt";
            } catch (ExecutionException e) {
                return "execute";
            }

	/*		Intent intent;
            intent = new Intent(context, StatusActivity.class);
			intent.putExtra(STATION, station);
            intent.putExtra(PLACE, places.get(i).get("ID"));
            context.startActivity(intent);
*/
	        if(entity==null)
	            return "nonE";
            for (int i = 0; i < entity.size(); i++) {
                mEntity.add(entity.get(i));
            }
            return "entity";
        }

        //Parser parser = new Parser();

        //Log.d("TAG", parser.getStation(context).toString());

        return "";
    }

    protected void onPostExecute(String result) {
        if (result.equals("nonL")) {
            Toast.makeText(context, "Stazione inesistente", Toast.LENGTH_SHORT).show();
        }
        if (result.equals("nonE")) {
            Toast.makeText(context, "Ente inesistente", Toast.LENGTH_SHORT).show();
            delegate.processFinish(mEntity,0);
        }

        if (result.equals("DA")) {
            delegate.processFinish(mTrainList);
        }

        if (result.equals("meteo")) {
            //delegate.processFinish(s,0);
        }

        if (result.equals("detail")) {
            delegate.processFinish(mdetail, 0);
        }

        if (result.equals("places")) {
            delegate.processFinish(mnearPlaces, 1);
        }

        if (result.equals("timeout")) {
            Toast.makeText(context, "Problemi di Connessione", Toast.LENGTH_SHORT).show();
        }

        if (result.equals("interrupt")) {
            Toast.makeText(context, "Qualcosa è andato storto", Toast.LENGTH_SHORT).show();
        }

        if (result.equals("execute")) {
            Toast.makeText(context, "Nessuna connessione", Toast.LENGTH_SHORT).show();
        }

        if (result.equals("entity")) {
            delegate.processFinish(mEntity, 1);
        }
    }
}