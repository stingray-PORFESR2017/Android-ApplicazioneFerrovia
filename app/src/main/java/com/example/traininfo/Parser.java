package com.example.traininfo;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Parser {


    private final static String BASE_URL = "https://stingray.isti.cnr.it:8443/serviziosupervisionestazione/rfi/FrontEnd/Train/";
    private final static String STATION = "GetPlaces";
    private final static String DEPARTURES = "GetDepartures?PlaceId=";
    private final static String ARRIVALS = "GetArrivals?PlaceId=";
    private final static String TRAIN_DETAIL = "GetTrainDetails?TrainNumber=";
    private static String xmlString;

    public Parser() {
    }

    private static void getXML(Context context, String url) throws TimeoutException, InterruptedException, ExecutionException {
        RequestQueue queue = Volley.newRequestQueue(context);

        RequestFuture<String> future = RequestFuture.newFuture();
        StringRequest req = new StringRequest(Request.Method.GET, url, future, future);
        queue.add(req);

        try {
            xmlString = future.get(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw e;
        } catch (ExecutionException e) {
            e.printStackTrace();
            throw e;
        } catch (TimeoutException e) {
            e.printStackTrace();
            throw e;
        }

        //Log.d("TAG", xmlString);
    }

    private static Document getDomElement() {
        Document doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource();

            is.setCharacterStream(new StringReader(xmlString));
            doc = db.parse(is);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return null;
        } catch (SAXException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return doc;
    }

    private static String getValue(Element item, String str) {
        NodeList n = item.getElementsByTagName(str);
        return getElementValue(n.item(0));
    }

    private final static String getElementValue(Node elem) {
        Node child;
        if (elem != null) {
            if (elem.hasChildNodes()) {
                for (child = elem.getFirstChild(); child != null; child.getNextSibling()) {
                    if (child.getNodeType() == Node.TEXT_NODE) {
                        return child.getNodeValue();
                    }
                }
            }
        }

        return "";
    }

    private static ArrayList<HashMap<String, String>> getPlaces(Context context) throws TimeoutException, InterruptedException, ExecutionException {
        ArrayList<HashMap<String, String>> places = new ArrayList<>();

        String url = BASE_URL + STATION;
        try {
            getXML(context, url);
        } catch (TimeoutException e) {
            throw e;
        } catch (InterruptedException e) {
            throw e;
        } catch (ExecutionException e) {
            throw e;
        }

        Document doc = getDomElement();

        NodeList nl = doc.getElementsByTagName("LocalitaFrontEnd");

        for (int i = 0; i < nl.getLength(); i++) {
            HashMap<String, String> map = new HashMap<>();
            Element e = (Element) nl.item(i);

            map.put("Id", getValue(e, "Id"));
            map.put("Name", getValue(e, "Name"));

            places.add(map);
        }

        return places;
    }

    private static String getPlacesById(ArrayList<HashMap<String, String>> places, String id) {
        for (int i = 0; i < places.size(); i++) {
            if (places.get(i).get("Id").equals(id)) {
                return places.get(i).get("Name");
            }
        }
        return "";
    }


    public static ArrayList<HashMap<String, String>> getStation(Context context) throws TimeoutException, InterruptedException, ExecutionException {
        ArrayList<HashMap<String, String>> places = new ArrayList<>();

        String url = BASE_URL + STATION;

        try {
            getXML(context, url);
        } catch (TimeoutException e) {
            throw e;
        } catch (InterruptedException e) {
            throw e;
        } catch (ExecutionException e) {
            throw e;
        }

        Log.d("TAG", xmlString);
        /*RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest req = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                processStation(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });

        queue.add(req);*/

        Document doc = getDomElement();

        NodeList nl = doc.getElementsByTagName("LocalitaFrontEnd");
        for (int i = 0; i < nl.getLength(); i++) {
            HashMap<String, String> map = new HashMap<>();

            Element e = (Element) nl.item(i);

            map.put("ID", getValue(e, "Id"));
            map.put("Name", getValue(e, "Name"));
            map.put("Description", getValue(e, "Description"));
            map.put("Latitude", getValue(e, "Latitude"));
            map.put("Longitude", getValue(e, "Longitude"));
            map.put("Categoty", getValue(e, "Category"));

            places.add(map);
        }

        return places;
    }

    public static ArrayList<HashMap<String, String>> getDA(Context context, String id, int type) throws TimeoutException, InterruptedException, ExecutionException {
        ArrayList<HashMap<String, String>> departures = new ArrayList<>();

        String url;
        if (type == 1) {
            url = BASE_URL + ARRIVALS + id;
        } else {
            url = BASE_URL + DEPARTURES + id;
        }

        Log.d("TAG", url);

        try {
            getXML(context, url);
        } catch (TimeoutException e) {
            throw e;
        } catch (InterruptedException e) {
            throw e;
        } catch (ExecutionException e) {
            throw e;
        }

        Log.d("TAG", xmlString);

        Document doc = getDomElement();

        NodeList nl;
        if (type == 1) {
            nl = doc.getElementsByTagName("Arrival");
        } else {
            nl = doc.getElementsByTagName("Departure");
        }

        ArrayList<HashMap<String, String>> places = getPlaces(context);
        for (int i = 0; i < nl.getLength(); i++) {
            HashMap<String, String> map = new HashMap<>();

            Element e = (Element) nl.item(i);

            String trainNumber = getValue(e, "TrainNumber");
            map.put("Number", trainNumber);
            map.put("Platform", getValue(e, "Platform"));
            map.put("Time", getValue(e, "Time"));
            String place = getPlacesById(places, getValue(e, "Place"));
            String content = getValue(e, "Content");
            if (content.equalsIgnoreCase("Ferma a:")) {
                map.put("Content", "PROVENIENTE DA: " + place);
            } else {
                map.put("Content", "PROVENIENTE DA: " + place + "\n" + content);
            }

            departures.add(map);
        }

        return departures;
    }

    public static LinkedList<String> getTrainDetail(Context context, String id) throws TimeoutException, InterruptedException, ExecutionException {
        LinkedList<String> detail = new LinkedList<>();

        String url = BASE_URL + TRAIN_DETAIL + id;

        try {
            getXML(context, url);
        } catch (TimeoutException e) {
            throw e;
        } catch (InterruptedException e) {
            throw e;
        } catch (ExecutionException e) {
            throw e;
        }

        Log.d("TAG", xmlString);

        Document doc = getDomElement();

        NodeList nl = doc.getElementsByTagName("TrainHeader");
        Element e = (Element) nl.item(0);

        detail.add(getValue(e, "Id").substring(9));
        detail.add(getValue(e, "BrandCustomer"));
        detail.add(getValue(e, "BrandCategory"));
        detail.add(getValue(e, "PlaceName"));
        detail.add(getValue(e, "Time"));
        detail.add(getValue(e, "Delay"));

        nl = doc.getElementsByTagName("TrainPath");
        ArrayList<HashMap<String, String>> places = getPlaces(context);
        for (int i = 0; i < nl.getLength(); i++) {
            String trainPath = "";
            Log.d("TAG", "Path");
            e = (Element) nl.item(i);

            String place = getPlacesById(places, getValue(e, "Place"));
            trainPath = trainPath + place + "\n";
            Boolean arrived = Boolean.parseBoolean(getValue(e, "Arrived"));
            if (arrived) {
                NodeList nlD = e.getElementsByTagName("Arrival");
                Element el = (Element) nlD.item(0);
                Boolean cancelled = Boolean.parseBoolean(getValue(e, "Cancelled"));
                if (cancelled) {
                    trainPath = trainPath + "Cancellato in Arrivo" + "\n";
                } else {
                    trainPath = trainPath + "Arrivato: Ora: ";
                    trainPath = trainPath + (getValue(el, "Time").substring(11, 16));
                    trainPath = trainPath + " Piattaforma: " + getValue(el, "Platform");
                    String delay = getValue(el, "Delay");
                    if (delay.isEmpty()) {
                        delay = "0";
                    }
                    trainPath = trainPath + " Ritardo: " + delay + "min\n";
                }
            }

            Boolean departed = Boolean.parseBoolean(getValue(e, "Departed"));
            if (departed) {
                NodeList nlD = e.getElementsByTagName("Departure");
                Element el = (Element) nlD.item(0);
                Boolean cancelled = Boolean.parseBoolean(getValue(e, "Cancelled"));
                if (cancelled) {
                    trainPath = trainPath + "Cancellato in partenza" + "\n";
                } else {
                    trainPath = trainPath + "Partito: Ora: ";
                    trainPath = trainPath + (getValue(el, "Time").substring(11, 16));
                    trainPath = trainPath + " Piattaforma: " + getValue(el, "Platform");
                    String delay = getValue(el, "Delay");
                    if (delay.isEmpty()) {
                        delay = "0";
                    }
                    trainPath = trainPath + " Ritardo: " + delay + "min";

                }
            }

            detail.add(trainPath);

        }

        return detail;
    }

    public static ArrayList<HashMap<String, String>> getEntity(Context context) {
        ArrayList<HashMap<String, String>> entity = new ArrayList<>();
        AssetManager aM = context.getAssets();
        InputStream is = null;
        try {
            is = aM.open("entity_data.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }

        xmlString = readTextFile(is);
        Document doc = getDomElement();

        NodeList nl = doc.getElementsByTagName("entity");
        for (int i = 0; i < nl.getLength(); i++) {
            HashMap<String, String> map = new HashMap<>();

            Element e = (Element) nl.item(i);

            map.put("Name", getValue(e, "name"));
            map.put("Position", getValue(e, "position"));
            map.put("CurrentValue", getValue(e, "currentValue"));
            map.put("Status", getValue(e, "status"));

            entity.add(map);
        }

        return entity;
    }

    private static String readTextFile(InputStream is) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        byte buf[] = new byte[1024];
        int len;
        try {
            while ((len = is.read(buf)) != -1) {
                os.write(buf, 0, len);
            }

            os.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return os.toString();
    }
}
