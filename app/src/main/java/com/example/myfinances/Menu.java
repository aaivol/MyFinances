package com.example.myfinances;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
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
    private DBManager DBManager;
    public static String current_user;
    TextView TVhello;
    String[] months = {"February", "May", "July", "August"};
    int[] days = {28, 31, 30, 31};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        DBManager = new DBManager(this);

        current_user = getIntent().getStringExtra("EXTRA_USER_LOGIN");
        String text = getString(R.string.welcome_messages, current_user);
        TVhello = findViewById(R.id.hello_user);
        TVhello.setText(text);
    }

    @Override
    protected void onResume() {
        super.onResume();
        DBManager.open_db();

        setupPieChart();
    }
    @Override
    protected void onDestroy() {
        DBManager.close_db();
        super.onDestroy();
    }

    public int[] prices_to_int(){
        List<String> pricesList = DBManager.db_get_statictics(find_current_user_id());

        int[] newarr = new int[pricesList.size()];
        int a;

        for (int i = 0; i < pricesList.size(); i++) {
            a = Integer.parseInt(pricesList.get(i)); //TO_INT
            newarr[i] = a;
        }

        return newarr;
    }


    public void startBuyListActivity(View v) {
        Intent intent = new Intent(this, BuyList.class);
        intent.putExtra("EXTRA_USER_LOGIN", current_user);
        startActivity(intent);
    }

    // узнать ID текущего пользователя
    public String find_current_user_id(){
        String result = "-1";
        List<String> resultList = DBManager.db_get_userID();

        //берем id текущего пользователя
        for (int i = 0; i < resultList.size(); i+=2) {
            if (current_user.equals(resultList.get(i+1))) {
                result = (resultList.get(i));
            }
        }
        return result;
    }

    public void setupPieChart(){
        String[] categoriesList = getResources().getStringArray(R.array.categories);
        int[] priceList = prices_to_int();
        int cur_id = Integer.parseInt(find_current_user_id());

        List<PieEntry> pieEntries = new ArrayList<>();
        for (int i = 0; i < categoriesList.length; i++){
            if (priceList[i]!=0){
                pieEntries.add(new PieEntry(priceList[i], categoriesList[i]));
            }
        }

        final int[] MY_COLORS = {
                getResources().getColor(R.color.pie_white),
                getResources().getColor(R.color.pie_bright),
                getResources().getColor(R.color.pie_light),
                getResources().getColor(R.color.pie_blue),
                getResources().getColor(R.color.pie_no),
                getResources().getColor(R.color.pie_soft)};

        ArrayList<Integer> colors = new ArrayList<Integer>();
        for(int c: MY_COLORS) colors.add(c);

        PieDataSet dataSet = new PieDataSet(pieEntries, null);
        dataSet.setColors(colors);
        //dataSet.setColors(ColorTemplate.LIBERTY_COLORS);
        dataSet.setDrawValues(false);
        PieData data = new PieData(dataSet);

        PieChart pie = (PieChart) findViewById(R.id.piechart);
        pie.setData(data);
        pie.setDescription(null);
        pie.setDrawEntryLabels(false);

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