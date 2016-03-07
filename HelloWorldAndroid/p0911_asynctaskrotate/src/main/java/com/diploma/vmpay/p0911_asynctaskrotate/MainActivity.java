package com.diploma.vmpay.p0911_asynctaskrotate;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity
{
	MyTask mt;
	TextView tv;

	private static final String LOG_TAG = "myLogs";


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Log.d(LOG_TAG, "create MainActivity: " + this.hashCode());

		tv = (TextView) findViewById(R.id.tv);


		mt = (MyTask) getLastCustomNonConfigurationInstance();
		if(mt == null)
		{
			mt = new MyTask();
			mt.execute();
		}
		// передаем в MyTask ссылку на текущее MainActivity
		mt.link(this);

		Log.d(LOG_TAG, "create MyTask: " + mt.hashCode());
	}

	@Override
	public Object onRetainCustomNonConfigurationInstance()
	{
		// удаляем из MyTask ссылку на старое MainActivity
		mt.unLink();
		return mt;
		//return super.onRetainCustomNonConfigurationInstance();
	}

	class MyTask extends AsyncTask<String, Integer, Void>
	{
		MainActivity activity;

		// получаем ссылку на MainActivity
		void link(MainActivity act)
		{
			activity = act;
		}

		// обнуляем ссылку
		void unLink()
		{
			activity = null;
		}

		@Override
		protected Void doInBackground(String... params)
		{
			try
			{
				for(int i = 1; i <= 10; i++)
				{
					TimeUnit.SECONDS.sleep(1);
					publishProgress(i);
					Log.d(LOG_TAG, "i = " + i
							+ ", MyTask: " + this.hashCode()
							+ ", MainActivity: " + activity.hashCode());
				}

			} catch(InterruptedException e)
			{
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values)
		{
			super.onProgressUpdate(values);
			activity.tv.setText("i = " + values[0]);
		}
	}
}
