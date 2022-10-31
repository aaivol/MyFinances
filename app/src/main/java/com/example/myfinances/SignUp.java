package com.example.myfinances;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;

public class SignUp extends AppCompatActivity {
    private DBManager DBManager;
    private EditText l, n, p, rp;
    private TextView tv;
    private String login, name, parole, repeat_parole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        DBManager = new DBManager(this);
        l = findViewById(R.id.ETloginS);
        n = findViewById(R.id.ETNameS);
        p = findViewById(R.id.ETNumParoleS);
        rp = findViewById(R.id.ETNumParoleS2);
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

    public void startMainActivity() {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
    }

    public void saving_user(View v){
        login = l.getText().toString();
        name = n.getText().toString();
        parole = p.getText().toString();
        repeat_parole = rp.getText().toString();

        String letters = ".*[A-Za-z].*";

        //for debug
        tv = findViewById(R.id.TVdb);
        //list logins
        for (String record1: DBManager.db_check()){
            tv.append(record1);
            tv.append(" * ");
        }

        if (repeat_parole.equals(parole) & (!Objects.equals(parole, "")) & (!Objects.equals(login, "")) ) {
            DBManager.db_insert(login, name, parole);
            startMainActivity();
        }
        else if (!Objects.equals(parole, repeat_parole) & (!Objects.equals(parole, ""))){
            tv.setText("Пароли не совпадают!");
        }
        else if (login.equals("")){
            tv.setText("Логин не может быть пустым!");
        }
        else if (!login.matches(letters)){
            tv.setText("Логин должен содержать буквы!");
        }
        else if (login.contains(" ")){
            tv.setText("Логин не может содержать пробелы!");
        }
        else if (parole.equals("")){
            tv.setText("Придумайте пароль!");
        }
    }
}