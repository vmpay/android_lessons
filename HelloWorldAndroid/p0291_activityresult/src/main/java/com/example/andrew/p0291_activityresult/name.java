package com.example.andrew.p0291_activityresult;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class name extends AppCompatActivity implements View.OnClickListener {
    EditText etName;
    Button btnOK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        etName = (EditText) findViewById(R.id.etName);
        btnOK = (Button) findViewById(R.id.btnOK);
        btnOK.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.putExtra("name", etName.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }
}
