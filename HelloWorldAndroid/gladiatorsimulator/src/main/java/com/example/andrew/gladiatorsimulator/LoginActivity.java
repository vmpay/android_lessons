package com.example.andrew.gladiatorsimulator;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static java.lang.Integer.parseInt;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView image;
    EditText etLogin;
    EditText etPassword;
    Button btnLogin;
    Button btnForgotPassword;
    Button btnRegister;
    TextView tvMsg;
    private static final String TAG = "LoginActivity";
    String login ="";
    String password ="";
    String myurl = "";
    String apiKey = "8651fa249e0541e09bf57da564511763";
    Intent intentMain;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        intentMain  = new Intent(this, MainActivity.class);


        etLogin = (EditText) findViewById(R.id.etLogin);
        etPassword = (EditText) findViewById(R.id.etPassword);

        image = (ImageView) findViewById(R.id.iLogo);
        image.setClickable(true);
        image.setOnClickListener(this);

        btnLogin = (Button) findViewById(R.id.button_login);
        btnForgotPassword = (Button) findViewById(R.id.button_forgotpassword);
        btnRegister = (Button) findViewById(R.id.button_register);

        tvMsg = (TextView) findViewById(R.id.tvMessage);

        btnRegister.setOnClickListener(this);
        btnForgotPassword.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);;
        image.startAnimation(anim);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_login:
            {
                // Проверяем поля на пустоту и правильность заполнения
                if (TextUtils.isEmpty(etLogin.getText().toString())
                        || TextUtils.isEmpty(etPassword.getText().toString())) {
                    Log.d(TAG, "Login: " + etLogin.getText().toString() + " Password:" + etPassword.getText());
                    Toast.makeText(this, "No input data. Fill the fields.", Toast.LENGTH_SHORT).show();
                    return;
                }
                login = etLogin.getText().toString();
                password = etPassword.getText().toString();
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(login).matches())
                {
                    Log.d(TAG, "email = " + login);
                    Toast.makeText(this, "E-mail incorrect format", Toast.LENGTH_SHORT).show();
                    return;
                }
                //TODO: Проверка пароля. Какие символы недопустимы в пароле? Реализовать проверку надежности
                //TODO: Вставить метод отправки логина и пароля на проверку на сервер
                myurl = "https://gladiator274102.azure-api.net/Gladiator/fight?a=5&b=5&c=5&d=0";
                Toast.makeText(this, "Waiting for server response...", Toast.LENGTH_SHORT).show();
                new SendToApi().execute(myurl);
                break;
            }
            case R.id.button_forgotpassword:
            {
                //TODO: Вставить код перехода к активити Забыл пароль
                break;
            }
            case R.id.button_register:
            {
                //TODO: Вставить код перехода к активити Регистрация
                break;
            }
            case R.id.iLogo:
            {
                //TODO: Не забыть убрать потом этот кейс
                Log.d(TAG, "iLogo clicked");
                //Intent intentMain = new Intent(this, MainActivity.class);
                //intentMain.putExtra("fightlog", logtext);
                startActivity(intentMain);
                break;
            }
            default:
            {
                Log.d(TAG, "Default exit from switch Onclick");
                break;
            }

        }
    }

    private class SendToApi extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            Log.d(TAG, "Зашли в DoInBg: " + params[0]);
            final String myurl = params[0];
            String tmp="Empty";
            try {
                URL url = new URL(myurl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.addRequestProperty("Ocp-Apim-Subscription-Key", apiKey);
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder result = new StringBuilder();
                String line;
                while((line = reader.readLine()) != null) {
                    result.append(line);
                }
                tmp = result.toString();
                urlConnection.disconnect();
            } catch (MalformedURLException e) {
                Log.d(TAG, "MalformedURLException");
                //e.printStackTrace();
            } catch (IOException e) {
                Log.d(TAG, "IOException");
                tmp = "No connection to the server...";
                //e.printStackTrace();
            }
            return tmp;
        }
        @Override
        protected void onPostExecute(String result)
        {
            Log.d(TAG, "Зашли в OnPostEx result=" + result);
            //TODO: Убрать сабстринг
            String res = result.substring(0, 2);
            int code = parseInt(res, 10);
            switch (code){
                case 0:
                    res = "Incorrect username or password.";
                    tvMsg.setText(""+res);
                    break;
                case 1:
                    intentMain.putExtra("login", login);
                    startActivity(intentMain);
                    //res = "Victory!";
                    break;
                default:
                    Log.d(TAG, "Зашли в OnPostEx Unknown code result: " + code);
                    break;
            }


        }
    }
}
