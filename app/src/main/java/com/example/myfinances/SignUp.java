package com.example.myfinances;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
    }

    public void startMainActivity(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}