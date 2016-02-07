package com.diploma.vmpay.fragment_in_fragment_test;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    final String LOG_TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(LOG_TAG, "onCreate() MainActivity");
        /*Fragment frag1 = new Fragment1();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.fragmentMain, frag1);
        ft.commit();*/
    }
}
