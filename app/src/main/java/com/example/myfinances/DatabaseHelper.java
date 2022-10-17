package com.example.myfinances;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, MyConst.DATABASE_NAME, null, MyConst.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE  TABLE IF NOT EXISTS " + MyConst.USERS + "(" +
                MyConst.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MyConst.COLUMN_LOGIN + " TEXT NOT NULL, " +
                MyConst.COLUMN_NAME + " TEXT, " +
                MyConst.COLUMN_PAROLE + " TEXT NOT NULL);");

        db.execSQL("CREATE  TABLE IF NOT EXISTS " + MyConst.GOODS + "(" +
                MyConst.USER_ID + " INTEGER NOT NULL," +
                MyConst.COLUMN_NAME + " TEXT NOT NULL, " +
                MyConst.COLUMN_CATEGORY + " TEXT NOT NULL, " +
                MyConst.COLUMN_PRICE + " INTEGER);");

        db.execSQL("CREATE  TABLE IF NOT EXISTS " + MyConst.STATISTICS + "(" +
                MyConst.USER_ID + " INTEGER NOT NULL," +
                MyConst.COLUMN_FOOD + " TEXT NOT NULL, " +
                MyConst.COLUMN_MEDICAL + " TEXT NOT NULL, " +
                MyConst.COLUMN_TECH + " TEXT NOT NULL);");



        // добавление начальных данных
        db.execSQL("INSERT INTO "+ MyConst.USERS +" (" + MyConst.COLUMN_LOGIN
                + ", " + MyConst.COLUMN_NAME  + " , " + MyConst.COLUMN_PAROLE  + ") VALUES ('test', 'test', '1234');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ MyConst.USERS);
        onCreate(db);
    }

}