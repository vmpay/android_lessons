package com.diploma.vmpay.p1131_actionmode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity
{

	ActionMode actionMode;
	ListView lvActionMode;
	final String LOG_TAG = "myLogs";

	String[] data = { "one", "two", "three", "four", "five" };

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_activated_1, data);
		lvActionMode = (ListView) findViewById(R.id.lvActionMode);
		lvActionMode.setAdapter(adapter);
		lvActionMode.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		lvActionMode.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener()
		{

			@Override
			public boolean onCreateActionMode(android.view.ActionMode mode, Menu menu)
			{
				mode.getMenuInflater().inflate(R.menu.context, menu);
				return true;
			}

			@Override
			public boolean onPrepareActionMode(android.view.ActionMode mode, Menu menu)
			{
				return false;
			}

			@Override
			public boolean onActionItemClicked(android.view.ActionMode mode, MenuItem item)
			{
				mode.finish();
				return false;
			}

			@Override
			public void onDestroyActionMode(android.view.ActionMode mode)
			{
			}

			@Override
			public void onItemCheckedStateChanged(android.view.ActionMode mode, int position, long id, boolean checked)
			{
				Log.d(LOG_TAG, "position = " + position + ", checked = "
						+ checked);
			}
		});
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.context, menu);
		return true;
	}


}
