package com.example.myfinances;

import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.content.ContentValues;

public class DBManager {
    private Context context;
    private DatabaseHelper DatabaseHelper;
    private SQLiteDatabase db;

    public DBManager (Context context) {
        this.context = context;
        DatabaseHelper = new DatabaseHelper(context);
    }

    public void open_db(){
        db = DatabaseHelper.getWritableDatabase();
    }

    public void close_db(){
        db.close();
    }
}
