package com.diploma.vmpay.p0951_servicebackpendingintent;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MyService extends Service
{
	final String LOG_TAG = "myLogs";
	ExecutorService es;

	public void onCreate() {
		super.onCreate();
		Log.d(LOG_TAG, "MyService onCreate");
		es = Executors.newFixedThreadPool(2);
	}

	public void onDestroy() {
		super.onDestroy();
		Log.d(LOG_TAG, "MyService onDestroy");
	}

	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(LOG_TAG, "MyService onStartCommand");

		int time = intent.getIntExtra(MainActivity.PARAM_TIME, 1);
		PendingIntent pi = intent.getParcelableExtra(MainActivity.PARAM_PINTENT);

		MyRun mr = new MyRun(time, startId, pi);
		es.execute(mr);

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent)
	{
		// TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("Not yet implemented");
	}

	class MyRun implements Runnable {

		int time;
		int startId;
		PendingIntent pi;

		public MyRun(int time, int startId, PendingIntent pi) {
			this.time = time;
			this.startId = startId;
			this.pi = pi;
			Log.d(LOG_TAG, "MyRun#" + startId + " create");
		}

		public void run() {
			Log.d(LOG_TAG, "MyRun#" + startId + " start, time = " + time);
			try {
				// сообщаем об старте задачи
				pi.send(MainActivity.STATUS_START);

				// начинаем выполнение задачи
				TimeUnit.SECONDS.sleep(time);

				// сообщаем об окончании задачи
				Intent intent = new Intent().putExtra(MainActivity.PARAM_RESULT, time * 100);
				pi.send(MyService.this, MainActivity.STATUS_FINISH, intent);

			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (PendingIntent.CanceledException e) {
				e.printStackTrace();
			}
			stop();
		}

		void stop() {
			Log.d(LOG_TAG, "MyRun#" + startId + " end, stopSelfResult("
					+ startId + ") = " + stopSelfResult(startId));
		}
	}
}
