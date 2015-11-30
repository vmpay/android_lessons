package com.example.andrew.cloudcalculator;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



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
    Button btnResult;

    TextView tvResult;

    String oper = "";
    String result = "";
    String tmp = "Empty";
    String myurl = "";
    String apiKey = "1877991c30a7459e90e5b6a7b5b2445b";
    private static final String TAG = "URL-TAG";
    int num1int = 1;
    int num2int = 2;

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
        btnResult = (Button) findViewById(R.id.btnResult);

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
        btnResult.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        // Проверяем поля на пустоту
        if (TextUtils.isEmpty(etNum1.getText().toString())
                || TextUtils.isEmpty(etNum2.getText().toString())) {
            Toast.makeText(this, "No input data. Fill the fields.", Toast.LENGTH_SHORT).show();
            return;
        }
        // читаем EditText и заполняем переменные числами
        num1int = Integer.parseInt(etNum1.getText().toString());
        num2int = Integer.parseInt(etNum2.getText().toString());
        // определяем нажатую кнопку и выполняем соответствующую операцию
        // в oper пишем операцию, потом будем использовать в выводе
        switch (v.getId()) {
            case R.id.btnAdd:
                oper = "+";
                //result = num1 + num2;
                myurl = "https://calc274102.azure-api.net/Calc/add?a=" + num1int + "&b=" + num2int;
                //tmp = SendToAPI(myurl);
                Toast.makeText(this, "Waiting for server response", Toast.LENGTH_SHORT).show();
                new SentToApi().execute(myurl);
                //new SentToApi().onPostExecute(tmp);
                break;
            case R.id.btnSub:
                oper = "-";
                //result = num1 - num2;
                myurl = "https://calc274102.azure-api.net/Calc/sub?a=" + num1int + "&b=" + num2int;
                Toast.makeText(this, "Waiting for server response", Toast.LENGTH_SHORT).show();
                //tmp = SendToAPI(myurl);
                new SentToApi().execute(myurl);
                break;
            case R.id.btnMult:
                oper = "*";
                //result = num1 * num2;
                myurl = "https://calc274102.azure-api.net/Calc/mul?a=" + num1int + "&b=" + num2int;
                Toast.makeText(this, "Waiting for server response", Toast.LENGTH_SHORT).show();
                //tmp = SendToAPI(myurl);
                new SentToApi().execute(myurl);
                break;
            case R.id.btnDiv:
                oper = "/";
                //result = num1 / num2;
                myurl = "https://calc274102.azure-api.net/Calc/div?a=" + num1int + "&b=" + num2int;
                Toast.makeText(this, "Waiting for server response", Toast.LENGTH_SHORT).show();
                //tmp = SendToAPI(myurl);
                new SentToApi().execute(myurl);
                break;
            case R.id.btnJedenprzezx:
                oper = "1/x";
                //result = 1 / num1;
                myurl = "https://calc274102.azure-api.net/Calc/jedenprzezx?a=" + num1int;
                Toast.makeText(this, "Waiting for server response", Toast.LENGTH_SHORT).show();
                //tmp = SendToAPI(myurl);
                new SentToApi().execute(myurl);
                break;
            case R.id.btnSqrt:
                oper = "sqrt";
                //result = (float) Math.sqrt(num1);
                myurl = "https://calc274102.azure-api.net/Calc/sqrt?a=" + num1int;
                Toast.makeText(this, "Waiting for server response", Toast.LENGTH_SHORT).show();
                //tmp = SendToAPI(myurl);
                new SentToApi().execute(myurl);
                break;
            case R.id.btnXkwadrat:
                oper = "x^2";
                //result = num1 * num1;
                myurl = "https://calc274102.azure-api.net/Calc/xpow2?a=" + num1int;
                Toast.makeText(this, "Waiting for server response", Toast.LENGTH_SHORT).show();
                //tmp = SendToAPI(myurl);
                new SentToApi().execute(myurl);
                break;
            case R.id.btnXpowY:
                oper = "^";
                myurl = "https://calc274102.azure-api.net/Calc/xpowy?a=" + num1int + "&b=" + num2int;
                Toast.makeText(this, "Waiting for server response", Toast.LENGTH_SHORT).show();
                //Log.d(TAG, myurl);
                //tmp = SendToAPI(myurl);
                new SentToApi().execute(myurl);
                break;
            /*case R.id.btnResult: // После нажатия кнопки результат появляется результат вычислений
                // формируем строку вывода
                result = tmp;
                tvResult.setText(num1int + oper + num2int + "=" + result); // Можно поиграться с форматированием вывода
                tmp = "Empty set after button pressing";// но мне лень =)
                break;*/
            default:
                break;
        }
    }



    private class SentToApi extends AsyncTask<String, String, String>{
        @Override
        protected String doInBackground(String... params) {
            //int count = params.length;
            Log.d(TAG, "Зашли в DoInBg: " + myurl);
            final String myurl = params[0];
            try {
                URL url = new URL(myurl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.addRequestProperty("Ocp-Apim-Subscription-Key", apiKey);
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder result = new StringBuilder();
                String line;
                while((line = reader.readLine()) != null) {
                    result.append(line);
                }
                tmp = result.toString();
            } catch (MalformedURLException e) {
                Log.d(TAG, "MalformedURLException");
                e.printStackTrace();
            } catch (IOException e) {
                Log.d(TAG, "IOException");
                e.printStackTrace();
            }

            return tmp;
        }
        protected void onProgressUpdate(Integer... progress) {
            //setProgressPercent(progress[0]);
        }
        @Override
        protected void onPostExecute(String result) {
            //showDialog("Downloaded " + result + " bytes");
            Log.d(TAG, "Зашли в OnPostEx " + tmp);
            String resultS = tmp;
            tvResult.setText(num1int + oper + num2int + "=" + resultS); // Вывод результата сюда надо было вставлять?
            tmp = "Empty set in OnPostExecute";
        }
    }


    /*public String SendToAPI( String url) {
        final String myurl = url;
        Log.d(TAG, myurl);
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(myurl);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.addRequestProperty("Ocp-Apim-Subscription-Key", apiKey);
                    try {
                        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                        StringBuilder result = new StringBuilder();
                        String line;
                        while((line = reader.readLine()) != null) {
                            result.append(line);
                        }
                        tmp = result.toString();
                    } finally{
                        //Log.d(TAG, "Finally urlConnection.disconnect()");
                        urlConnection.disconnect();
                    }
                } catch (MalformedURLException e) {
                    Log.d(TAG, "MalformedURLException");
                    //e.printStackTrace();
                } catch (IOException e) {
                    Log.d(TAG, "IOException");
                    //e.printStackTrace();
                }
            }
        }.start();
        //Log.d(TAG, "before return tmp=" + tmp);
        return tmp;
    }*/
    /*public String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }*/

}