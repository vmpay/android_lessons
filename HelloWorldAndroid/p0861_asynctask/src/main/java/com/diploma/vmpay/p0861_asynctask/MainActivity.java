package com.diploma.vmpay.p0861_asynctask;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity
{

	MyTask mt;
	TextView tvInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		tvInfo = (TextView) findViewById(R.id.tvInfo);
	}

	public void onclick(View v) {
		mt = new MyTask();
		mt.execute();
	}

	class MyTask extends AsyncTask<Void, Void, Void>
	{

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			tvInfo.setText("Begin");
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			tvInfo.setText("End");
		}
	}
}
