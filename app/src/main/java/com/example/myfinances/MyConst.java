package com.example.myfinances;

public class MyConst {
    //database
    public static final String DATABASE_NAME = "my_finances.db";
    public static final int VERSION = 1;
    //table users
    //-------
    public static final String USERS = "users";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_LOGIN = "login";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PAROLE = "parole";
    //table goods
    //-------
    public static final String GOODS = "goods";
    public static final String USER_ID = "user_id";
    //COLUMN_NAME
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_PRICE = "price";
    //table statistics
    //-------
    public static final String STATISTICS = "statistics";
    //USER_ID
    public static final String COLUMN_FOOD = "food";
    public static final String COLUMN_MEDICAL = "medical";
    public static final String COLUMN_TECH = "tech";
    public static final String COLUMN_TRAVEL = "travel";
    public static final String COLUMN_HOME = "home";
    public static final String COLUMN_OTHER = "other";
    public static final String COLUMN_GLOBAL = "_global";
}
