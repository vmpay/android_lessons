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

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        image.startAnimation(anim);
    }

    @Override
    public void onClick(View v) {
        tvMsg.setText("");
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
            //String res = result;
            int code = parseInt(result, 10);
            switch (code){
                case 0:// Login failed
                    result = "Incorrect username or password.";
                    tvMsg.setText(""+result);
                    break;
                case 1:// Login succeed
                    intentMain.putExtra("login", login);
                    startActivity(intentMain);
                    break;
                case 2:// Connection error
                    tvMsg.setText("Cannot connect to the server...");
                    break;
                case 3:// Recovery password mail has been sent
                    tvMsg.setText("Recovery password message has been sent to " + login);
                    break;
                case 4:// Recovery password mail hasnt been sent. No such username

                    break;
                default:// Unknown code
                    Log.d(TAG, "Зашли в OnPostEx Unknown code result: " + code);
                    tvMsg.setText(""+result);
                    break;
            }
        }
    }
}
