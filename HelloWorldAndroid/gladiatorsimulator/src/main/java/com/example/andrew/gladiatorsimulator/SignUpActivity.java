package com.example.andrew.gladiatorsimulator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.Integer.parseInt;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{
    EditText etLogin;
    EditText etPassword;
    EditText etRePassword;
    Button btnRSignup;
    TextView tvMsg;
    private static final String TAG = "SignUpActivity";
    String login;
    String password;
    String repassword;
    String myurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etLogin = (EditText) findViewById(R.id.etLogin);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etRePassword = (EditText) findViewById(R.id.etRePassword);

        btnRSignup = (Button) findViewById(R.id.button_register);

        tvMsg = (TextView) findViewById(R.id.tvMessage);

        btnRSignup.setOnClickListener(this);
        Log.d(TAG, "Успешно прошли onCreate()");
    }

    @Override
    public void onClick(View v) {
        tvMsg.setText("");
        // Проверяем поля на пустоту и правильность заполнения
        if (TextUtils.isEmpty(etLogin.getText().toString())
                || TextUtils.isEmpty(etPassword.getText().toString())
                || TextUtils.isEmpty(etRePassword.getText().toString())) {
            Log.d(TAG, "Empty fields");
            Toast.makeText(this, "No input data. Fill the fields.", Toast.LENGTH_SHORT).show();
            return;
        }
        login = etLogin.getText().toString();
        password = etPassword.getText().toString();
        repassword = etRePassword.getText().toString();
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(login).matches())
        {
            Log.d(TAG, "email = " + login);
            Toast.makeText(this, "E-mail incorrect format", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!repassword.equals(password))
        {
            Log.d(TAG, "password = " + password + " repassword = " + repassword);
            Toast.makeText(this, "Passwords are not the same.", Toast.LENGTH_SHORT).show();
            return;
        }
        //TODO: Проверка пароля. Какие символы недопустимы в пароле? Реализовать проверку надежности
        myurl = "https://gladiator274102.azure-api.net/Gladiator/signup?login=" + login + "&password=" + password;
        Toast.makeText(this, "Waiting for server response...", Toast.LENGTH_SHORT).show();
        new SendSignup().execute(myurl);
    }

    private class SendSignup extends SendToApi {

        @Override
        protected void onPostExecute(String result)
        {
            Log.d(TAG, "Зашли в OnPostEx result=" + result);
            int code = parseInt(result, 10);
            switch (code){
                case 0:// Registration complete
                    tvMsg.setText("Your registration is completed. Turn back and sign in.");
                    break;
                case 1:// Registration failed
                    tvMsg.setText("The login is already in use. Try another one.");
                    break;
                case 2:// Connecting error
                    tvMsg.setText("Cannot connect to the server...");
                    break;
                default:// Unknown code
                    Log.d(TAG, "Зашли в OnPostEx Unknown code result: " + code);
                    tvMsg.setText(""+result);
                    break;
            }
        }
    }
}
