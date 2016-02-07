package com.diploma.vmpay.fragment_in_fragment_test;

/**
 * Created by Andrew on 07.02.2016.
 */
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


public class AlertFragment extends Fragment {

    final String LOG_TAG = "myLogs";
    SharedPreferences sPref;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreateView() AlertFragment");
        View v = inflater.inflate(R.layout.alerts_fragment, null);

        TextView tvAlerts = (TextView) v.findViewById(R.id.tvAlerts);
        sPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String savedText = sPref.getString("SomePreferences", "");

        if(!savedText.isEmpty())
        {
            Log.d(LOG_TAG, "onCreateView() AlertFragment: before linearLayout");
            LinearLayout linLayout = (LinearLayout) v.findViewById(R.id.alertLayout);
            LayoutInflater ltInflater = getActivity().getLayoutInflater();
            View item = ltInflater.inflate(R.layout.item, linLayout, false);
            TextView tvName = (TextView) item.findViewById(R.id.tvAlert);
            Log.d(LOG_TAG, "onCreateView() AlertFragment:Before setText");
            tvName.setText(savedText);
            Log.d(LOG_TAG, "onCreateView() AlertFragment: before getLayoutParams");
            item.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            Log.d(LOG_TAG, "onCreateView() AlertFragment: before addView");
            linLayout.addView(item);
            //tvAlerts.setText(savedText);
            Log.d(LOG_TAG, "onCreateView() AlertFragment: before setVisibility()");
            tvAlerts.setVisibility(View.GONE);
        }
        else
        {
            tvAlerts.setText("Some other stuff");
        }

        return v;
    }
}
