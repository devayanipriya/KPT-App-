package com.example.kpt.data;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kpt.DBHandler;
import com.example.kpt.data.model.LoggedInUser;
import com.example.kpt.ui.login.LoginActivity;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    String result;

    public Result<LoggedInUser> login(String username, String password, DBHandler dbHandler) {

        Log.d("checkresult", "login2 " + result);

        try {
            Log.d("checkresult", "login2 1 " + result);
            dbHandler.getWritableDatabase();
            Log.d("checkresult", "login2 2 " + result);
            result = dbHandler.getLoginCred(username, password);
            Log.d("checkresult", "login2 3 " + result);


            if (!result.equals(""))
            {
                // TODO: handle loggedInUser authentication
                LoggedInUser currentUser = new LoggedInUser(
                        java.util.UUID.randomUUID().toString(),
                        username);
                return new Result.Success<>(currentUser);
            }

            return new Result.Error(new IOException("Error logging in 0"));

        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}