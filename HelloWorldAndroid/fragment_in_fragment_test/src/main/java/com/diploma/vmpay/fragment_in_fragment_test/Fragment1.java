package com.diploma.vmpay.fragment_in_fragment_test;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Andrew on 07.02.2016.
 */
public class Fragment1 extends Fragment {

    final String LOG_TAG = "myLogs";
    SharedPreferences sPref;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreateView() Fragment1");
        View v = inflater.inflate(R.layout.welcome_fragment, null);

        Button button = (Button) v.findViewById(R.id.btnAddAlert);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(LOG_TAG, "ButtonAddAlert click in Fragment1");
                sPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor ed = sPref.edit();
                ed.putString("SomePreferences", "Blah-Blah");
                ed.commit();
            }
        });

        Button button2 = (Button) v.findViewById(R.id.btnClearAlerts);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(LOG_TAG, "ClearAlertsButton click in Fragment1");
                sPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor ed = sPref.edit();
                ed.clear();
                ed.commit();
            }
        });

        /*Fragment frag2 = new AlertFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.fragmentSP, frag2);
        ft.commit();*/

        return v;
    }
}
