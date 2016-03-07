package com.diploma.vmpay.p0491_simpleadaptercustom1;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by Andrew on 05.03.2016.
 */
public class MySimpleAdapter extends SimpleAdapter
{
	/**
	 * Constructor
	 *
	 * @param context  The context where the View associated with this SimpleAdapter is running
	 * @param data     A List of Maps. Each entry in the List corresponds to one row in the list. The
	 *                 Maps contain the data for each row, and should include all the entries specified in
	 *                 "from"
	 * @param resource Resource identifier of a view layout that defines the views for this list
	 *                 item. The layout file should include at least those named views defined in "to"
	 * @param from     A list of column names that will be added to the Map associated with each
	 *                 item.
	 * @param to       The views that should display column in the "from" parameter. These should all be
	 *                 TextViews. The first N views in this list are given the values of the first N columns
	 */

	final int negative = android.R.drawable.arrow_down_float;
	final int positive = android.R.drawable.arrow_up_float;

	public MySimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to)
	{
		super(context, data, resource, from, to);
	}

	@Override
	public void setViewText(TextView v, String text) {
		// метод супер-класса, который вставляет текст
		super.setViewText(v, text);
		// если нужный нам TextView, то разрисовываем
		if (v.getId() == R.id.tvValue) {
			int i = Integer.parseInt(text);
			if (i < 0) v.setTextColor(Color.RED); else
			if (i > 0) v.setTextColor(Color.GREEN);
		}
	}

	@Override
	public void setViewImage(ImageView v, int value) {
		// метод супер-класса
		super.setViewImage(v, value);
		// разрисовываем ImageView
		if (value == negative) v.setBackgroundColor(Color.RED); else
		if (value == positive) v.setBackgroundColor(Color.GREEN);
	}
}
