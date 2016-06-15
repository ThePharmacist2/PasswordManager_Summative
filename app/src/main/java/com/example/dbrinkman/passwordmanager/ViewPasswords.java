package com.example.dbrinkman.passwordmanager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewPasswords extends AppCompatActivity {
    TextView txtvwViewPasswords;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_passwords);
        txtvwViewPasswords = (TextView)findViewById(R.id.txtvwViewPasswords);
    }

    @Override
    protected void onStart(){
        new ShowItems().execute();
        super.onStart();
    }



    /* ShowItems gets called on by the onStart() and just retrieves all data entries
    and puts them in an array then sets the array to a txtvw */

    private class ShowItems extends AsyncTask<Void, String, Boolean>
    {
        private SQLiteDatabase db2;
        private PplsPasswordDb pplsPasswordDb;
        private Cursor crsrDBReader;
        private ArrayList<String> arylstAllItems;

        @Override
        protected void onPreExecute()
        {
            pplsPasswordDb = new PplsPasswordDb(ViewPasswords.this, null, null, 0);
            arylstAllItems = new ArrayList<String>();
        }

        @Override
        protected Boolean doInBackground(Void... params)
        {
            try
            {
                db2 = pplsPasswordDb.getReadableDatabase();
            }
            catch(SQLiteException sqlEx)
            {
                return false;
            }

            crsrDBReader = db2.rawQuery("SELECT * FROM PEOPLES_PASSWORD", null);

            if(crsrDBReader != null)
            {
                if(crsrDBReader.moveToFirst())
                {
                    while(crsrDBReader.isAfterLast() == false)
                    {
                        String strItem = "Account Type: " + crsrDBReader.getString(crsrDBReader.getColumnIndex("ACCOUNT_TYPE"))
                                + "\n Username: " + crsrDBReader.getString(crsrDBReader.getColumnIndex("USERNAME")) + "\n Password: "
                                + crsrDBReader.getString(crsrDBReader.getColumnIndex("PASSWORD")) + "\n" ;

                        arylstAllItems.add(strItem);

                        crsrDBReader.moveToNext();
                    }
                }
                crsrDBReader.close();
            }

            pplsPasswordDb.close();
            return true;
        }

        @Override
        protected void onPostExecute(Boolean value)
        {
            txtvwViewPasswords.setText(arylstAllItems.toString());
        }

    }

    /*GoToCredits and GoAddPassword just run intents on a button click to send
    the user to designated screens */

    public void GotToCredits(View vw){
        Intent goToCredits = new Intent(this, Credits.class);

        startActivityForResult(goToCredits, 0);
    }

    public void GoAddPassword(View vw){
        Intent goToAddPassworda = new Intent(this, AddPassworda.class);

        startActivityForResult(goToAddPassworda, 0);
    }

}
