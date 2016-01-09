package com.example.andrew.gladiatorsimulator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.Integer.parseInt;

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
    TextView tvWelcome;
    String tmp = "Empty";
    String logtext = "Empty log";
    String myurl = "";
    String login = "admin@admin.com";
    String res = "";
    int statsleft = 15, hp=0, ap=0, crit=0, lvl=0, bonusstats=0;
    private static final String TAG = "URL-TAG";
    String[] data = {"Level 0", "Level 1", "Level 2", "Level 3"};
    @Override
    protected void onRestart() {
        super.onRestart();
        //Log.d(TAG, "MainActivity: onRestart() ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Log.d(TAG, "MainActivity: onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Log.d(TAG, "MainActivity: onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Log.d(TAG, "MainActivity: onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Log.d(TAG, "MainActivity: onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Log.d(TAG, "MainActivity: onDestroy()");
    }

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
        tvWelcome = (TextView) findViewById(R.id.tvWelcome);

        btnAddHP.setOnClickListener(this);
        btnAddAP.setOnClickListener(this);
        btnAddCrit.setOnClickListener(this);
        btnFight.setOnClickListener(this);
        btnResult.setOnClickListener(this);
        btnReset.setOnClickListener(this);
        //tvResult.setText("Choose stats");

        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        // заголовок
        spinner.setPrompt("Choose enemy level");
        // выделяем элемент
        spinner.setSelection(lvl);
        // устанавливаем обработчик нажатия
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // записываем позицию нажатого элемента
                lvl = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        Intent intent = getIntent();
        if (intent.getStringExtra("login")!=null) {
            login = intent.getStringExtra("login");
            bonusstats = parseInt(intent.getStringExtra("lvl"), 10);
            statsleft = statsleft + bonusstats;
        }
        tvStatsLeft.setText("" + statsleft);
        tvWelcome.setText("Hello, " + login);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        tvResult.setText("");
        switch (v.getId()){
            case R.id.btnAddHP:
                if (statsleft>0){
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
                if (statsleft>0) {
                    Toast.makeText(this, statsleft + " stat points left. Distribute it.", Toast.LENGTH_SHORT).show();
                    break;
                }
                myurl = "https://gladiator274102.azure-api.net/Gladiator/fight?login=" + login + "&a=" + hp + "&b=" + ap + "&c=" + crit + "&d=" + lvl;
                Toast.makeText(this, "Waiting for server response...", Toast.LENGTH_SHORT).show();
                new SendGlad().execute(myurl);
                break;
            case R.id.btnResult:
                tvResult.setText(""+res);
                // TODO: Add logs layout
                Intent intent = new Intent(this, log_activity.class);
                intent.putExtra("fightlog", logtext);
                startActivity(intent);
                break;
            case R.id.btnReset:
                statsleft = 15 + bonusstats;
                tvStatsLeft.setText(""+statsleft);
                hp = 0;
                ap = 0;
                crit = 0;
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

    private class SendGlad extends SendToApi {

        @Override
        protected void onPostExecute(String result) {
            Log.d(TAG, "Зашли в OnPostEx result= " + result);
            res = result.substring(0, 2);
            logtext = result.substring(2);
            int code = parseInt(res, 10);
            switch (code){
                case 0:
                    res = "Defeat!";
                    break;
                case 1:
                    res = "Victory!";
                    statsleft = 1;
                    tvStatsLeft.setText("" + statsleft);
                    break;
                case 2:
                    res = "Cannot connect to the server...";
                    break;
                case 32:
                    res = "Table not found.";
                    break;
                case 33:
                    res = "Something goes wrong  - Empty code.";
                    break;
                case 34:
                    res = "Authentification failed. Check Primary & Secondary keys.";
                    break;
                case 35:
                    res = "Send mail error.";
                    break;
                default:
                    res = "Unknown code result: "+code;
                    break;
            }

            tvResult.setText(""+res);
            tmp = "Empty set in OnPostExecute";

        }
    }
}
