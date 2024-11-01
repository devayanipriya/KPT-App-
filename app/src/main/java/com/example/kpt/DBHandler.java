package com.example.kpt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.kpt.data.model.Rating;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    // creating a constant variables for our database.
    // below variable is for our database name.
    static final String DB_NAME = "KPTDB";

    // below int is our database version
    static final int DB_VERSION = 1;

    // below variable is for our table name.
    public static final String TABLE_NAME = "KPTRating";
    public static final String TABLE_NAME_2 = "KPTUser";

    // below variable is for our id column.
    public static final String ID_COL = "id";

    // below variable is for our name column
    public static final String NAME_COL = "name";

    // below variable id for our date column.
    public static final String DATE_COL = "date";

    // below variable id for our division column.
    public static final String DIV_COL = "division";

    // below variable for our rate column.
    public static final String RATE_COL = "rate";

    // below variable for our password column.
    public static final String PW_COL = "password";

    private static SQLiteDatabase db;

    private static final String query = "CREATE TABLE " + TABLE_NAME + " ("
            + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NAME_COL + " TEXT,"
            + DATE_COL + " TEXT,"
            + DIV_COL + " TEXT,"
            + RATE_COL + " TEXT )";

    private static final String query2 = "CREATE TABLE " + TABLE_NAME_2 + " ("
            + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NAME_COL + " TEXT ,"
            + PW_COL + " TEXT )";

    // creating a constructor for our database handler.
    public DBHandler(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        db = this.getWritableDatabase();
    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d("tabledb", "onCreate: created table");
        db.execSQL(query);
        db.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_2);
        onCreate(db);
    }

    public boolean insertData(String name, String date, String div, String rate) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME_COL, name);
        contentValues.put(DATE_COL, date);
        contentValues.put(DIV_COL, div);
        contentValues.put(RATE_COL, rate);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean insertUser() {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<String> KPT_User = new ArrayList<String>();

        KPT_User.add("admin@gmail.com");
        KPT_User.add("user1@gmail.com");
        KPT_User.add("user2@gmail.com");

        ArrayList<String> KPT_Pw = new ArrayList<String>();

        KPT_Pw.add("admin");
        KPT_Pw.add("user1");
        KPT_Pw.add("user2");

        ContentValues contentValues = new ContentValues();

        for (int i=0 ; i < KPT_User.size() ; i++)
        {
            contentValues.put(NAME_COL, KPT_User.get(i));
            contentValues.put(PW_COL, KPT_Pw.get(i));

            long result = db.insert(TABLE_NAME_2, null, contentValues);

            if (result == -1)
            {
                return false;
            }
            else
            {
                if (i == KPT_User.size())
                {
                    return true;
                }
            }

        }

        return true;
    }

    public boolean isEmpty(String TableName){

        SQLiteDatabase database = this.getReadableDatabase();
        long NoOfRows = DatabaseUtils.queryNumEntries(database,TableName);

        if (NoOfRows == 0){
            return true;
        } else {
            return false;
        }
    }

    public boolean insertBulkData() {
        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<String> KPT_User = new ArrayList<String>();

        KPT_User.add("user0@gmail.com");
        KPT_User.add("user1@gmail.com");
        KPT_User.add("user2@gmail.com");
        KPT_User.add("user3@gmail.com");
        KPT_User.add("user4@gmail.com");
        KPT_User.add("user5@gmail.com");
        KPT_User.add("user6@gmail.com");
        KPT_User.add("user7@gmail.com");
        KPT_User.add("user8@gmail.com");
        KPT_User.add("user9@gmail.com");
        KPT_User.add("user10@gmail.com");
        KPT_User.add("user11@gmail.com");

        ArrayList<String> KPT_Date = new ArrayList<String>();

        KPT_Date.add("2023-05-03");
        KPT_Date.add("2023-05-23");
        KPT_Date.add("2023-05-24");
        KPT_Date.add("2023-06-06");
        KPT_Date.add("2023-06-21");
        KPT_Date.add("2023-06-26");
        KPT_Date.add("2023-07-02");
        KPT_Date.add("2023-07-05");
        KPT_Date.add("2023-07-07");
        KPT_Date.add("2023-07-08");
        KPT_Date.add("2023-07-10");
        KPT_Date.add("2023-07-12");

        ArrayList<String> KPT_Div = new ArrayList<String>();

        KPT_Div.add("BG");
        KPT_Div.add("JB");
        KPT_Div.add("JB");
        KPT_Div.add("PTNC");
        KPT_Div.add("PP");
        KPT_Div.add("UI");
        KPT_Div.add("WEZ");
        KPT_Div.add("BAD");
        KPT_Div.add("BAD");
        KPT_Div.add("BP");
        KPT_Div.add("BG");
        KPT_Div.add("PNC");

        ArrayList<String> KPT_Rate = new ArrayList<String>();

        KPT_Rate.add("Excellent");
        KPT_Rate.add("Average");
        KPT_Rate.add("Excellent");
        KPT_Rate.add("Excellent");
        KPT_Rate.add("Poor");
        KPT_Rate.add("Poor");
        KPT_Rate.add("Excellent");
        KPT_Rate.add("Average");
        KPT_Rate.add("Excellent");
        KPT_Rate.add("Excellent");
        KPT_Rate.add("Poor");
        KPT_Rate.add("Excellent");

        ContentValues contentValues = new ContentValues();

        for (int i=0 ; i < KPT_User.size() ; i++)
        {
            contentValues.put(NAME_COL, KPT_User.get(i));
            contentValues.put(DATE_COL, KPT_Date.get(i));
            contentValues.put(DIV_COL, KPT_Div.get(i));
            contentValues.put(RATE_COL, KPT_Rate.get(i));

            long result = db.insert(TABLE_NAME, null, contentValues);

            if (result == -1)
            {
                return false;
            }
            else
            {
                if (i == KPT_User.size())
                {
                    return true;
                }
            }

        }

        return true;
    }

    public Cursor fetch() {
        String[] columns = new String[] { DBHandler.ID_COL, DBHandler.DATE_COL, DBHandler.DIV_COL, DBHandler.RATE_COL };
        Cursor cursor = db.query(DBHandler.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    // code to get all ratings in a list view
    public List<Rating> getAllRatings() {
        List<Rating> ratingList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Rating rating = new Rating();
                rating.setID(Integer.parseInt(cursor.getString(0)));
                rating.setName(cursor.getString(1));
                rating.setDate(cursor.getString(2));
                rating.setDiv(cursor.getString(3));
                rating.setRate(cursor.getString(4));
                // Adding contact to list
                ratingList.add(rating);
            } while (cursor.moveToNext());
        }

        // return contact list
        return ratingList;
    }

    // code to get all ratings in a list view
    public List<Rating> getRangeRatings(String startDate, String endDate) {
        List<Rating> ratingList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME +
                " WHERE " + DATE_COL + " BETWEEN '" +
                startDate + "' AND '" + endDate + "'";

        Log.d("getrange", "getRangeRatings: " + selectQuery);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Rating rating = new Rating();
                rating.setID(Integer.parseInt(cursor.getString(0)));
                rating.setName(cursor.getString(1));
                rating.setDate(cursor.getString(2));
                rating.setDiv(cursor.getString(3));
                rating.setRate(cursor.getString(4));
                // Adding contact to list
                ratingList.add(rating);
            } while (cursor.moveToNext());
        }

        // return contact list
        return ratingList;
    }

    // code to get all ratings in a list view
    public String getLoginCred(String name, String password) {

        String LoginResult = "";

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME_2 +
                " WHERE " + NAME_COL + " = '" +
                name + "' AND " + PW_COL + " = '" + password + "'";

        Log.d("getlogincred", "getLoginCred: " + selectQuery);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor!=null && cursor.getCount()>0)
        {
            cursor.moveToFirst();
            LoginResult = cursor.getString(1);
            cursor.close();
        }
        else
        {
            LoginResult =  "";
            cursor.close();
        }

        // return contact list
        return LoginResult;
    }


    // this method is use to add new course to our sqlite database.
    /*public void addNewRate(String username, String date, String div, String rate) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(NAME_COL, username);
        values.put(DATE_COL, date);
        values.put(DIV_COL, div);
        values.put(RATE_COL, rate);

        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }*/

}
