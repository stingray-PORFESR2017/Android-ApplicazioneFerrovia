package com.example.traininfo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

enum entityType {LUCE, SCALDINA};
enum Command {ON, OFF};

class AsyncTaskCommander extends AsyncTask<Void, Void, Boolean> {

    private final String command_url = "https://stingray.isti.cnr.it:8443/serviziosupervisionestazione/CMAD/update/";

    private Context context;
    private String cmad_addr;
    private String ente_addr;
    private entityType type;
    private Command command;

    private final String success_response="OK";

    public AsyncTaskCommander(Context c, Command command, entityType type, String cmad_addr, String ente_addr) {
        super();
        this.cmad_addr=cmad_addr;
        this.ente_addr=ente_addr;
        this.command=command;
        this.type=type;
        this.context= c;

    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        //non si possono inviare comandi se non si è autenticati
        if(!LoginManager.isSet()) {
            Log.d("AsyncTaskCommander", "Comando non inviato: non si è autenticati");
            return false;
        }

        RequestQueue queue = Volley.newRequestQueue(context);
        RequestFuture<String> future = RequestFuture.newFuture();

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, command_url+getUrlKey(),
                future, future)
        {
            @Override
            public Map getHeaders() {
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/xml");
                headers.put("accept", "application/xml");
                String creds = String.format("%s:%s",LoginManager.getUsername(), LoginManager.getPassword());
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                headers.put("Authorization", auth);
                Log.d("AsyncTaskCommander", headers.toString());
                return headers;
            }

            @Override
            public String getBodyContentType() {
                return "application/xml; charset=" +
                        getParamsEncoding();
            }

            @Override
            public byte[] getBody() {
                String secureId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
                String postData = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                        "<JCMADCommand>\n" +
                            "\t"+getCommand_xml()+"\n" +
                        "\t<Id>"+ secureId +"</Id>\n" +
                        "\t<mac_ADR_CMAD>"+ cmad_addr +"</mac_ADR_CMAD>\n" +
                         getMacEnte_xml() +
                        "</JCMADCommand>";

                Log.d("AsyncTaskCommander", postData);
                try {
                    return postData.getBytes(getParamsEncoding());
                } catch (UnsupportedEncodingException uee) {
                    uee.printStackTrace();
                    return null;
                }
            }

        };

        queue.add(stringRequest);
        String response;
        try {
            response= future.get();
            Log.d("RISPOSTA COMANDO", response);

        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return false;
        }

        if(response.equals(success_response)) return true;
        else return false;

    }

    @Override
    protected void onPostExecute(Boolean result) {
        if(result) {
            if (ente_addr == null) {
                switch (type) {
                    case LUCE:
                        switch (command) {
                            case ON:
                                Toast.makeText(context, R.string.ill_all_on, Toast.LENGTH_SHORT).show();
                                break;
                            case OFF:
                                Toast.makeText(context, R.string.ill_all_off, Toast.LENGTH_SHORT).show();
                                break;
                        }
                        break;
                    case SCALDINA:
                        switch (command) {
                            case ON:
                                Toast.makeText(context, R.string.red_all_on, Toast.LENGTH_SHORT).show();
                                break;
                            case OFF:
                                Toast.makeText(context, R.string.red_all_off, Toast.LENGTH_SHORT).show();
                                break;
                        }
                }
            } else {
                switch (type) {
                    case LUCE:
                        switch (command) {
                            case ON:
                                Toast.makeText(context, R.string.ill_single_on, Toast.LENGTH_SHORT).show();
                                break;
                            case OFF:
                                Toast.makeText(context, R.string.ill_single_off, Toast.LENGTH_SHORT).show();
                                break;
                        }
                        break;
                    case SCALDINA:
                        switch (command) {
                            case ON:
                                Toast.makeText(context, R.string.red_single_on, Toast.LENGTH_SHORT).show();
                                break;
                            case OFF:
                                Toast.makeText(context, R.string.red_single_off, Toast.LENGTH_SHORT).show();
                                break;
                        }
                }
            }
        } else {
            if(!LoginManager.isSet()) {
                Toast.makeText(context, R.string.command_without_login, Toast.LENGTH_SHORT).show();
            } else if (!hasInternetConnection()) {
                Toast.makeText(context, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, R.string.command_error , Toast.LENGTH_SHORT).show();
            }

        }
    }

    private String getCommand_xml() {
        switch (type) {
            case LUCE:
                switch (command) {
                    case ON: return "<command commandill=\"ON\"></command>";
                    case OFF:return "<command commandill=\"OFF\"></command>";
                }
            case SCALDINA:
                switch (command) {
                    case ON: return "<command commandred=\"ON\"></command>";
                    case OFF:return "<command commandred=\"OFF\"></command>";
                }
        }
        return "";
    }

    private String getMacEnte_xml() {
        if(ente_addr!=null) {
            switch (type) {
                case LUCE: return "\t<mac_ADR_ILL>"+ ente_addr +"</mac_ADR_ILL>\n";
                case SCALDINA: return "\t<mac_ADR_RED>"+ ente_addr +"</mac_ADR_RED>\n";
            }
        }

        return "";
    }

    private String getUrlKey () {
            return cmad_addr;

    }

    private boolean hasInternetConnection() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworks = connectivityManager.getActiveNetworkInfo();
        return activeNetworks != null && activeNetworks.isConnected();
    }


}
