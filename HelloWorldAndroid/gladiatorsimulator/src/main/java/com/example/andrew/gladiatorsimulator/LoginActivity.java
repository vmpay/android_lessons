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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView image;
    EditText etLogin;
    EditText etPassword;
    Button btnLogin;
    Button btnForgotPassword;
    Button btnRegister;
    TextView tvMsg;
    private static final String TAG = "LoginActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
                //TODO: Добавить проверку имейла, а также ограничений на формат пароля и логина
                // Проверяем поля на пустоту и правильность заполнения
                if (TextUtils.isEmpty(etLogin.getText().toString())
                        || TextUtils.isEmpty(etPassword.getText().toString())) {
                    Log.d(TAG, etLogin.getText().toString() + " " + etPassword.getText());
                    Toast.makeText(this, "No input data. Fill the fields.", Toast.LENGTH_SHORT).show();
                    return;
                }
                //TODO: Вставить метод отправки логина и пароля на проверку на сервер
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
                Intent intentMain = new Intent(this, MainActivity.class);
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
}
