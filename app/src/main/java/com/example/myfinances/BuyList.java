package com.example.myfinances;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

public class BuyList extends AppCompatActivity {
    private DBManager DBManager;
    private EditText ETgname;
    private String get_good_name;
    private TextView TV_good_card;
    private static String get_good_category, get_user_id, current_user;

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
                DBManager.db_insert_good(get_user_id, get_good_name, get_good_category);

                //тут же вписываем в активити
                // (в след раз при открытии она запишется уже из ф-ии onResume() )
                add_good(get_good_name);
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
