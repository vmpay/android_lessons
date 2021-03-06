package com.diploma.vmpay.p1151_multiplescreen;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Andrew on 15.03.2016.
 */
public class DetailsFragment extends Fragment
{

	public static DetailsFragment newInstance(int pos) {
		DetailsFragment details = new DetailsFragment();
		Bundle args = new Bundle();
		args.putInt("position", pos);
		details.setArguments(args);
		return details;
	}

	int getPosition() {
		return getArguments().getInt("position", 0);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.details, container, false);
		TextView tv = (TextView) v.findViewById(R.id.tvText);
		tv.setText(getResources().getStringArray(R.array.content)[getPosition()]);
		return v;
	}
}