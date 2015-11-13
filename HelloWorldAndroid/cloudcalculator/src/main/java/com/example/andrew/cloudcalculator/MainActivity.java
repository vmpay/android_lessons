package com.example.andrew.cloudcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
//import org.apache.http.legacy;

//import com.squareup.okhttp.*;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/*import retrofit.*;
import retrofit.http.GET;
import retrofit.http.Header;*/


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
    String tmp = "";
    String website = "https://calc274102.azure-api.net/Calc/add?a=2&b=3";
    String apiKey = "1877991c30a7459e90e5b6a7b5b2445b";

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
                //String num1str = String.valueOf(num1);
                //String num2str = String.valueOf(num2);
                //String num1str = Float.toString(num1);
                //String num2str = Float.toString(num2);
                //final String myurl = "https://calc274102.azure-api.net/Calc/xpowy?a=" + num1 + "&b=" + num2;

                final String myurl = "https://calc274102.azure-api.net/Calc/xpowy?a=2&b=3";
                new Thread() {

                    @Override
                    public void run() {
                oper = "x^y";
                //result = (float) Math.pow(num1, num2);

                        /*@GET("https://calc274102.azure-api.net/Calc/add?a={num1}&b={num2}")
                        retrofit.Call<User> getUser(@Header("Ocp-Apim-Subscription-Key") String apiKey)*/



                        try {
                            //URL url = new URL("https://github.com/vmpay");


                            //URL url = new URL("https://calc274102.azure-api.net/Calc/xpowy?a=2&b=3");
                            URL url = new URL(myurl);
                            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                            urlConnection.addRequestProperty("Ocp-Apim-Subscription-Key", apiKey);//.setRequestProperty("Ocp-Apim-Subscription-Key", apiKey);
                            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                            //readStream(in);
                            //in.read();
                            tmp = convertStreamToString(in);
                            urlConnection.disconnect();
                            //tmp = in.toString();



                        } catch (MalformedURLException e) {
                           // e.printStackTrace();
                        } catch (IOException e) {
                            //e.printStackTrace();
                        }





                /*HttpClient httpclient = HttpClients.createDefault();

                try
                {
                    URIBuilder builder = new URIBuilder("https://calc274102.azure-api.net/Calc/add?a={num1}&b={num2}");


                    URI uri = builder.build();
                    HttpGet request = new HttpGet(uri);

                    // Request body
                    //StringEntity reqEntity = new StringEntity("{body}");
                    //request.setEntity(reqEntity);

                    HttpResponse response = httpclient.execute(request);
                    HttpEntity entity = response.getEntity();

                    if (entity != null)
                    {
                        //System.out.println(EntityUtils.toString(entity));
                        tmp = EntityUtils.toString(entity);
                    }
                }
                catch (Exception e)
                {
                   // System.out.println(e.getMessage());
                }*/

                    }
                }.start();
                break;
            default:
                break;
        }

        // формируем строку вывода
        //tvResult.setText(num1 + " " + oper + " " + num2 + " = " + result);
        tvResult.setText(oper + " " + " Result = " + result + tmp );
    }





    public String convertStreamToString(InputStream is) {
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
    }

}