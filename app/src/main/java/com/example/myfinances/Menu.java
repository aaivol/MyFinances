package com.example.myfinances;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Menu extends AppCompatActivity {
    public static String current_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    public void startBuyListActivity(View v) {
        current_user = getIntent().getStringExtra("EXTRA_USER_LOGIN");
        Intent intent = new Intent(this, BuyList.class);
        intent.putExtra("EXTRA_USER_LOGIN", current_user);
        startActivity(intent);
    }
}