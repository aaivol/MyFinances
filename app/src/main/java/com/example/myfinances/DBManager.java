package com.example.myfinances;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.content.ContentValues;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Objects;

public class DBManager {
    private Context context;
    private DatabaseHelper DatabaseHelper;
    private SQLiteDatabase db;

    public DBManager (Context context) {
        this.context = context;
        DatabaseHelper = new DatabaseHelper(context);
    }

    public void db_insert(String login, String name, String parole){
        ContentValues cv = new ContentValues();
        cv.put(MyConst.COLUMN_LOGIN, login);
        cv.put(MyConst.COLUMN_NAME, name);
        cv.put(MyConst.COLUMN_PAROLE, parole);
        db.insert(MyConst.USERS,null, cv);
    }

    public void db_insert_good(String current_id, String name, String category){
        ContentValues cv = new ContentValues();
        cv.put(MyConst.USER_ID, current_id);
        cv.put(MyConst.COLUMN_NAME, name);
        cv.put(MyConst.COLUMN_CATEGORY, category);
        db.insert(MyConst.GOODS,null, cv);
        Log.println(Log.INFO, "INSERTED", "Good_INSERTED");
        Log.println(Log.INFO, "INSERTED", name);
    }

    public void db_update_statictics(String current_id, String price, String category){
        String value_to_increase;
        int intprice = Integer.parseInt(price);
        ContentValues cv = new ContentValues();
        cv.put(MyConst.USER_ID, current_id);

        if (db_get_statictics(current_id).isEmpty()) {
            db.insert(MyConst.STATISTICS, null, cv);
        }

        if (Objects.equals(category, "Еда")){
            if (db_get_statictics(current_id).get(0) != null){
                value_to_increase = db_get_statictics(current_id).get(0);
                intprice += Integer.parseInt(value_to_increase);
                price = Integer.toString(intprice);
            }
            cv.put(MyConst.COLUMN_FOOD, price);
        }

        if (Objects.equals(category, "Транспорт")){
            if (db_get_statictics(current_id).get(0) != null){
                value_to_increase = db_get_statictics(current_id).get(0);
                intprice += Integer.parseInt(value_to_increase);
                price = Integer.toString(intprice);
            }
            cv.put(MyConst.COLUMN_TRANS, price);
        }

        if (Objects.equals(category, "Лекарства")){
            if (db_get_statictics(current_id).get(1) != null){
                value_to_increase = db_get_statictics(current_id).get(1);
                intprice += Integer.parseInt(value_to_increase);
                price = Integer.toString(intprice);
            }
            cv.put(MyConst.COLUMN_MEDICAL, price);
        }

        if (Objects.equals(category, "Техника")){
            if (db_get_statictics(current_id).get(2) != null){
                value_to_increase = db_get_statictics(current_id).get(2);
                intprice += Integer.parseInt(value_to_increase);
                price = Integer.toString(intprice);
            }
            cv.put(MyConst.COLUMN_TECH, price);
        }

        if (Objects.equals(category, "Путешествия")){
            if (db_get_statictics(current_id).get(3) != null){
                value_to_increase = db_get_statictics(current_id).get(3);
                intprice += Integer.parseInt(value_to_increase);
                price = Integer.toString(intprice);
            }
            cv.put(MyConst.COLUMN_TRAVEL, price);
        }

        if (Objects.equals(category, "Дом")){
            if (db_get_statictics(current_id).get(4) != null){
                value_to_increase = db_get_statictics(current_id).get(4);
                intprice += Integer.parseInt(value_to_increase);
                price = Integer.toString(intprice);
            }
            cv.put(MyConst.COLUMN_HOME, price);
        }

        if (Objects.equals(category, "Другое")){
            if (db_get_statictics(current_id).get(5) != null){
                value_to_increase = db_get_statictics(current_id).get(5);
                intprice += Integer.parseInt(value_to_increase);
                price = Integer.toString(intprice);
            }
            cv.put(MyConst.COLUMN_OTHER, price);
        }

        db.update(MyConst.STATISTICS, cv, MyConst.USER_ID + "=?", new String[]{current_id});
        Log.println(Log.INFO, "INSERTED", category);
    }

    public List<String> db_check(){
        List<String> loginList = new ArrayList<>();

        Cursor cursor = db.query(MyConst.USERS, null, null,
                null, null, null, null);

        while (cursor.moveToNext()){
            String record1 = cursor.getString(cursor.getColumnIndexOrThrow(MyConst.COLUMN_LOGIN));
            loginList.add(record1);
        }
        cursor.close();
        return loginList;
    }

