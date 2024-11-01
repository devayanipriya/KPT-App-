package com.example.kpt;

import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DecimalFormat;

public class MyAxisValueFormatter extends ValueFormatter {

    private DecimalFormat mFormat;

    public MyAxisValueFormatter()
    {
        mFormat = new DecimalFormat("#####.0");
    }

    @Override
    public String getFormattedValue(float value) {
        //return super.getFormattedValue(value);
        //return mFormat.format(value)+ "MYR";
        return "";
    }
}
