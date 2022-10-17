package com.example.myfinances;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private DBManager DBManager;
    private EditText login, parole;
    public static String current_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBManager = new DBManager(this);
        login = findViewById(R.id.ETlogin);
        parole = findViewById(R.id.ETNumParole);
    }

    @Override
    protected void onResume() {
        super.onResume();
        DBManager.open_db();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        DBManager.close_db();
    }

    public void startMenuActivity(View v) {
        login = findViewById(R.id.ETlogin);
        //запоминаем логин текущего пользователя
        //пригодится в активити со списком покупок
        current_user = login.getText().toString();
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }

    public void startSighUpActivity(View v) {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }
}