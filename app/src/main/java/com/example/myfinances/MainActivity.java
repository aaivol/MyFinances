package com.example.myfinances;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DBManager DBManager;
    private EditText ETlogin, ETparole;
    public static String current_user;
    private static String current_parole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBManager = new DBManager(this);
        ETlogin = findViewById(R.id.ETlogin);
        ETparole = findViewById(R.id.ETNumParole);
    }

    @Override
    protected void onResume() {
        super.onResume();
        DBManager.open_db();
    }
    @Override
    protected void onDestroy() {
        DBManager.close_db();
        super.onDestroy();
    }

    public void startMenuActivity(View v) {
        List<String> resultList = DBManager.db_get_user();

        current_user = ETlogin.getText().toString();
        current_parole = ETparole.getText().toString();

        for (int i = 0; i < resultList.size(); i+=2) {
            if (current_user.equals(resultList.get(i)) & (current_parole.equals(resultList.get(i + 1)))) {
                Intent intent = new Intent(this, Menu.class);
                startActivity(intent);
            }
        }
    }

    public void startSighUpActivity(View v) {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }
}