package com.example.myfinances;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

public class BuyList extends AppCompatActivity {
    private DBManager DBManager;
    private EditText ETgname, ETgprice;
    private String get_good_name;
    private TextView TV_good_card, TV_toAlert, TV_cost;
    private View clicked_card;
    private static String get_good_category, get_user_id, current_user, get_good_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_list);
        DBManager = new DBManager(this);
        current_user = getIntent().getStringExtra("EXTRA_USER_LOGIN");
    }

    @Override
    protected void onResume() {
        super.onResume();
        DBManager.open_db();

        String id = find_current_user_id();
        List<String> resultList = DBManager.db_get_goods();

        //берем id текущего пользователя
        for (int i = 0; i < resultList.size(); i+=2) {
            if (id.equals(resultList.get(i))) { //если текущий ID == ID товара
                add_good(resultList.get(i+1)); // функция записи имени товара в карту
            }
        }

    }
    @Override
    protected void onDestroy() {
        DBManager.close_db();
        super.onDestroy();
    }


    // добавить товар в список на экране
    public void add_good(String goodname){
        LinearLayout good_card = (LinearLayout) getLayoutInflater().inflate(R.layout.good_card, null);
        TV_good_card = good_card.findViewById(R.id.tv_good_name);

        LinearLayout.LayoutParams good_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        good_params.setMargins(20, 30, 0, 0);
        good_card.setLayoutParams(good_params);

        LinearLayout goods_list = (LinearLayout) findViewById(R.id.goods); // лэйаут с карточками товаров

        TV_good_card.setText(goodname); //записать имя товара в указанный ТВ
        goods_list.addView(good_card); //добавить карточку в лэйаут
    }

    // окно удаления товара
    public void CreateDeleteDialog(View v){
        TV_toAlert = v.findViewById(R.id.tv_good_name);//имя текущего товара

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(TV_toAlert.getText().toString());
        builder.setMessage(R.string.cost_attention);
        ConstraintLayout cl = (ConstraintLayout) getLayoutInflater().inflate(R.layout.delete_card, null);
        ETgprice = cl.findViewById(R.id.ET_good_price);
        TV_cost = cl.findViewById(R.id.costerror);
        builder.setView(cl);

        builder.setPositiveButton(R.string.bought, new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TV_cost.setVisibility(View.INVISIBLE);
                // берем категорию!!! из карточки в списке (надеюсь сробит)
                get_good_category = get_category_from_db(TV_toAlert.getText().toString());
                get_user_id = find_current_user_id();

                //берем стоимость из ETgprice
                get_good_price = ETgprice.getText().toString();
                //заносим стоимость товара в таблицу STATISTICS !
                if (!get_good_price.equals("")){
                    DBManager.db_update_statictics(find_current_user_id(), get_good_price, get_good_category);
                    //удаляем из GOODS товар
                    DBManager.db_delete_good(TV_toAlert.getText().toString());
                    //перезагрузка окна
                    finish();
                    startActivity(getIntent());
                }
                else {
                    ETgprice.setBackgroundColor(getColor(R.color.bright_orange));
                }
            }
        });
        builder.setNeutralButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //удаляем из GOODS товар
                DBManager.db_delete_good(TV_toAlert.getText().toString());
                //перезагрузка окна
                finish();
                startActivity(getIntent());
            }
        });
        builder.setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public String get_category_from_db(String name) {
        String result="";
        List<String> categoriesList = DBManager.db_get_category();

        //берем id текущего пользователя
        for (int i = 0; i < categoriesList.size(); i+=2) {
            if (name.equals(categoriesList.get(i))) {
                result = (categoriesList.get(i+1));
            }
        }
        return result;
    };

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

    // окно добавления товара
    public void CreateAddDialog(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.add_dialog);
        builder.setMessage(R.string.add_msg);

        ConstraintLayout cl = (ConstraintLayout) getLayoutInflater().inflate(R.layout.activity_dialog_add, null);
        Spinner mySpinner = (Spinner) cl.findViewById(R.id.SP_good_category);
        ETgname = cl.findViewById(R.id.ET_good_name);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(BuyList.this,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.categories));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        builder.setView(cl);

        builder.setPositiveButton(R.string.add_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // берем имя товара и категорию из АлертДайлог
                get_good_name = ETgname.getText().toString();
                get_good_category = mySpinner.getSelectedItem().toString();
                get_user_id = find_current_user_id();

                //заносим товар в базу данных под ID который нашли
                if (!get_good_name.equals("")) {
                    DBManager.db_insert_good(get_user_id, get_good_name, get_good_category);
                    //тут же вписываем в активити
                    // (в след раз при открытии она запишется уже из ф-ии onResume() )
                    add_good(get_good_name);
                }
            }
        });
        builder.setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
