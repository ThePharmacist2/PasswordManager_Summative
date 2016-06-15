package com.example.dbrinkman.passwordmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Credits extends AppCompatActivity {

    TextView txtvwRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
        txtvwRating = (TextView)findViewById(R.id.txtvwRating);
    }





    public void Rating(View vw){
        txtvwRating.setText("Thanks for the rating!");
    }

    public void GoAddPassword(View vw){
        Intent goToAddPassworda = new Intent(this, AddPassworda.class);

        startActivityForResult(goToAddPassworda, 0);
    }

    public void ViewPasswords(View vw){

        Intent goToViewPasswords = new Intent(this, ViewPasswords.class);

        startActivityForResult(goToViewPasswords, 0);
    }
}
