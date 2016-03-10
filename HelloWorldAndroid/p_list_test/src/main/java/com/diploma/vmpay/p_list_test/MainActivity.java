package com.diploma.vmpay.p_list_test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener
{
	private final String LOG_TAG = "myLogs";
	private String[] countries = {
			"Albania", "Belgium", "Canada", "Denmark",
			"Estonia", "France", "Germany", "Hungary",
			"Ireland", "Jakarta", "Kenia", "Luxemburg"
	};
	private ListView lvList;
	private ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		lvList = (ListView) findViewById(R.id.lvList);
		lvList.setOnItemClickListener(this);

		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, countries);

		lvList.setAdapter(adapter);

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{

	}
}
