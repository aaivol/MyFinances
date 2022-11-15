package com.example.myfinances;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class Menu extends AppCompatActivity {
    public static String current_user;
    TextView TVhello;
    String[] months = {"February", "May", "July", "August"};
    int[] days = {28, 31, 30, 31};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        current_user = getIntent().getStringExtra("EXTRA_USER_LOGIN");
        String text = getString(R.string.welcome_messages, current_user);
        TVhello = findViewById(R.id.hello_user);
        TVhello.setText(text);

        setupPieChart();
    }

    public void startBuyListActivity(View v) {
        Intent intent = new Intent(this, BuyList.class);
        intent.putExtra("EXTRA_USER_LOGIN", current_user);
        startActivity(intent);
    }

    public void setupPieChart(){
        List<PieEntry> pieEntries = new ArrayList<>();
        for (int i = 0; i < months.length; i++){
            pieEntries.add(new PieEntry(days[i], months[i]));
        }

        PieDataSet dataSet = new PieDataSet(pieEntries, null);
        dataSet.setColors(ColorTemplate.LIBERTY_COLORS);
        dataSet.setDrawValues(false);
        PieData data = new PieData(dataSet);

        PieChart pie = (PieChart) findViewById(R.id.piechart);
        pie.setData(data);
        pie.setDescription(null);

        pie.setDrawHoleEnabled(true);
        pie.setHoleColor(Color.TRANSPARENT);
        pie.setHoleRadius(50f);

        pie.invalidate();
        pie.animateY(650);

        //legend attributes
        Legend legend = pie.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setTextSize(12);
        legend.setFormSize(20);
        legend.setFormToTextSpace(6);
    }

}