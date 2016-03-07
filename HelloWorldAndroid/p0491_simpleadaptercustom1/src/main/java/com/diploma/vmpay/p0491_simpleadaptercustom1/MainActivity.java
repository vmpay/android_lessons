package com.diploma.vmpay.p0491_simpleadaptercustom1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
{
	// имена атрибутов для Map
	final String ATTRIBUTE_NAME_TEXT = "text";
	final String ATTRIBUTE_NAME_VALUE = "value";
	final String ATTRIBUTE_NAME_IMAGE = "image";

	// картинки для отображения динамики
	final int positive = android.R.drawable.arrow_up_float;
	final int negative = android.R.drawable.arrow_down_float;

	ListView lvSimple;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// массив данных
		int[] values = { 8, 4, -3, 2, -5, 0, 3, -6, 1, -1 };

		// упаковываем данные в понятную для адаптера структуру
		ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>(
				values.length);
		Map<String, Object> m;
		int img = 0;
		for (int i = 0; i < values.length; i++) {
			m = new HashMap<String, Object>();
			m.put(ATTRIBUTE_NAME_TEXT, "Day " + (i + 1));
			m.put(ATTRIBUTE_NAME_VALUE, values[i]);
			if (values[i] == 0) img = 0; else
				img = (values[i] > 0) ? positive : negative;
			m.put(ATTRIBUTE_NAME_IMAGE, img);
			data.add(m);
		}

		// массив имен атрибутов, из которых будут читаться данные
		String[] from = { ATTRIBUTE_NAME_TEXT, ATTRIBUTE_NAME_VALUE,
				ATTRIBUTE_NAME_IMAGE };
		// массив ID View-компонентов, в которые будут вставлять данные
		int[] to = { R.id.tvText, R.id.tvValue, R.id.ivImg };

		// создаем адаптер
		MySimpleAdapter sAdapter = new MySimpleAdapter(this, data,
				R.layout.item, from, to);

		// определяем список и присваиваем ему адаптер
		lvSimple = (ListView) findViewById(R.id.lvSimple);
		lvSimple.setAdapter(sAdapter);
	}

}
