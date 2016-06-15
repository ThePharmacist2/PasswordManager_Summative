package com.example.dbrinkman.passwordmanager;

/**
 * Created by student on 27/05/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PasswordDb extends SQLiteOpenHelper {

    private static final String DB_NAME = "password";
    private static final int DB_VERSION = 1;

    public PasswordDb(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        updateMyDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("CREATE TABLE PASSWORD (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "MASTER_PASSWORD TEXT, " +
                "HAS_PASSWORD INTEGER);");

        Log.i("updateMyDatabase(): ", "SQL line just ran.");
    }

    public void insertElement(SQLiteDatabase db, ContentValues newContent){

        db.insert("PASSWORD", null, newContent);

    }

    public int alterElement(SQLiteDatabase db, ContentValues alteredContent, String where,
                            String[] whereArgs){

        return db.update("PASSWORD", alteredContent, where, whereArgs);

    }

    public int deleteElement(SQLiteDatabase db, String where, String[] whereArgs){

        return db.delete("PASSWORD", where, whereArgs);

    }

}