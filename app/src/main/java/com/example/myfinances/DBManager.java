package com.example.myfinances;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.content.ContentValues;
import android.util.Log;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

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
        Log.println(Log.INFO, "INSERTED", "HERE");
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

    public void open_db(){
        db = DatabaseHelper.getWritableDatabase();
    }

    public void close_db(){
        db.close();
    }
}
