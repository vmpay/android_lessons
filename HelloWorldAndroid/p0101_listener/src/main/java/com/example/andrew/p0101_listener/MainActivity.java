package com.example.andrew.p0101_listener;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tvOut;
    Button btnOk;
    Button btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // найдем View-элементы
        tvOut = (TextView) findViewById(R.id.tvOut);
        btnOk = (Button) findViewById(R.id.btnOk);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        // создание обработчика
        View.OnClickListener oclBtn = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String a, b;
                switch (v.getId()) {
                    case R.id.btnOk:
                        // кнопка ОК
                        //tvOut.setText("Нажата кнопка ОК");
                        a = "Vse OK: ";
                        b = a.concat(v.toString());
                        tvOut.setText(b);
                        break;
                    case R.id.btnCancel:
                        // кнопка Cancel
                        //tvOut.setText("Нажата кнопка Cancel");
                        a = "Cancel: ";
                        b = a.concat(Integer.toString(v.getId()));
                        tvOut.setText(b);
                        break;
                    default:
                        // Не совпало ни с чем
                        a = "Чето другое залетело: ";
                        b = a.concat(v.toString());
                        tvOut.setText(b);
                        break;
                }
            }
        };
        btnOk.setOnClickListener(oclBtn);
        btnCancel.setOnClickListener(oclBtn);
    }
}
