package com.example.andrew.gladiatorsimulator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class log_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_activity);
        TextView tvView = (TextView) findViewById(R.id.tvView);
        TextView tvResult = (TextView) findViewById(R.id.tvLogResult);

        Intent intent = getIntent();

        String fightlog = intent.getStringExtra("fightlog");
        String fightresult = intent.getStringExtra("result");
        //fightlog = fightlog.replaceAll("NL", System.getProperty("line.separator"));
        fightlog = fightlog.replaceAll("NL", "\n");
        tvResult.setText("" + fightresult);
        tvView.setText("" + fightlog);
    }
}
