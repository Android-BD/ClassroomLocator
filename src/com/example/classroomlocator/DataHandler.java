package com.example.classroomlocator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;
import java.util.jar.Attributes;

/**
 * Created by Mithi.yan on 10/11/2014.
 */
public class DataHandler {
    public static final String CLASSNAME = "name";
    public static final String SECTION = "email";
    public static final String TABLE_NAME = "ooad_table";
    public static final String DATABASE_NAME = "mithila_db";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_CREATE = "create table ooad_table (name text not null, email text not null)";

    DataBaseHelper dbhelper;
    Context ctx;
    SQLiteDatabase db;

    public DataHandler(Context ctx){
        this.ctx = ctx;
        dbhelper = new DataBaseHelper(ctx);
    }

    private static class DataBaseHelper extends SQLiteOpenHelper{

        public DataBaseHelper(Context ctx){
            super(ctx,DATABASE_NAME,null,DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
                sqLiteDatabase.execSQL(DATABASE_CREATE);
                //insertData("CS6359","002");


        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS ooad_table");
            onCreate(sqLiteDatabase);

        }
    }

    public DataHandler open(){
        db = dbhelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbhelper.close();
    }

    public long insertData(String name, String email){
        ContentValues content = new ContentValues();
        content.put(CLASSNAME, name);
        content.put(SECTION, email);
        return db.insertOrThrow(TABLE_NAME, null, content);
    }

    public Cursor returnData(){
        return db.query(TABLE_NAME, new String[] {CLASSNAME, SECTION}, null, null,null, null, null);
    }

}
