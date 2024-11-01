package com.example.kpt;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.example.kpt.DivisionActivity;
import com.example.kpt.R;
import com.example.kpt.data.model.Division;
import com.example.kpt.data.model.LoggedInUser;

public class DivisionActivity extends AppCompatActivity implements OnItemSelectedListener  {

    String selectedItem = null;
    String currentUser = null;
    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.division_select);

        db = new DBHandler(this);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        SharedPreferences divPrefs = getSharedPreferences("DIVPREFS",MODE_PRIVATE);
        selectedItem = divPrefs.getString("divPrefs", "");

        SharedPreferences userPrefs = getSharedPreferences("USERPREFS",MODE_PRIVATE);
        currentUser = userPrefs.getString("userPrefs", "Guest");

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.division, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        parent.getItemAtPosition(pos);
        selectedItem = parent.getItemAtPosition(pos).toString();
        //Toast.makeText(getApplicationContext(), currentUser + " " + selectedItem, Toast.LENGTH_SHORT).show();

        if (pos != 0)
        {
            openFeedback();
        }
        else
        {
            //Toast.makeText(getApplicationContext(), "pos is " + pos, Toast.LENGTH_SHORT).show();
        }

    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences divPrefs = getSharedPreferences("DIVPREFS",0);
        SharedPreferences.Editor editor = divPrefs.edit();
        editor.putString("divPrefs", selectedItem);
        editor.apply();
    }

    public void openFeedback(){
        Intent intent = new Intent(this, Feedback.class);
        startActivity(intent);
    }

}
