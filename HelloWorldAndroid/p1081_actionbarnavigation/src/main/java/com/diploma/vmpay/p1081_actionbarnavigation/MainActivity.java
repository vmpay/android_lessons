package com.diploma.vmpay.p1081_actionbarnavigation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;

public class MainActivity extends AppCompatActivity implements android.support.v7.app.ActionBar.OnNavigationListener
{
	final String LOG_TAG = "myLogs";

	String[] data = new String[] { "one", "two", "three" };

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
		setSupportActionBar(myToolbar);
		getSupportActionBar().setNavigationMode(android.support.v7.app.ActionBar.NAVIGATION_MODE_LIST);


		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, data);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		getSupportActionBar().setListNavigationCallbacks(adapter, this);



//		ActionBar bar = getActionBar();
//
//		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//
//		ActionBar.Tab tab = bar.newTab();
//		tab.setText("tab1");
//		tab.setTabListener(this);
//		bar.addTab(tab);
//
//		tab = bar.newTab();
//		tab.setText("tab2");
//		tab.setTabListener(this);
//		bar.addTab(tab);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId)
	{
		Log.d(LOG_TAG, "selected: position = " + itemPosition + ", id = "
				+ itemId + ", " + data[itemPosition]);
		return false;
	}
}
