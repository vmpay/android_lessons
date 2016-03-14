package com.diploma.vmpay.p1101_dialogfragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Andrew on 14.03.2016.
 */
public class Dialog1 extends DialogFragment implements View.OnClickListener
{
	final String LOG_TAG = "myLogs";

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		getDialog().setTitle("Title!");
		View v = inflater.inflate(R.layout.dialog1, null);
		v.findViewById(R.id.btnYes).setOnClickListener(this);
		v.findViewById(R.id.btnNo).setOnClickListener(this);
		v.findViewById(R.id.btnMaybe).setOnClickListener(this);
		return v;
	}

	public void onClick(View v) {
		Log.d(LOG_TAG, "Dialog 1: " + ((Button) v).getText());
		dismiss();
	}

	public void onDismiss(DialogInterface dialog) {
		super.onDismiss(dialog);
		Log.d(LOG_TAG, "Dialog 1: onDismiss");
	}

	public void onCancel(DialogInterface dialog) {
		super.onCancel(dialog);
		Log.d(LOG_TAG, "Dialog 1: onCancel");
	}
}
