package com.example.andrew.gladiatorsimulator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
        fightlog = fightlog.replaceAll("NL", "\n");
        tvResult.setText("" + fightresult);
        tvView.setText("" + fightlog);
        Animation anim = null;
        anim = AnimationUtils.loadAnimation(this, R.anim.animresult);
        tvResult.startAnimation(anim);
    }
}
