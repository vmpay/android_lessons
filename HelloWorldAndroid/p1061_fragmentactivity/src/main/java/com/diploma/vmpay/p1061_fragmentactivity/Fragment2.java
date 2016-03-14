package com.diploma.vmpay.p1061_fragmentactivity;

/**
 * Created by Andrew on 07.02.2016.
 */
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class Fragment2 extends Fragment {

    public interface onSomeEventListener {
        void someEvent(String s);
    }

    onSomeEventListener someEventListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            someEventListener = (onSomeEventListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onSomeEventListener");
        }
    }

    final String LOG_TAG = "myLogs";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment2, null);

        Button button = (Button) v.findViewById(R.id.button);
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Log.d(LOG_TAG, "Button click in Fragment2");
                someEventListener.someEvent("Test text to Fragment1");
            }
        });

        return v;
    }
}
