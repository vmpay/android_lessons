package com.diploma.vmpay.p1101_dialogfragment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity
{
	Dialog1 dlg1;
	Dialog2 dlg2;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		dlg1 = new Dialog1();
		dlg2 = new Dialog2();
	}

	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btnDlg1:
				dlg1.show(getSupportFragmentManager(), "dlg1");
				break;
			case R.id.btnDlg2:
				dlg2.show(getSupportFragmentManager(), "dlg2");
				break;
			default:
				break;
		}

	}
}
