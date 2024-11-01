package com.example.kpt.ui.login;

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
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kpt.DBHandler;
import com.example.kpt.DivisionActivity;
import com.example.kpt.Feedback;
import com.example.kpt.Graph;
import com.example.kpt.R;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    DBHandler db;
    String currentUser = null;
    String result = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        db = new DBHandler(this);

        if (db.isEmpty("KPTUser"))
        {
            boolean isInserted = db.insertUser();

            if (isInserted == true)
            {
                Log.d("KPTUSER", "onCreate: user inserted");
            }
            else
            {
                Log.d("KPTUSER", "onCreate: user not inserted");
            }
        }

        if (db.isEmpty("KPTRating"))
        {
            boolean isInserted = db.insertBulkData();

            if (isInserted == true)
            {
                Log.d("KPTRATING", "onCreate: bulk data inserted");
            }
            else
            {
                Log.d("KPTRATING", "onCreate: bulk data not inserted");
            }
        }

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {

                    updateUiWithUser(loginResult.getSuccess());

                    if (loginResult.getSuccess().getDisplayName().equalsIgnoreCase("admin@gmail.com"))
                    {
                        //Toast.makeText(getApplicationContext(), "admin (" + loginResult.getSuccess().getDisplayName() + ")", Toast.LENGTH_SHORT).show();
                        openGraph();
                        //finish();
                    }
                    else
                    {
                        //Toast.makeText(getApplicationContext(), "not admin (" + loginResult.getSuccess().getDisplayName() + ")", Toast.LENGTH_SHORT).show();
                        openDivision();
                    }

                }
                setResult(Activity.RESULT_OK);

                //finish();

            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString(), db);
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);

                loginViewModel.login(usernameEditText.getText().toString(), passwordEditText.getText().toString(), db);

            }
        });
    }

    private void updateUiWithUser(LoggedInUserView model) {

        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_SHORT).show();

        SharedPreferences userPrefs = getSharedPreferences("USERPREFS",0);
        SharedPreferences.Editor editor = userPrefs.edit();
        editor.putString("userPrefs", model.getDisplayName());
        editor.apply();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }


    public void openDivision(){
        //Toast.makeText(getApplicationContext(), "OPEN DIV", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, DivisionActivity.class);
        startActivity(intent);
    }

    public void openGraph(){
        //Toast.makeText(getApplicationContext(), "OPEN GRAPH", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, Graph.class);
        startActivity(intent);
    }
}