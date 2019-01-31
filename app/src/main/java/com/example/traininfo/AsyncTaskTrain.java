package com.example.traininfo;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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

    private final ArrayList<DATrain> mTrainList = new ArrayList<>();
    private final LinkedList<String> mdetail = new LinkedList<>();
    private final LinkedList<String> mnearPlaces = new LinkedList<>();
    private final ArrayList<com.example.traininfo.Status> mEntity = new ArrayList<>();

    private double latitude;
    private double longitude;

    public static final float NEAR_KM = 10000;

    public AsyncTaskTrain(Context context, String station) {
        this.context = context;
        this.station = station;
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
        if (integers[0] == 1) {

            //parser.getXML(context, "https://stingray.isti.cnr.it:8443/serviziosupervisionestazione/rfi/FrontEnd/Train/GetPlaces");

            ArrayList<HashMap<String, String>> places = null;
            try {
                places = Parser.getStation(context);
            } catch (TimeoutException e) {
                return "timeout";
            } catch (InterruptedException e) {
                return "interrupt";
            } catch (ExecutionException e) {
                return "execute";
            }

            int i;
            for (i = 0; i < places.size(); i++) {
                if (places.get(i).containsValue(station)) {
                    Intent intent = new Intent(context, DAInfo.class);
                    if (integers[1] == 1) {
                        intent.putExtra(TYPE, context.getString(R.string.arrivals_intent));
                    } else {
                        intent.putExtra(TYPE, context.getString(R.string.departures_intent));
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
            ArrayList<HashMap<String, String>> places = null;
            try {
                places = Parser.getStation(context);
            } catch (TimeoutException e) {
                return "timeout";
            } catch (InterruptedException e) {
                return "interrupt";
            } catch (ExecutionException e) {
                return "execute";
            }

            Log.d("TAG", "" + latitude);
            Log.d("TAg", "" + longitude);

            for (int i = 0; i < places.size(); i++) {
                float[] result = new float[1];

                //Log.d("Latitude", places.get(i).get("Latitude"));
                String latitudeString = places.get(i).get("Latitude");
                //Log.d("Longitude", places.get(i).get("Longitude"));
                String longitudeString = places.get(i).get("Longitude");

                double latitudePlace;
                if (latitudeString.isEmpty())
                    continue;

                latitudePlace = Double.parseDouble(places.get(i).get("Latitude"));

                double longitudePlace;
                if (longitudeString.isEmpty())
                    continue;

                longitudePlace = Double.parseDouble(places.get(i).get("Longitude"));

                Location.distanceBetween(latitude, longitude, latitudePlace, longitudePlace, result);
                if (result[0] < NEAR_KM) {
                    Log.d("TAG", "Entro 10km");
                    mnearPlaces.add(places.get(i).get("Name"));
                }
            }

            return "places";
        }

        if (integers[0] == 6) {
            ArrayList<HashMap<String, String>> entity = Parser.getEntity(context);

            for (int i = 0; i < entity.size(); i++) {
                String name = entity.get(i).get("Name");
                String pos = entity.get(i).get("Position");
                String cVal = entity.get(i).get("CurrentValue");
                String status = entity.get(i).get("Status");
                com.example.traininfo.Status s = new com.example.traininfo.Status(name, pos, cVal, status);
                mEntity.add(s);
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

        if (result.equals("DA")) {
            delegate.processFinish(mTrainList);
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
            delegate.processFinish(mEntity, 0);
        }
    }
}
