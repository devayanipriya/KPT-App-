package com.example.kpt;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

public class MyValueFormatter extends ValueFormatter {

    private DecimalFormat mFormat;

    public MyValueFormatter()
    {
        mFormat = new DecimalFormat("#####");
    }

    @Override
    public String getFormattedValue(float value) {
        //return super.getFormattedValue(value);
        int val = (int) value;
        return String.valueOf(val);
    }
}
