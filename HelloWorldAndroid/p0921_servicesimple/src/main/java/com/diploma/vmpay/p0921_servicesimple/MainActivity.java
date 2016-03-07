package com.diploma.vmpay.p0921_servicesimple;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity
{
	final String LOG_TAG = "myLogs";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void onClickStart(View v) {
		startService(new Intent(this, MineService.class));
	}

	public void onClickStop(View v) {
		stopService(new Intent(this, MineService.class));
	}
}
