package com.example.andrew.p0151_contextmenu;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tvColor, tvSize;
    final int MENU_COLOR_RED = 1;
    final int MENU_COLOR_GREEN = 2;
    final int MENU_COLOR_BLUE = 3;
    final int MENU_SIZE_22 = 4;
    final int MENU_SIZE_26 = 5;
    final int MENU_SIZE_30 = 6;
    private static final String TAG = "myLogs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvColor = (TextView) findViewById(R.id.tvColor);
        tvSize = (TextView) findViewById(R.id.tvSize);

        // для tvColor и tvSize необходимо создавать контекстное меню
        registerForContextMenu(tvColor);
        registerForContextMenu(tvSize);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.tvColor:
                getMenuInflater().inflate(R.menu.colormenu, menu);
                /*menu.add(0, MENU_COLOR_RED, 0, "Red");
                menu.add(0, MENU_COLOR_GREEN, 0, "Green");
                menu.add(0, MENU_COLOR_BLUE, 0, "Blue");*/
                break;
            case R.id.tvSize:
                getMenuInflater().inflate(R.menu.sizemenu, menu);
               /* menu.add(0, MENU_SIZE_22, 0, "22");
                menu.add(0, MENU_SIZE_26, 0, "26");
                menu.add(0, MENU_SIZE_30, 0, "30");*/
                break;
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        Log.d(TAG, "Выбрали пункт меню с id: " + item.getItemId()+ "converted getitemid: " + Integer.toString(item.getItemId()) + " converted R.id.BLUE " + Integer.toString(R.id.MENU_COLOR_BLUE));
        switch (item.getItemId()) {
            // пункты меню для tvColor
            case R.id.MENU_COLOR_RED:
                Log.d(TAG, "выбрали ред");
                tvColor.setTextColor(Color.RED);
                tvColor.setText("Text color = red");
                break;
            case R.id.MENU_COLOR_GREEN:
                tvColor.setTextColor(Color.GREEN);
                tvColor.setText("Text color = green");
                break;
            case R.id.MENU_COLOR_BLUE:
                tvColor.setTextColor(Color.BLUE);
                tvColor.setText("Text color = blue");
                break;
            // пункты меню для tvSize
            case R.id.MENU_SIZE_22:
                tvSize.setTextSize(22);
                tvSize.setText("Text size = 22");
                break;
            case R.id.MENU_SIZE_26:
                Log.d(TAG, "выбрали 26");
                tvSize.setTextSize(26);
                tvSize.setText("Text size = 26");
                break;
            case R.id.MENU_SIZE_30:
                tvSize.setTextSize(30);
                tvSize.setText("Text size = 30");
                break;
        }
        return super.onContextItemSelected(item);
    }
}
