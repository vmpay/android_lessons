package com.diploma.vmpay.p1111_preferencefragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by Andrew on 14.03.2016.
 */
public class Fragment1 extends PreferenceFragment
{
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.pref1);
	}
}
