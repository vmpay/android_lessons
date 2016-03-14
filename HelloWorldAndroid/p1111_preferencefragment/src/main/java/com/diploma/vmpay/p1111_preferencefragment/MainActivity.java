package com.diploma.vmpay.p1111_preferencefragment;

import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

public class MainActivity extends PreferenceActivity
{

	public void onBuildHeaders(List<PreferenceActivity.Header> target) {
		loadHeadersFromResource(R.xml.pref_head, target);
	}

	@Override
	protected boolean isValidFragment(String fragmentName)
	{
		if (Fragment1.class.getName().equals(fragmentName)
				|| Fragment2.class.getName().equals(fragmentName))
		{
			return true;
		}else
		{
			return false;
		}
	}
}
