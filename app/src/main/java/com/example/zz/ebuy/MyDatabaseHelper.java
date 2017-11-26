package com.example.zz.ebuy;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String User="create table userdata("
            + "id integer primary key autoincrement,"
            + "username text,"
            + "password text)";
    private static final String Shop="create table shopdata("
            + "id integer primary key autoincrement,"
            + "shopname text,"
            + "shopimage text)";
    private static final String Good="create table gooddata("
            + "id integer primary key autoincrement,"
            + "goodname text,"
            + "goodprice integer)";
    private  static final String Car="create table cardata("
            + "id integer primary key autoincrement,"
            + "shopname text,"
            + "goodname text,"
            + "goodprice integer)";
    private Context mContext;
    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext =context ;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(User);
        db.execSQL(Shop);
        db.execSQL(Good);
        db.execSQL(Car);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists userdata") ;
        db.execSQL("drop table if exists shopdata");
        db.execSQL("drop table if exists gooddata");
        db.execSQL("drop table if exists cardata");
        onCreate(db) ;
    }
}