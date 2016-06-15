package com.example.dbrinkman.passwordmanager;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;



public class PasswordManager extends AppCompatActivity {

    EditText edtxtMasterPword;
    EditText edtxtRetypePword;
    TextView txtvwUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_manager);
        edtxtMasterPword = (EditText)findViewById(R.id.edtxtMasterPword);
        edtxtRetypePword = (EditText)findViewById(R.id.edtxtRetypePword);
        txtvwUserInfo = (TextView)findViewById(R.id.txtvwUserInfo);
    }

    /*The onStart() method checks for if there is a password and if there is it runs intent
    "goToLoginScreen
     */

    @Override
    protected void onStart() {

        Cursor searchCursor;
        PasswordDb passwordDb = new PasswordDb(this, null, null, 0);
        SQLiteDatabase db;
        String[] elementsToSearch = {"1"};
        try {
            db = passwordDb.getReadableDatabase();
            searchCursor = db.query("PASSWORD", new String[]{"MASTER_PASSWORD", "HAS_PASSWORD"},
                    "HAS_PASSWORD = ?", elementsToSearch, null, null, null);

            if (searchCursor.moveToFirst()) {
                if (searchCursor.getInt(1) == 1) {
                    searchCursor.close();
                    db.close();
                    Intent goToLoginScreen = new Intent(this, LoginScreen.class);

                    startActivityForResult(goToLoginScreen, 0);
                } else {
                    searchCursor.close();
                    db.close();
                }
            }

        }catch(SQLiteException e){

            txtvwUserInfo.setText("Database not found");
        }

        super.onStart();

    }

    /*The CreatePword method checks if both the edtxt fields match and if they do
    then it inserts the password into the database and that is now your master password.
     */

    public void CreatePword(View vw) {
        PasswordDb passwordDb = new PasswordDb(this, null, null, 0);
        SQLiteDatabase db;
        ContentValues passwordValues = new ContentValues();
        String password;
        int hasPassword = 0;

        try {
            db = passwordDb.getWritableDatabase();
            if (!edtxtMasterPword.getText().toString().equals(edtxtRetypePword.getText().toString())) {
                txtvwUserInfo.setText("Passwords do not match!");
                edtxtMasterPword.setText("");
                edtxtRetypePword.setText("");
            } else {
                password = edtxtMasterPword.getText().toString();
                hasPassword = 1;
                passwordValues.put("MASTER_PASSWORD", password);
                passwordValues.put("HAS_PASSWORD", hasPassword);
                passwordDb.insertElement(db, passwordValues);

                txtvwUserInfo.setText("Password successfully created");
                db.close();

            }
        } catch (SQLiteException e) {

            txtvwUserInfo.setText("Password could not be created");
        }
    }

    /* The GoLogin method runs intent goToLoginScreen */

    public void GoLogin(View vw){
        Intent goToLoginScreen = new Intent(this, LoginScreen.class);

        startActivityForResult(goToLoginScreen, 0);
    }

}
