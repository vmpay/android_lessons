package com.diploma.vmpay.p0801_handler;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
	final String LOG_TAG = "myLogs";

	Handler h;
	TextView tvInfo;
	Button btnStart, btnTest;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tvInfo = (TextView) findViewById(R.id.tvInfo);
		btnStart = (Button) findViewById(R.id.btnStart);
		btnTest = (Button) findViewById(R.id.btnTest);

		btnStart.setOnClickListener(this);
		btnTest.setOnClickListener(this);

		h = new Handler()
		{
			public void handleMessage(android.os.Message msg)
			{
				// обновляем TextView
				tvInfo.setText("Закачано файлов: " + msg.what);
				if(msg.what == 10) btnStart.setEnabled(true);
			}
		};
	}

	void downloadFile()
	{
		// пауза - 1 секунда
		try
		{
			TimeUnit.SECONDS.sleep(1);
		} catch(InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v)
	{
		switch(v.getId())
		{
			case R.id.btnStart:
				btnStart.setEnabled(false);
				Thread t = new Thread(new Runnable()
				{
					public void run()
					{
						for(int i = 1; i <= 10; i++)
						{
							// долгий процесс
							downloadFile();
							h.sendEmptyMessage(i);
							// пишем лог
							Log.d(LOG_TAG, "i = " + i);
						}
					}
				});
				t.start();
				break;
			case R.id.btnTest:
				Log.d(LOG_TAG, "test");
				break;
			default:
				break;
		}
	}
}
