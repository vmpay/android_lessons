package com.diploma.vmpay.p0871_asynctaskparams;

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
		mt.execute("file_path_1", "file_path_2", "file_path_3", "file_path_4");
	}

	class MyTask extends AsyncTask<String, Integer, Void>
	{

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			tvInfo.setText("Begin");
		}

		@Override
		protected Void doInBackground(String... urls) {
			try {
				int cnt = 0;
				for (String url : urls) {
					// загружаем файл
					downloadFile(url);
					// выводим промежуточные результаты
					publishProgress(++cnt);
				}
				// разъединяемся
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}


		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			tvInfo.setText("Downloaded " + values[0] + " files");
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			tvInfo.setText("End");
		}


		private void downloadFile(String url) throws InterruptedException {
			TimeUnit.SECONDS.sleep(2);
		}
	}
}
