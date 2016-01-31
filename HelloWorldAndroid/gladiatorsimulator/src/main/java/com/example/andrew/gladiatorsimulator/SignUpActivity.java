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
    private EditText etLogin;
    private EditText etPassword;
    private EditText etRePassword;
    private TextView tvMsg;
    private Button btnRSignup;
    private static final String TAG = "SignUpActivity";
    private final String link = "api";

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
        HideSoftKeyboard a = new HideSoftKeyboard();
        a.hideSoftKeyboard(this);
        // Проверяем поля на пустоту и правильность заполнения
        if (TextUtils.isEmpty(etLogin.getText().toString())
                || TextUtils.isEmpty(etPassword.getText().toString())
                || TextUtils.isEmpty(etRePassword.getText().toString())) {
            Log.d(TAG, "Empty fields");
            Toast.makeText(this, "No input data. Fill the fields.", Toast.LENGTH_SHORT).show();
            return;
        }
        String login = etLogin.getText().toString();
        String password = etPassword.getText().toString();
        String repassword = etRePassword.getText().toString();
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
        String myurl = link + "/signup?login=" + login + "&password=" + password;
        Toast.makeText(this, "Waiting for server response...", Toast.LENGTH_SHORT).show();
        btnRSignup.setEnabled(false);
        new SendSignup().execute(myurl);
    }

    private class SendSignup extends SendToApi {

        @Override
        protected void onPostExecute(String result)
        {
            Log.d(TAG, "Зашли в OnPostEx result=" + result);
            int code = parseInt(result, 10);
            switch (code){
                case 10:// Registration complete
                    tvMsg.setText("Your registration is completed. Turn back and sign in.");
                    break;
                case 11:// Registration failed
                    tvMsg.setText("The login is already in use. Try another one.");
                    break;
                case 2:// Connecting error
                    tvMsg.setText("Cannot connect to the server...");
                    break;
                case 32:
                    tvMsg.setText("Table not found.");
                    break;
                case 33:
                    tvMsg.setText("Something goes wrong  - Empty code.");
                    break;
                case 34:
                    tvMsg.setText("Authentification failed. Check Primary & Secondary keys.");
                    break;
                case 35:
                    tvMsg.setText("Send mail error.");
                    break;
                default:// Unknown code
                    Log.d(TAG, "Зашли в OnPostEx Unknown code result: " + code);
                    tvMsg.setText(""+result);
                    break;
            }
            btnRSignup.setEnabled(true);
        }
    }
}
