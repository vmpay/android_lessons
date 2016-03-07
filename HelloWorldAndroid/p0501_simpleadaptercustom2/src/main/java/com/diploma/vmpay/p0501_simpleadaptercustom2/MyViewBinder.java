package com.diploma.vmpay.p0501_simpleadaptercustom2;

import android.content.ContentValues;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

/**
 * Created by Andrew on 05.03.2016.
 */
public class MyViewBinder implements SimpleAdapter.ViewBinder
{
	int red;
	int orange;
	int green;
	private Context context;

	public MyViewBinder(Context context)
	{
		/* red = context.getColor(R.color.Red);
		 orange = context.getColor(R.color.Orange);
		 green = context.getColor(R.color.Green);*/
		red =ContextCompat.getColor(context, R.color.Red);
		orange =ContextCompat.getColor(context, R.color.Orange);
		green =ContextCompat.getColor(context, R.color.Green);
	}


	@Override
	public boolean setViewValue(View view, Object data, String textRepresentation)
	{
		int i = 0;
		switch (view.getId()) {
			// LinearLayout
			case R.id.llLoad:
				i = ((Integer) data).intValue();
				if (i < 40) view.setBackgroundColor(green); else
				if (i < 70) view.setBackgroundColor(orange); else
					view.setBackgroundColor(red);
				return true;
			// ProgressBar
			case R.id.pbLoad:
				i = ((Integer) data).intValue();
				((ProgressBar)view).setProgress(i);
				return true;
		}
		return false;
	}
}
