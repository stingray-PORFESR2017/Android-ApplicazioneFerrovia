package com.example.traininfo;

import android.content.Context;
import android.os.AsyncTask;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

class AsyncTaskVerifyCredential extends AsyncTask<Void, Void, Boolean> {

    private String username_typed;
    private String password_typed;
    private Context context;

    private final String login_accepted_response="OK";
    private final String login_refused_responde="You cannot access to this resource"; //per ora il server non risponde nulla
    private final String AuthInfo_url= "https://stingray.isti.cnr.it:8443/serviziosupervisionestazione/CMAD/AuthInfo/";


    public interface AsyncLoginResponse {
        void loginResponse(boolean result);
    }

    public AsyncLoginResponse delegate;


    public AsyncTaskVerifyCredential (String user, String pass, Context c, AsyncLoginResponse asyncLoginResponse) {
        super();
        this.delegate= asyncLoginResponse;
        this.username_typed=user;
        this.password_typed =pass;
        this.context= c;
    }


    @Override
    protected Boolean doInBackground(Void... voids) {

        RequestQueue queue = Volley.newRequestQueue(context);

        RequestFuture<String> future = RequestFuture.newFuture();

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AuthInfo_url+username_typed,
                future, future)
        {
            @Override
            public Map getHeaders() {
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/xml");
                headers.put("accept", "application/xml");
                String creds = String.format("%s:%s",username_typed, password_typed);
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                headers.put("Authorization", auth);
                Log.d("LoginManager", headers.toString());
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
                        "<AuthInfo User=\""+ username_typed +"\">\n" +
                        "\t<id>"+ secureId +"</id>\n" +
                        "</AuthInfo>";

                Log.d("LoginManager", postData);
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
            Log.d("RISPOSTA", response);

        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return false;
        }

        if(response.equals(login_accepted_response)) return true;
        else return false;

    }

    @Override
    protected void onPostExecute(Boolean result) {
        delegate.loginResponse(result);
    }
}
