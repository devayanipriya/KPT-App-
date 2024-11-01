package com.example.kpt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kpt.ui.login.LoginActivity;
import com.google.android.material.snackbar.Snackbar;

public class FinalGuest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thankyou_guest);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        SharedPreferences divPrefs = getSharedPreferences("DIVPREFS", MODE_PRIVATE);

        SharedPreferences userPrefs = getSharedPreferences("USERPREFS",MODE_PRIVATE);

        SharedPreferences ratePrefs = getSharedPreferences("RATEPREFS",MODE_PRIVATE);

        Button Exitbutton = (Button) findViewById(R.id.exit);

        Exitbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("BUTTONS", "User tapped the Exit");

                Intent intent = new Intent(FinalGuest.this, LoginActivity.class);
                startActivity(intent);
                finish();
                }
        });


    }
}