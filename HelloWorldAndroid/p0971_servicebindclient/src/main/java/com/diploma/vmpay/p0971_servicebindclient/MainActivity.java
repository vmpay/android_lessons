package com.diploma.vmpay.p0971_servicebindclient;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity
{
	final String LOG_TAG = "myLogs";

	boolean bound = false;
	ServiceConnection sConn;
	Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//intent = new Intent("com.diploma.vmpay.p0971servicebindclient.MyService");
		intent = new Intent(this, MyService.class);

		sConn = new ServiceConnection() {
			public void onServiceConnected(ComponentName name, IBinder binder) {
				Log.d(LOG_TAG, "MainActivity onServiceConnected");
				bound = true;
			}

			public void onServiceDisconnected(ComponentName name) {
				Log.d(LOG_TAG, "MainActivity onServiceDisconnected");
				bound = false;
			}
		};
	}

	public void onClickStart(View v) {
		startService(intent);
	}

	public void onClickStop(View v) {
		stopService(intent);
	}

	public void onClickBind(View v) {
		bindService(intent, sConn, 0);
	}

	public void onClickUnBind(View v) {
		if (!bound) return;
		unbindService(sConn);
		bound = false;
	}

	protected void onDestroy() {
		super.onDestroy();
		onClickUnBind(null);
	}
}
