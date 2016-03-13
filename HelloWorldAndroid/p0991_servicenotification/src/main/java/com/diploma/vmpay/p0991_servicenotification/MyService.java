package com.diploma.vmpay.p0991_servicenotification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import org.xml.sax.helpers.DefaultHandler;

import java.util.concurrent.TimeUnit;

public class MyService extends Service
{
	NotificationManager notificationManager;

	@Override
	public void onCreate() {
		super.onCreate();
		notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	}

	public int onStartCommand(Intent intent, int flags, int startId) {
		Runnable runnable = new Runnable()
		{
			@Override
			public void run()
			{
				try {
					TimeUnit.SECONDS.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				sendNotif(1);
			}
		};
		Thread thread = new Thread(runnable);
		thread.start();
		return super.onStartCommand(intent, flags, startId);
	}

	void sendNotif(int id) {
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
				.setSmallIcon(R.mipmap.ic_launcher)
				.setContentTitle("Event tracker")
				.setContentText("Events received")
				.setAutoCancel(true)
				.setOngoing(false)
				.setLights(Color.RED, 3000, 3000)
						//.setNumber(2)
				//.setVibrate(new long[] { 1000, 1000, 500, 500, 250, 250 })
				.setTicker("Hello, I'm here!");
		NotificationCompat.InboxStyle inboxStyle =
				new NotificationCompat.InboxStyle();
		String[] events = { "event 1", "event 2", "event 3"};
		// Sets a title for the Inbox in expanded layout
		inboxStyle.setBigContentTitle(id + " event tracker details:");
		inboxStyle.setSummaryText("U tebya grustnoe litso. Ulibnis! ;)");
		// Moves events into the expanded layout
		for (int i=0; i < events.length; i++) {

			inboxStyle.addLine(events[i]);
		}
		// Moves the expanded layout object into the notification object.
		mBuilder.setStyle(inboxStyle);

		// Creates an explicit intent for an Activity in your app
		Intent resultIntent = new Intent(this, MainActivity.class);
		resultIntent.putExtra(MainActivity.FILE_NAME, "somefile");
//		PendingIntent pIntent = PendingIntent.getActivity(this, 0, resultIntent, 0);

		// The stack builder object will contain an artificial back stack for the
		// started Activity.
		// This ensures that navigating backward from the Activity leads out of
		// your application to the Home screen.
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		// Adds the back stack for the Intent (but not the Intent itself)
		stackBuilder.addParentStack(MainActivity.class);
		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent =
				stackBuilder.getPendingIntent(
						0,
						PendingIntent.FLAG_UPDATE_CURRENT
				);
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager =
				(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		// mId allows you to update the notification later on.
		mNotificationManager.notify(id, mBuilder.build());

	}

	public IBinder onBind(Intent arg0) {
		return null;
	}
}
