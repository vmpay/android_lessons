package com.example.andrew.gladiatorsimulator;

import android.content.Intent;
import android.os.Handler;
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

    private Button btnFight;
    private Button btnResult;
    private Button btnReset;
    private TextView tvResult;
    private TextView tvStatsLeft;
    private TextView tvHP;
    private TextView tvAP;
    private TextView tvCrit;
    private String logtext = "Empty log";
    private String login = "admin@admin.com";
    private String res = "Empty result";
    private int statsleft = 15;
    private int hp=0;
    private int ap=0;
    private int crit=0;
    private int lvl=0;
    private int bonusstats=0;
    private int i=0;
    private static final String TAG = "URL-TAG";
    private final String[] data = {"Level 0", "Level 1", "Level 2", "Level 3", "pvp"};
    private Intent intent;
    private final String link = "api";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent(this, log_activity.class);

        Button btnAddHP = (Button) findViewById(R.id.btnAddHP);
        Button btnAddAP = (Button) findViewById(R.id.btnAddAP);
        Button btnAddCrit = (Button) findViewById(R.id.btnAddCrit);
        btnFight = (Button) findViewById(R.id.btnFight);
        btnResult = (Button) findViewById(R.id.btnResult);
        btnReset = (Button) findViewById(R.id.btnReset);

        tvResult = (TextView) findViewById(R.id.tvResult);
        tvStatsLeft = (TextView) findViewById(R.id.statsleft_int);
        tvHP = (TextView) findViewById(R.id.hp_int);
        tvAP = (TextView) findViewById(R.id.ap_int);
        tvCrit = (TextView) findViewById(R.id.crit_int);
        TextView tvWelcome = (TextView) findViewById(R.id.tvWelcome);

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
                Log.d(TAG, "lvl = " + position);
                lvl = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        Intent intentinput = getIntent();
        if (intentinput.getStringExtra("login")!=null) {
            login = intentinput.getStringExtra("login");
            bonusstats = parseInt(intentinput.getStringExtra("lvl"), 10);
            statsleft = statsleft + bonusstats;
        }
        
        tvStatsLeft.setText("" + statsleft);
        tvWelcome.setText("Hello, " + login);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        statsleft = savedInstanceState.getInt("statsleft");
        hp = savedInstanceState.getInt("hp");
        ap = savedInstanceState.getInt("ap");
        crit = savedInstanceState.getInt("crit");
        tvStatsLeft.setText("" + statsleft);
        tvHP.setText("" + hp);
        tvAP.setText("" + ap);
        tvCrit.setText("" + crit);
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("statsleft", statsleft);
        outState.putInt("hp", hp);
        outState.putInt("ap", ap);
        outState.putInt("crit", crit);
    }

    @Override
    public void onClick(View v) {
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
                String myurl;
                if (lvl==4)
                    myurl = link + "/fightpvp?login=" + login + "&a=" + hp + "&b=" + ap + "&c=" + crit;
                else
                    myurl = link + "/fight?login=" + login + "&a=" + hp + "&b=" + ap + "&c=" + crit + "&d=" + lvl;
                Toast.makeText(this, "Waiting for server response...", Toast.LENGTH_SHORT).show();
                btnFight.setEnabled(false);
                btnReset.setEnabled(false);
                btnResult.setEnabled(false);
                new SendGlad().execute(myurl);
                break;
            case R.id.btnResult:
                tvResult.setText("" + res);
                // TODO: Add logs layout
                intent.putExtra("fightlog", logtext);
                intent.putExtra("result", res);
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
                String tmp = "Stats have been resetted";
                tvResult.setText(""+ tmp);
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
                    intent.putExtra("fightlog", logtext);
                    intent.putExtra("result", res);
                    startActivity(intent);
                    break;
                case 1:
                    res = "Victory!";
                    intent.putExtra("fightlog", logtext);
                    intent.putExtra("result", res);
                    startActivity(intent);
                    bonusstats += 1;
                    statsleft = 1;
                    tvStatsLeft.setText("" + statsleft);
                    break;
                case 2:
                    res = "Cannot connect to the server...";
                    break;
                case 3:
                    //TODO: waiting for opponents
                    Log.d(TAG, "Waiting for other players");
                    res = "Looking for opponents";
                    if (i>2)
                    {
                        res = "There is no opponents to fight";
                        break;
                    }
                    i++;
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            // Actions to do after 10 seconds
                            String myurl = link + "/fightpvp?login=" + login;
                            //Toast.makeText(this, "Waiting for server response...", Toast.LENGTH_SHORT).show();
                            new SendGlad().execute(myurl);
                        }
                    }, 10000);
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
            if (res!="Looking for opponents")
            {
                btnFight.setEnabled(true);
                btnReset.setEnabled(true);
                btnResult.setEnabled(true);
            }
            tvResult.setText(""+res);
            tmp = "Empty set in OnPostExecute";

        }
    }
}
