package com.example.dbrinkman.passwordmanager;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class AddPassworda extends AppCompatActivity {
    EditText edtxtAccountType;
    EditText edtxtUsername;
    EditText edtxtPassword;
    EditText edtxtConfirmPassword;
    TextView txtvwUserInfo3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_passworda);
        edtxtAccountType = (EditText)findViewById(R.id.edtxtAccountType);
        edtxtUsername = (EditText)findViewById(R.id.edtxtUsername);
        edtxtPassword = (EditText)findViewById(R.id.edtxtPassword);
        edtxtConfirmPassword = (EditText)findViewById(R.id.edtxtConfirmPassword);
        txtvwUserInfo3 = (TextView)findViewById(R.id.txtvwUserInfo3);
    }




    public void AddPassworda(View vw){

            new PasswordAdder().execute();
    }

    private class PasswordAdder extends AsyncTask<String, String, Boolean>
    {
        private SQLiteDatabase db2;
        private PplsPasswordDb pplsPasswordDb;
        private ContentValues cntntVals;
        private String accountType;
        private String username;
        private String pplsPassword;
        @Override
        protected void onPreExecute()
        {
            pplsPasswordDb = new PplsPasswordDb(AddPassworda.this, null, null, 0);
            accountType = edtxtAccountType.getText().toString();
            username = edtxtUsername.getText().toString();
            pplsPassword = edtxtPassword.getText().toString();

        }

        @Override
        protected Boolean doInBackground(String... params)
        {
            cntntVals = new ContentValues();
            cntntVals.put("ACCOUNT_TYPE", accountType);
            cntntVals.put("USERNAME", username);
            cntntVals.put("PASSWORD", pplsPassword);

            try
            {
                db2 = pplsPasswordDb.getWritableDatabase();
            }
            catch(SQLiteException sqlEx)
            {
                return false;
            }

            if (accountType.length() == 0 || username.length() == 0 || pplsPassword.length() == 0)
            {
                publishProgress("You must enter all fields to add password");
                return false;
            }
            else
            {

                pplsPasswordDb.insertElement(db2, cntntVals);
                publishProgress("Password has been added.");
                Log.i("Status update", "Shit ran");
            }

            pplsPasswordDb.close();
            return true;
        }

        @Override
        protected void onProgressUpdate(String... values)
        {
            txtvwUserInfo3.setText(values[0]);
        }
    }

    public void ViewPasswords(View vw){

            Intent goToViewPasswords = new Intent(this, ViewPasswords.class);

            startActivityForResult(goToViewPasswords, 0);
    }
}
