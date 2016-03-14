package com.diploma.vmpay.p1101_dialogfragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;

/**
 * Created by Andrew on 14.03.2016.
 */
public class Dialog2 extends DialogFragment implements DialogInterface.OnClickListener
{
	final String LOG_TAG = "myLogs";

	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder adb = new AlertDialog.Builder(getActivity())
				.setTitle("Title!").setPositiveButton(R.string.yes, this)
				.setNegativeButton(R.string.no, this)
				.setNeutralButton(R.string.maybe, this)
				.setMessage(R.string.message_text);
		return adb.create();
	}

	public void onClick(DialogInterface dialog, int which) {
		int i = 0;
		switch (which) {
			case Dialog.BUTTON_POSITIVE:
				i = R.string.yes;
				break;
			case Dialog.BUTTON_NEGATIVE:
				i = R.string.no;
				break;
			case Dialog.BUTTON_NEUTRAL:
				i = R.string.maybe;
				break;
		}
		if (i > 0)
			Log.d(LOG_TAG, "Dialog 2: " + getResources().getString(i));
	}

	public void onDismiss(DialogInterface dialog) {
		super.onDismiss(dialog);
		Log.d(LOG_TAG, "Dialog 2: onDismiss");
	}

	public void onCancel(DialogInterface dialog) {
		super.onCancel(dialog);
		Log.d(LOG_TAG, "Dialog 2: onCancel");
	}
}
