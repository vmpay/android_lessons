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
public class Fragment1 extends Fragment implements View.OnClickListener {

    final String LOG_TAG = "myLogs";
    SharedPreferences sPref;
    Button btnAddSP, btnClear, btnAddAlert;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreateView() Fragment1");
        View v = inflater.inflate(R.layout.welcome_fragment, null);

        Fragment frag2 = new AlertFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.fragmentSP, frag2);
        //ft.addToBackStack(null);
        ft.commit();

        btnAddSP = (Button) v.findViewById(R.id.btnAddSP);
        btnClear = (Button) v.findViewById(R.id.btnClearAlerts);
        btnAddAlert = (Button) v.findViewById(R.id.btnAddAlert);

        btnAddAlert.setOnClickListener(this);
        btnAddSP.setOnClickListener(this);
        btnClear.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        sPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor ed = sPref.edit();
        switch (v.getId())
        {
            case R.id.btnAddSP:
                Log.d(LOG_TAG, "AddSharedPreferences click in Fragment1");
                ed.putString("SomePreferences", "Blah-Blah");
                ed.commit();
                break;
            case R.id.btnClearAlerts:
                Log.d(LOG_TAG, "ClearAlertsButton click in Fragment1");
                ed.clear();
                ed.commit();
                break;
            case R.id.btnAddAlert:
                Log.d(LOG_TAG, "AddAlertButton click in Fragment1");
                AddAlertFragment addAlertFragment = new AddAlertFragment();
                FragmentTransaction fTrans;
                fTrans = getFragmentManager().beginTransaction();
                fTrans.replace(R.id.fragmentMain, addAlertFragment);
                fTrans.addToBackStack(null);
                fTrans.commit();
                break;
        }
    }
}
