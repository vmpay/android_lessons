package com.example.andrew.gladiatorsimulator;

import android.content.Intent;
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

import static java.lang.Integer.parseInt;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etLogin;
    private EditText etPassword;
    private TextView tvMsg;
    private static final String TAG = "LoginActivity";
    private String login ="";
    private Intent intentMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        intentMain  = new Intent(this, MainActivity.class);


        etLogin = (EditText) findViewById(R.id.etLogin);
        etPassword = (EditText) findViewById(R.id.etPassword);

        ImageView image = (ImageView) findViewById(R.id.iLogo);
        image.setClickable(true);
        //image.setOnClickListener(this);

        Button btnLogin = (Button) findViewById(R.id.button_login);
        Button btnForgotPassword = (Button) findViewById(R.id.button_forgotpassword);
        Button btnRegister = (Button) findViewById(R.id.button_register);

        tvMsg = (TextView) findViewById(R.id.tvMessage);

        btnRegister.setOnClickListener(this);
        btnForgotPassword.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        image.startAnimation(anim);
    }

    @Override
    public void onClick(View v) {
        tvMsg.setText("");
        switch (v.getId()) {
            case R.id.button_login:
                String myurl = "";
            {
                // Проверяем поля на пустоту и правильность заполнения
                if (TextUtils.isEmpty(etLogin.getText().toString())
                        || TextUtils.isEmpty(etPassword.getText().toString())) {
                    Log.d(TAG, "Login: " + etLogin.getText().toString() + " Password:" + etPassword.getText());
                    Toast.makeText(this, "No input data. Fill the fields.", Toast.LENGTH_SHORT).show();
                    return;
                }
                login = etLogin.getText().toString();
                String password = etPassword.getText().toString();
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(login).matches())
                {
                    Log.d(TAG, "email = " + login);
                    Toast.makeText(this, "E-mail incorrect format", Toast.LENGTH_SHORT).show();
                    return;
                }
                //TODO: Проверка пароля. Какие символы недопустимы в пароле? Реализовать проверку надежности
                myurl = "https://gladiator274102.azure-api.net/Gladiator/login?login=" + login + "&password=" + password;
                Toast.makeText(this, "Waiting for server response...", Toast.LENGTH_SHORT).show();
                new SendLogin().execute(myurl);
                break;
            }
            case R.id.button_forgotpassword:
            {
                if (TextUtils.isEmpty(etLogin.getText().toString())) {
                    Log.d(TAG, "Login: " + etLogin.getText().toString());
                    Toast.makeText(this, "E-mail is empty. Fill the field.", Toast.LENGTH_SHORT).show();
                    return;
                }
                login = etLogin.getText().toString();
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(login).matches())
                {
                    Log.d(TAG, "email = " + login);
                    Toast.makeText(this, "E-mail incorrect format", Toast.LENGTH_SHORT).show();
                    return;
                }
                //TODO: Change URL чето не пахает
                myurl = "https://gladiator274102.azure-api.net/Gladiator/recallpsw?login=" + login;
                Toast.makeText(this, "Waiting for server response...", Toast.LENGTH_SHORT).show();
                new SendLogin().execute(myurl);
                break;
            }
            case R.id.button_register:
            {
                //TODO: Вставить код перехода к активити Регистрация
                Intent intentSignup = new Intent(this, SignUpActivity.class);
                startActivity(intentSignup);
                break;
            }
            case R.id.iLogo:
            {
                //TODO: Не забыть убрать потом этот кейс
                Log.d(TAG, "iLogo clicked");
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

    private class SendLogin extends SendToApi {

        @Override
        protected void onPostExecute(String result)
        {
            Log.d(TAG, "Зашли в OnPostEx result=" + result);
            String res = result.substring(0, 2);
            String lvlstr = result.substring(2);
            int code = parseInt(res, 10);
            //int lvl = parseInt(lvlstr, 10);
            switch (code){
                case 2:// Connection error
                    tvMsg.setText("Cannot connect to the server...");
                    break;
                case 12:// Login succeed
                    intentMain.putExtra("login", login);
                    intentMain.putExtra("lvl", lvlstr);
                    startActivity(intentMain);
                    break;
                case 14:
                    tvMsg.setText("Entity can't be retreived.");
                    break;
                case 15:
                    tvMsg.setText("Incorrect password.");
                    break;
                case 16:
                    tvMsg.setText("Recovery password mail has been sent to you.");
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
        }
    }
}
