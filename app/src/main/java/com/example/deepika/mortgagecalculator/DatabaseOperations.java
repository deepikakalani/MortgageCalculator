package com.example.deepika.mortgagecalculator;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.util.Log;

import com.example.deepika.mortgagecalculator.TableData.TableInfo;

/**
 * Created by deepika on 3/20/17.
 */

public class DatabaseOperations extends SQLiteOpenHelper {

    public static final int database_version = 1;
    public String CREATE_QUERY = "CREATE TABLE " + TableInfo.TABLE_NAME + "(" + TableInfo.STREET + " TEXT," + TableInfo.CITY + " TEXT," + TableInfo.STATE + " TEXT," + TableInfo.MONTHLY_PAYMENT + " TEXT);";

    public DatabaseOperations(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, TableInfo.DATABASE_NAME, null, database_version);
        Log.d("Database Operations", "DatabaseCreated");
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);
        Log.d("Database Operations", "Table is created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long insertInfo(DatabaseOperations dop, String street, String city, String state, String payment){
        SQLiteDatabase sqLiteDatabase = dop.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TableInfo.STREET, street);
        cv.put(TableInfo.CITY, city);
        cv.put(TableInfo.STATE, state);
        cv.put(TableInfo.MONTHLY_PAYMENT, payment);
        long k = sqLiteDatabase.insert(TableInfo.TABLE_NAME, null, cv);
        Log.d("Database Operations", "Data inserted");

        return k;
    }

    public Cursor getInfo(DatabaseOperations databaseOperations){
        SQLiteDatabase sqLiteDatabase = databaseOperations.getReadableDatabase();
        String[] columns = {"rowid", TableInfo.STREET, TableInfo.CITY, TableInfo.STATE, TableInfo.MONTHLY_PAYMENT};
        Cursor c = sqLiteDatabase.query(TableInfo.TABLE_NAME, columns, null, null, null, null, null);
        return c;
    }
}
