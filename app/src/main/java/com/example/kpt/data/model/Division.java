package com.example.kpt.data.model;

import android.util.Log;

public class Division {

    private String division;

    public Division(String division) {
        this.division = division;
    }

    public void setDivision(String division) {
        this.division = division;
        Log.d("DROPDOWN", this.division);
    }

    public String getDivision() {
        return division;
    }

}
