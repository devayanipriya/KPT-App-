package com.example.kpt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Feedback extends AppCompatActivity {

    String selectedRate = null;
    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rating_select);

        db = new DBHandler(this);

        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        SharedPreferences divPrefs = getSharedPreferences("DIVPREFS", MODE_PRIVATE);

        SharedPreferences userPrefs = getSharedPreferences("USERPREFS",MODE_PRIVATE);

        SharedPreferences ratePrefs = getSharedPreferences("RATEPREFS",MODE_PRIVATE);

        Button Excbutton = (Button) findViewById(R.id.excellent);
        Excbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("BUTTONS", "User tapped the Excellent");
                selectedRate = "Excellent";

                SharedPreferences.Editor editor = ratePrefs.edit();
                editor.putString("ratePrefs", selectedRate);
                editor.apply();

                //Snackbar.make(v, userPrefs.getString("userPrefs", "Guest") + divPrefs.getString("divPrefs", "none") + ratePrefs.getString("ratePrefs", "-"), Snackbar.LENGTH_LONG).setAction("Action", null).show();
                insertData(userPrefs.getString("userPrefs", "Guest"), divPrefs.getString("divPrefs", "none"), ratePrefs.getString("ratePrefs", "-"));
                //openFinalGuest();
            }
        });

        Button Avgbutton = (Button) findViewById(R.id.average);
        Avgbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("BUTTONS", "User tapped the Average");
                selectedRate = "Average";

                SharedPreferences.Editor editor = ratePrefs.edit();
                editor.putString("ratePrefs", selectedRate);
                editor.apply();

                //Snackbar.make(v, userPrefs.getString("userPrefs", "Guest") + divPrefs.getString("divPrefs", "none") + ratePrefs.getString("ratePrefs", "-"), Snackbar.LENGTH_LONG).setAction("Action", null).show();
                insertData(userPrefs.getString("userPrefs", "Guest"), divPrefs.getString("divPrefs", "none"), ratePrefs.getString("ratePrefs", "-"));
                //openFinalGuest();
            }
        });

        Button Poorbutton = (Button) findViewById(R.id.poor);
        Poorbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("BUTTONS", "User tapped the Poor");
                selectedRate = "Poor";

                SharedPreferences.Editor editor = ratePrefs.edit();
                editor.putString("ratePrefs", selectedRate);
                editor.apply();

                //Snackbar.make(v, userPrefs.getString("userPrefs", "Guest") + divPrefs.getString("divPrefs", "none") + ratePrefs.getString("ratePrefs", "-"), Snackbar.LENGTH_LONG).setAction("Action", null).show();
                insertData(userPrefs.getString("userPrefs", "Guest"), divPrefs.getString("divPrefs", "none"), ratePrefs.getString("ratePrefs", "-"));
                //openFinalGuest();
            }
        });

        Button prevButton = (Button) findViewById(R.id.Previous);
        prevButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("BUTTONS", "User tapped the Prev");
                finish();
                }
        });

    }

    public void openFinalGuest(){
        Intent intent = new Intent(this, FinalGuest.class);
        startActivity(intent);
    }

    public void insertData(String user, String div, String rate)
    {
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        boolean isInserted = db.insertData(user, date, div, rate);

        if (isInserted == true)
        {
            Toast.makeText(Feedback.this, "Rating has been submitted.", Toast.LENGTH_SHORT).show();
            openFinalGuest();;
        }
        else
            Toast.makeText(Feedback.this, "Sorry, something's wrong.", Toast.LENGTH_SHORT).show();
    }

}