package com.diploma.vmpay.p0921_servicesimple;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import java.util.concurrent.TimeUnit;

public class MineService extends Service
{
	final String LOG_TAG = "myLogs";

	public void onCreate()
	{
		super.onCreate();
		Log.d(LOG_TAG, "onCreate");
	}

	public int onStartCommand(Intent intent, int flags, int startId)
	{
		Log.d(LOG_TAG, "onStartCommand");
		someTask();
		return super.onStartCommand(intent, flags, startId);
	}

	public void onDestroy()
	{
		super.onDestroy();
		Log.d(LOG_TAG, "onDestroy");
	}

	public IBinder onBind(Intent intent)
	{
		Log.d(LOG_TAG, "onBind");
		return null;
	}

	void someTask()
	{
		MyTask myTask = new MyTask();
		myTask.execute();
	}

	class MyTask extends AsyncTask <Void, Void, Void>
	{

		@Override
		protected Void doInBackground(Void... params)
		{
			for (int i = 1; i<=5; i++) {
				Log.d(LOG_TAG, "i = " + i);
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			stopSelf();
			return null;
		}
	}
}
