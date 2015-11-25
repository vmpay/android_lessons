package com.example.andrew.gladiatorsimulator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnAddHP;
    Button btnAddAP;
    Button btnAddCrit;
    Button btnFight;
    Button btnResult;
    Button btnReset;
    TextView tvResult;
    TextView tvStatsLeft;
    TextView tvHP;
    TextView tvAP;
    TextView tvCrit;
    String oper = "";
    String tmp = "Empty";
    String myurl = "";
    String apiKey = "8651fa249e0541e09bf57da564511763";
    int statsleft = 15, hp=0, ap=0, crit=0, lvl=0;
    private static final String TAG = "URL-TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAddHP = (Button) findViewById(R.id.btnAddHP);
        btnAddAP = (Button) findViewById(R.id.btnAddAP);
        btnAddCrit = (Button) findViewById(R.id.btnAddCrit);
        btnFight = (Button) findViewById(R.id.btnFight);
        btnResult = (Button) findViewById(R.id.btnResult);
        btnReset = (Button) findViewById(R.id.btnReset);

        tvResult = (TextView) findViewById(R.id.tvResult);
        tvStatsLeft = (TextView) findViewById(R.id.statsleft_int);
        tvHP = (TextView) findViewById(R.id.hp_int);
        tvAP = (TextView) findViewById(R.id.ap_int);
        tvCrit = (TextView) findViewById(R.id.crit_int);
        btnAddHP.setOnClickListener(this);
        btnAddAP.setOnClickListener(this);
        btnAddCrit.setOnClickListener(this);
        btnFight.setOnClickListener(this);
        btnResult.setOnClickListener(this);
        btnReset.setOnClickListener(this);
        tvResult.setText("Choose stats");
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        switch (v.getId()){
            case R.id.btnAddHP:
                if (statsleft>0){
                    //hp = hp + Integer.parseInt(tvHP.getText().toString());
                    hp = hp + 1;
                    tvHP.setText(""+hp);
                    statsleft = statsleft - 1;
                    tvStatsLeft.setText(""+statsleft);
                }
                else
                    Toast.makeText(this, "No stat points left", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnAddAP:
                if (statsleft>0){
                    ap = ap + 1;
                    tvAP.setText(""+ap);
                    statsleft = statsleft - 1;
                    tvStatsLeft.setText(""+statsleft);
                }
                else
                    Toast.makeText(this, "No stat points left", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnAddCrit:
                if (statsleft>0){
                    crit = crit + 1;
                    tvCrit.setText(""+crit);
                    statsleft = statsleft - 1;
                    tvStatsLeft.setText(""+statsleft);
                }
                else
                    Toast.makeText(this, "No stat points left", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnFight:
                myurl = "https://gladiator274102.azure-api.net/Gladiator/fight?a=" + hp + "&b=" + ap + "&c=" + crit + "&d=" + lvl;
                tmp = SendToAPI(myurl);
                //Toast.makeText(this, "Cannot connect", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnResult:
                tvResult.setText(""+tmp);
                break;
            case R.id.btnReset:
                statsleft=15;
                tvStatsLeft.setText(""+statsleft);
                hp=0;
                ap=0;
                crit=0;
                tvHP.setText(""+hp);
                tvAP.setText(""+ap);
                tvCrit.setText(""+crit);
                tmp="Stats have been resetted";
                tvResult.setText(""+tmp);
                break;
            default:
                Log.d(TAG, "Unexpected switch exit");
                break;
        }


    }

    public String SendToAPI(/*final*/ String url) {
        final String myurl = url;
        Log.d(TAG, myurl);
        new Thread() {
            @Override
            public void run() {



                try {
                    URL url = new URL(myurl);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.addRequestProperty("Ocp-Apim-Subscription-Key", apiKey);
                    try {
                        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                        StringBuilder result = new StringBuilder();
                        String line;
                        while((line = reader.readLine()) != null) {
                            result.append(line);
                        }
                        tmp = result.toString();
                    } finally{
                        //Log.d(TAG, "Finally urlConnection.disconnect()");
                        urlConnection.disconnect();
                    }
                } catch (MalformedURLException e) {
                    Log.d(TAG, "MalformedURLException");
                    //e.printStackTrace();
                } catch (IOException e) {
                    Log.d(TAG, "IOException");
                    //e.printStackTrace();
                }
            }
        }.start();
        //Log.d(TAG, "before return tmp=" + tmp);
        return tmp;
    }
}
