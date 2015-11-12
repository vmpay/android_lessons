package com.example.andrew.cloudcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    EditText etNum1;
    EditText etNum2;

    Button btnAdd;
    Button btnSub;
    Button btnMult;
    Button btnDiv;
    Button btnXpowY;
    Button btnJedenprzezx;
    Button btnSqrt;
    Button btnXkwadrat;

    TextView tvResult;

    String oper = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // находим элементы
        etNum1 = (EditText) findViewById(R.id.etNum1);
        etNum2 = (EditText) findViewById(R.id.etNum2);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnSub = (Button) findViewById(R.id.btnSub);
        btnMult = (Button) findViewById(R.id.btnMult);
        btnDiv = (Button) findViewById(R.id.btnDiv);
        btnXpowY = (Button) findViewById(R.id.btnXpowY);
        btnJedenprzezx = (Button) findViewById(R.id.btnJedenprzezx);
        btnSqrt = (Button) findViewById(R.id.btnSqrt);
        btnXkwadrat = (Button) findViewById(R.id.btnXkwadrat);

        tvResult = (TextView) findViewById(R.id.tvResult);

        // прописываем обработчик
        btnAdd.setOnClickListener(this);
        btnSub.setOnClickListener(this);
        btnMult.setOnClickListener(this);
        btnDiv.setOnClickListener(this);
        btnXpowY.setOnClickListener(this);
        btnJedenprzezx.setOnClickListener(this);
        btnSqrt.setOnClickListener(this);
        btnXkwadrat.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        float num1 = 0;
        float num2 = 0;
        float result = 0;

        // Проверяем поля на пустоту
        if (TextUtils.isEmpty(etNum1.getText().toString())
                || TextUtils.isEmpty(etNum2.getText().toString())) {
            return;
        }

        // читаем EditText и заполняем переменные числами
        num1 = Float.parseFloat(etNum1.getText().toString());
        num2 = Float.parseFloat(etNum2.getText().toString());

        // определяем нажатую кнопку и выполняем соответствующую операцию
        // в oper пишем операцию, потом будем использовать в выводе
        switch (v.getId()) {
            case R.id.btnAdd:
                oper = "+";
                result = num1 + num2;
                break;
            case R.id.btnSub:
                oper = "-";
                result = num1 - num2;
                break;
            case R.id.btnMult:
                oper = "*";
                result = num1 * num2;
                break;
            case R.id.btnDiv:
                oper = "/";
                result = num1 / num2;
                break;
            case R.id.btnJedenprzezx:
                oper = "1/x";
                result = 1 / num1;
                break;
            case R.id.btnSqrt:
                oper = "sqrt";
                result = (float) Math.sqrt(num1);
                break;
            case R.id.btnXkwadrat:
                oper = "x^2";
                result = num1 * num1;
                break;
            case R.id.btnXpowY:
                oper = "x^y";
                result = (float) Math.pow(num1, num2);
                break;
            default:
                break;
        }

        // формируем строку вывода
        //tvResult.setText(num1 + " " + oper + " " + num2 + " = " + result);
        tvResult.setText(oper + " " + " Result = " + result);
    }
}
