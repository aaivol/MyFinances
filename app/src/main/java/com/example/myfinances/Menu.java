package com.example.myfinances;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;

import java.util.ArrayList;
import java.util.List;

public class Menu extends AppCompatActivity {
    public static String current_user;
    AnyChartView anyChartView;
    String[] months = {"February", "May", "July", "August"};
    int[] days = {28, 31, 30, 31};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        anyChartView = findViewById(R.id.piechart);
        setupChartView();
    }

    public void startBuyListActivity(View v) {
        current_user = getIntent().getStringExtra("EXTRA_USER_LOGIN");
        Intent intent = new Intent(this, BuyList.class);
        intent.putExtra("EXTRA_USER_LOGIN", current_user);
        startActivity(intent);
    }


    public void setupChartView(){
        Pie pie = AnyChart.pie();
        List<DataEntry> dataEntries = new ArrayList<>();

        for (int i = 0; i < months.length; i++){
            dataEntries.add(new ValueDataEntry(months[i], days[i]));
        }
        pie.data(dataEntries);
        anyChartView.setChart(pie);

    }
}