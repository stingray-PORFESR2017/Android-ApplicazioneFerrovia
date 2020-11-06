package com.example.traininfo;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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
import java.time.ZonedDateTime;
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
            map.put("Category", getValue(e, "Category"));

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

        //fix NullPointerException che talvolta si generava cercando un treno anche esistente
        if(doc==null) {
            return detail;
        }

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
    public static ArrayList<Cmad> getEntity(Context context, String macAddr)throws TimeoutException, InterruptedException, ExecutionException {

        ArrayList<Cmad> entity = new ArrayList<>();
        String url;
        if(macAddr.equals("All"))
            url = "https://stingray.isti.cnr.it:8443/serviziosupervisionestazione/CMAD/ALL/";
        else
            url = "https://stingray.isti.cnr.it:8443/serviziosupervisionestazione/CMAD/MAC_ADR_ALL/"+macAddr;
      //  String url = "https://stingray.isti.cnr.it:8443/serviziosupervisionestazione/CMAD/MAC_ADR_ALL/fffe00000007";

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
        if(doc==null){
            return null;
        }
        NodeList nl = doc.getElementsByTagName("ns2:DatiCMAD");
        for (int i = 0; i < nl.getLength(); i++) {

            Element e = (Element) nl.item(i);
            Element e_id = (Element)e.getElementsByTagName("Id").item(0);
            Element e_cmadAnalogInfo = (Element)e.getElementsByTagName("CMAD_ANALOG_INFO").item(0);
            Element e_tensione = (Element)e.getElementsByTagName("Tensione").item(0);
            Element e_corrente = (Element)e.getElementsByTagName("Corrente").item(0);
            Element e_potenzaAttiva = (Element)e.getElementsByTagName("PotenzaAttiva").item(0);
            Element e_potenzaReattiva = (Element)e.getElementsByTagName("PotenzaReattiva").item(0);
            Element e_fattorePotenza = (Element)e.getElementsByTagName("FattorePotenza").item(0);

            String MAC_ADR= getValue(e_id,"MAC_ADR");
            String DATE=  getValue(e_id, "DATE");
            ZonedDateTime z= ZonedDateTime.parse(DATE);
         //   DATE=z.getDayOfMonth()+"-"+z.getDayOfMonth()+"-"+z.getYear()+" "+z.getHour()+":"+z.getMinute()+":"+z.getSecond();
          //  DATE=z.toLocalDate()+" "+z.toLocalTime();
            String CMAD_HEADER=  getValue(e, "CMAD_HEADER");
            String CMAD_TYPE=  getValue(e, "CMAD_TYPE");
            String CMAD_REVISION=  getValue(e, "CMAD_REVISION");
            String CMAD_POSITION=  getValue(e, "CMAD_POSITION");
            String CMAD_DESCRIPTION=  getValue(e, "CMAD_DESCRIPTION");
            String CMAD_LONGITUDE=  getValue(e, "CMAD_LONGITUDE");
            String CMAD_LATITUDE=  getValue(e, "CMAD_LATITUDE");
            String CMAD_DIGITAL_INFO=  getValue(e, "CMAD_DIGITAL_INFO");
            String TempEst=  getValue(e_cmadAnalogInfo, "TempEst");
            String Lux=  getValue(e_cmadAnalogInfo, "Lux");
            String tensioneL1=  getValue(e_tensione, "L1");
            String tensioneL2=  getValue(e_tensione, "L2");
            String tensioneL3=  getValue(e_tensione, "L3");
            String correnteL1=  getValue(e_corrente, "L1");
            String correnteL2=  getValue(e_corrente, "L2");
            String correnteL3= getValue(e_corrente, "L3");
            String potenzaAttivaL1= getValue(e_potenzaAttiva, "L1");
            String potenzaAttivaL2=  getValue(e_potenzaAttiva, "L2");
            String potenzaAttivaL3=  getValue(e_potenzaAttiva, "L3");
            String potenzaReattivaL1=  getValue(e_potenzaReattiva, "L1");
            String potenzaReattivaL2=  getValue(e_potenzaReattiva, "L2");
            String potenzaReattivaL3=  getValue(e_potenzaReattiva, "L3");
            String fattorePotenzaL1=  getValue(e_fattorePotenza, "L1");
            String fattorePotenzaL2=  getValue(e_fattorePotenza, "L2");
            String fattorePotenzaL3=  getValue(e_fattorePotenza, "L3");
            String TempSuolo=  getValue(e_cmadAnalogInfo, "TempSuolo");
            String EnergiaAttiva=  getValue(e_cmadAnalogInfo, "EnergiaAttiva");
            String EnergiaReattiva=  getValue(e_cmadAnalogInfo, "EnergiaReattiva");
            String CMAD_RAW_BASE64=getValue(e,"CMAD_RAW_BASE64");
            String CMAD_CRC=getValue(e,"CMAD_CRC");

            Element e_listMadred= (Element)e.getElementsByTagName("ListMadRed").item(0);
            NodeList n_listMadred = e_listMadred.getElementsByTagName("DatiMadRed");
            ArrayList<Madred> mr=new ArrayList();
            for (int j = 0; j < n_listMadred.getLength(); j++) { //lista madred
                Element e_datiMadred = (Element) n_listMadred.item(j);
                Element e_datiMadred_id = (Element) e_datiMadred.getElementsByTagName("Id").item(0);
                Element e_datiMadred_wireAnalogInfo = (Element) e_datiMadred.getElementsByTagName("WIRE_ANALOG_INFO").item(0);

                String m=getValue(e_datiMadred_id,"MAC_ADR");
                String d=getValue(e_datiMadred_id,"DATE");
                ZonedDateTime zdt= ZonedDateTime.parse(d);
               // d=zdt.toLocalDate()+" "+z.toLocalTime();
                String h=getValue(e_datiMadred,"HEADER");
                String t=getValue(e_datiMadred,"TYPE");
                String r=getValue(e_datiMadred,"REVISION");
                String p=getValue(e_datiMadred,"POSITION");
                String des=getValue(e_datiMadred,"DESCRIPTION");
                String lon=getValue(e_datiMadred,"LONGITUDE");
                String lat=getValue(e_datiMadred,"LATITUDE");
                String di=getValue(e_datiMadred,"DIGITAL_INFO");
                String wdi=getValue(e_datiMadred,"WIRE_DIGITAL_INFO");
                String RAW_BASE64=getValue(e_datiMadred,"RAW_BASE64");
                String CRC=getValue(e_datiMadred,"CRC");
                ArrayList<String> temperatura=new ArrayList<>();
                for(int l=1;l<3;l++){
                    temperatura.add(getValue(e_datiMadred_wireAnalogInfo,"Temperatura"+l));
                }
                ArrayList<String> corrente=new ArrayList<>();
                for(int l=1;l<13;l++){
                    corrente.add(getValue(e_datiMadred_wireAnalogInfo,"ValoreCorrenteCavo"+l));
                }
                mr.add(new Madred(m,d,h,t,r,p,des,lon,lat,di,wdi,temperatura,corrente,RAW_BASE64,CRC));
            }

            Element e_listMadill= (Element)e.getElementsByTagName("ListMadIll").item(0);
            NodeList n_listMadill = e_listMadill.getElementsByTagName("DatiMadIll");
            ArrayList<Madill>ml=new ArrayList();
            for (int j = 0; j < n_listMadill.getLength(); j++) { //lista madill
                Element e_datiMadill = (Element) n_listMadill.item(j);
                Element e_datiMadill_id = (Element) e_datiMadill.getElementsByTagName("Id").item(0);
                Element e_datiMadred_analogInfo = (Element) e_datiMadill.getElementsByTagName("ANALOG_INFO").item(0);

                String m=getValue(e_datiMadill_id,"MAC_ADR");
                String d=getValue(e_datiMadill_id,"DATE");
                ZonedDateTime zdt= ZonedDateTime.parse(d);
               // d=zdt.toLocalDate()+" "+z.toLocalTime();
                String h=getValue(e_datiMadill,"HEADER");
                String t=getValue(e_datiMadill,"TYPE");
                String r=getValue(e_datiMadill,"REVISION");
                String p=getValue(e_datiMadill,"POSITION");
                String des=getValue(e_datiMadill,"DESCRIPTION");
                String lon=getValue(e_datiMadill,"LONGITUDE");
                String lat=getValue(e_datiMadill,"LATITUDE");
                String di=getValue(e_datiMadill,"DIGITAL_INFO");
                String cl=getValue(e_datiMadred_analogInfo,"ComandoLampada");
                String pl=getValue(e_datiMadred_analogInfo,"PotenzaLampada");
                String vl=getValue(e_datiMadred_analogInfo,"VitaLampada");
                String tl=getValue(e_datiMadred_analogInfo,"TensioneLampada");
                String crl=getValue(e_datiMadred_analogInfo,"CorrenteLampada");
                String s=getValue(e_datiMadred_analogInfo,"SCORTA");
                String RAW_BASE64=getValue(e_datiMadill,"RAW_BASE64");
                String CRC=getValue(e_datiMadill,"CRC");
                ml.add(new Madill(m,d,h,t,r,p,des,lon,lat,di,cl,pl,vl,tl,crl,s,RAW_BASE64,CRC));
            }

            Cmad s = new Cmad(MAC_ADR, DATE, CMAD_HEADER, CMAD_TYPE, CMAD_REVISION, CMAD_POSITION, CMAD_DESCRIPTION, CMAD_LONGITUDE, CMAD_LATITUDE, CMAD_DIGITAL_INFO, TempEst, Lux, tensioneL1, tensioneL2, tensioneL3, correnteL1, correnteL2, correnteL3, potenzaAttivaL1, potenzaAttivaL2, potenzaAttivaL3, potenzaReattivaL1, potenzaReattivaL2, potenzaReattivaL3, fattorePotenzaL1, fattorePotenzaL2, fattorePotenzaL3, TempSuolo, EnergiaAttiva, EnergiaReattiva, mr, ml,CMAD_RAW_BASE64,CMAD_CRC);
            entity.add(s);
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