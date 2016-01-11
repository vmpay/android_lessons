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

        Intent intent = getIntent();

        String fightlog = intent.getStringExtra("fightlog");
        fightlog = fightlog.replaceAll("NL", System.getProperty("line.separator"));
        tvView.setText("" + fightlog);
    }
}
