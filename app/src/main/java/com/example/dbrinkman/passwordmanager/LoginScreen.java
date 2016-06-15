package com.example.dbrinkman.passwordmanager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LoginScreen extends AppCompatActivity {

    EditText edtxtLogin;
    TextView txtvwUserInfo2;
    String masterPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        edtxtLogin = (EditText) findViewById(R.id.edtxtLogin);
        txtvwUserInfo2 = (TextView) findViewById(R.id.txtvwUserInfo2);
    }

    @Override
    protected void onStart(){
        new RetrieveMasterPassword().execute();
        super.onStart();
    }




   private class RetrieveMasterPassword extends AsyncTask<Void, String, Boolean> {
        private SQLiteDatabase db;
        private PasswordDb passwordDb;
        private Cursor searchCursor;
        private String[] elementsToSearch;

        @Override
        protected void onPreExecute() {
            passwordDb = new PasswordDb(LoginScreen.this, null, null, 0);
            elementsToSearch = new String[]{"1"};
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            try {
                db = passwordDb.getReadableDatabase();
                searchCursor = db.query("PASSWORD", new String[]{"MASTER_PASSWORD", "HAS_PASSWORD"},
                        "HAS_PASSWORD = ?", elementsToSearch, null, null, null);

                if (searchCursor.moveToFirst()) {
                    masterPassword = searchCursor.getString(0);
                    Log.i("msg", masterPassword);
                    Log.i("msg","doInBackground Ran");
                    searchCursor.close();
                    //passwordDb.close();
                    db.close();
                } else {
                    Log.i("msg","doInBackground Did Not Run");
                    searchCursor.close();
                    //passwordDb.close();
                    db.close();
                }

            } catch (SQLiteException e) {
                return false;

            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean Value) {

        }
    }




    public void AddPassworda(View vw){

        Intent AddPassworda = new Intent(this, AddPassworda.class);

        startActivity(AddPassworda);

        if(edtxtLogin.getText().toString().equals(masterPassword)){

            Log.i("msg", edtxtLogin.getText().toString());
            Log.i("msg", masterPassword);

            Intent goToAddPassworda = new Intent(this, AddPassworda.class);

            startActivity(goToAddPassworda);
            //startActivityForResult(goToAddPassworda, 0);

        }else{
            txtvwUserInfo2.setText("Wrong Password, Phone Will Self Destruct in T-10 seconds");
        }
    }
}
