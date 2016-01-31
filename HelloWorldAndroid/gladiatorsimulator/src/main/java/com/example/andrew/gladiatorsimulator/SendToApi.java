package com.example.andrew.gladiatorsimulator;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class SendToApi extends AsyncTask<String, Void, String> {
    private static final String TAG = "SendToApiClass";
    String tmp="Empty";

    @Override
    protected String doInBackground(String... params) {
        Log.d(TAG, "Зашли в DoInBg: " + params[0]);
        final String myurl = params[0];
        try {
            URL url = new URL(myurl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            //String apiKey = "My-Ocp-Apim-Subscription-Key";
            //urlConnection.addRequestProperty("Ocp-Apim-Subscription-Key", apiKey);
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder result = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null) {
                result.append(line);
            }
            tmp = result.toString();
            urlConnection.disconnect();
        } catch (MalformedURLException e) {
            Log.d(TAG, "MalformedURLException");
            //e.printStackTrace();
        } catch (IOException e) {
            Log.d(TAG, "IOException");
            tmp = "02";// No connection to the server...
            //e.printStackTrace();
        }
        return tmp;
    }
}
