package com.diploma.vmpay.fragment_in_fragment_test;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Andrew on 08.02.2016.
 */
public class AddAlertFragment extends Fragment  implements View.OnClickListener {

    final String LOG_TAG = "myLogs";
    Button btnOK;
    SharedPreferences sPref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreateView() AddAlertFragment");
        View v = inflater.inflate(R.layout.add_alert_fragment, null);
        btnOK = (Button) v.findViewById(R.id.btnOK);
        btnOK.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        // implements your things
        sPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("SomePreferences", "New Alert Added");
        ed.commit();
        getFragmentManager().popBackStack();
        getFragmentManager().beginTransaction().commit();
    }

}
