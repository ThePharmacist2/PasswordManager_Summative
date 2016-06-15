package com.example.dbrinkman.passwordmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by student on 08/06/2016.
 */

public class PplsPasswordDb extends SQLiteOpenHelper {

    private static final String DB_NAME = "pplsPassword";
    private static final int DB_VERSION = 1;

    public PplsPasswordDb(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db2)
    {
        updateMyDatabase(db2, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db2, int oldVersion, int newVersion) {
        updateMyDatabase(db2, oldVersion, newVersion);
    }

    private void updateMyDatabase(SQLiteDatabase db2, int oldVersion, int newVersion){
        db2.execSQL("CREATE TABLE PEOPLES_PASSWORD (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ACCOUNT_TYPE TEXT, " +
                "USERNAME TEXT, " +
                "PASSWORD TEXT);");

        Log.i("updateMyDatabase(): ", "SQL line just ran.");
    }

    public void insertElement(SQLiteDatabase db2, ContentValues newContent){

        db2.insert("PEOPLES_PASSWORD", null, newContent);

    }

    public int alterElement(SQLiteDatabase db2, ContentValues alteredContent, String where,
                            String[] whereArgs){

        return db2.update("PEOPLES_PASSWORD", alteredContent, where, whereArgs);

    }

    public int deleteElement(SQLiteDatabase db2, String where, String[] whereArgs){

        return db2.delete("PEOPLES_PASSWORD", where, whereArgs);

    }

}