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
    private TextView tv, tvlogins;
    private String login, name, parole, repeat_parole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        DBManager = new DBManager(this);
        l = findViewById(R.id.ETloginS);
        n = findViewById(R.id.ETNameS);
        p = findViewById(R.id.ETParoleS);
        rp = findViewById(R.id.ETParoleS2);
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
        tvlogins = findViewById(R.id.logins);


        tv = findViewById(R.id.TVmsg);

        //list logins
        for (String record1: DBManager.db_check()){
            tvlogins.append(record1);
            tvlogins.append(" * ");
        }

        if (repeat_parole.equals(parole) & (!Objects.equals(parole, "")) & (!Objects.equals(login, "")) ) {
            DBManager.db_insert(login, name, parole);
            startMainActivity();
        }
        else if (!Objects.equals(parole, repeat_parole) & (!Objects.equals(parole, ""))){
            tv.setText("???????????? ???? ??????????????????!");
        }
        else if (login.equals("")){
            tv.setText("?????????? ???? ?????????? ???????? ????????????!");
        }
        else if (!login.matches(letters)){
            tv.setText("?????????? ???????????? ?????????????????? ??????????!");
        }
        else if (login.contains(" ")){
            tv.setText("?????????? ???? ?????????? ?????????????????? ??????????????!");
        }
        else if (parole.equals("")){
            tv.setText("???????????????????? ????????????!");
        }
    }
}