package com.example.myfinances;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DBManager DBManager;
    private EditText ETlogin, ETparole;
    public static String current_user, current_parole;
    private TextView incorrectTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBManager = new DBManager(this);
        ETlogin = findViewById(R.id.ETlogin);
        ETparole = findViewById(R.id.ETNumParole);
        incorrectTV = findViewById(R.id.TVerror);
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
        incorrectTV.setVisibility(View.INVISIBLE);
        List<String> resultList = DBManager.db_get_user();

        current_user = ETlogin.getText().toString();
        current_parole = ETparole.getText().toString();

        for (int i = 0; i < resultList.size(); i+=2) {
            if (current_user.equals(resultList.get(i)) & (current_parole.equals(resultList.get(i + 1)))) {
                incorrectTV.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(this, Menu.class);
                intent.putExtra("EXTRA_USER_LOGIN", current_user);
                startActivity(intent);
                break;
            }
            if (i == resultList.size()-2){
                incorrectTV.setVisibility(View.VISIBLE);
            }
        }

    }

    public void startSighUpActivity(View v) {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }
}