    public List<String> db_get_goods() {
        List<String> goodsList = new ArrayList<>();

        Cursor cursor = db.query(MyConst.GOODS, null, null,
                null, null, null, null);

        while (cursor.moveToNext()) {
            String goodID = cursor.getString(cursor.getColumnIndexOrThrow(MyConst.USER_ID));
            String goodNAME = cursor.getString(cursor.getColumnIndexOrThrow(MyConst.COLUMN_NAME));
            goodsList.add(goodID);
            goodsList.add(goodNAME);
        }
        cursor.close();
        return goodsList;
    }

    public List<String> db_get_statictics(String current_id) {
        List<String> statList = new ArrayList<>();

        Cursor cursor = db.query(MyConst.STATISTICS, null, null,
                null, null, null, null);

        while (cursor.moveToNext()) {
            String ID = cursor.getString(cursor.getColumnIndexOrThrow(MyConst.USER_ID));
            //какому аккаунту статистика
            String food_price = cursor.getString(cursor.getColumnIndexOrThrow(MyConst.COLUMN_FOOD));
            String tr_price = cursor.getString(cursor.getColumnIndexOrThrow(MyConst.COLUMN_TRANS));
            String med_price = cursor.getString(cursor.getColumnIndexOrThrow(MyConst.COLUMN_MEDICAL));
            String tec_price = cursor.getString(cursor.getColumnIndexOrThrow(MyConst.COLUMN_TECH));
            String trav_price = cursor.getString(cursor.getColumnIndexOrThrow(MyConst.COLUMN_TRAVEL));
            String home_price = cursor.getString(cursor.getColumnIndexOrThrow(MyConst.COLUMN_HOME));
            String oth_price = cursor.getString(cursor.getColumnIndexOrThrow(MyConst.COLUMN_OTHER));
            //добавлять только если ID текущего совпадает с найденным
            if (ID.equals(current_id)) {
                statList.add(food_price);
                statList.add(tr_price);
                statList.add(med_price);
                statList.add(tec_price);
                statList.add(trav_price);
                statList.add(home_price);
                statList.add(oth_price); //+6
                // id = i+=7
            }
        }
        cursor.close();
        return statList;
    }

    public void db_delete_good(String name){
        db.delete(MyConst.GOODS, MyConst.COLUMN_NAME + "=?", new String[]{name});
        Log.println(Log.INFO, "DEL", "Good_DELETED");
    }

    public List<String> db_get_category(){
        List<String> categories = new ArrayList<>();

        Cursor cursor = db.query(MyConst.GOODS, null, null,
                null, null, null, null);

        while (cursor.moveToNext()) {
            String goodNAME = cursor.getString(cursor.getColumnIndexOrThrow(MyConst.COLUMN_NAME));
            String category = cursor.getString(cursor.getColumnIndexOrThrow(MyConst.COLUMN_CATEGORY));
            categories.add(goodNAME);
            categories.add(category);
        }
        cursor.close();

        return categories;
    }

    public List<String> db_get_user(){
        List<String> resultList = new ArrayList<>();

        Cursor cursor = db.query(MyConst.USERS, null, null,
                null, null, null, null);

        while (cursor.moveToNext()){
            String login = cursor.getString(cursor.getColumnIndexOrThrow(MyConst.COLUMN_LOGIN));
            String parole = cursor.getString(cursor.getColumnIndexOrThrow(MyConst.COLUMN_PAROLE));
            resultList.add(login);
            resultList.add(parole);
        }
        cursor.close();
        return resultList;
    }

    public List<String> db_get_userID(){
        List<String> resultList = new ArrayList<>();

        Cursor cursor = db.query(MyConst.USERS, null, null,
                null, null, null, null);

        while (cursor.moveToNext()){
            String userID = cursor.getString(cursor.getColumnIndexOrThrow(MyConst.COLUMN_ID));
            String login = cursor.getString(cursor.getColumnIndexOrThrow(MyConst.COLUMN_LOGIN));
            resultList.add(userID);
            resultList.add(login);
        }
        cursor.close();
        return resultList;
    }

    public void open_db(){
        db = DatabaseHelper.getWritableDatabase();
    }

    public void close_db(){
        db.close();
    }
}
