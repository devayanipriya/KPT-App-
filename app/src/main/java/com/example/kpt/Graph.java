package com.example.kpt;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import com.example.kpt.data.model.Rating;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class Graph extends AppCompatActivity {

    private HorizontalBarChart bChart;
    TextView selectedDate;
    ImageButton datePicker;
    DBHandler db;
    private SimpleCursorAdapter adapter;
    List<Rating> ratings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_display);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        selectedDate = findViewById(R.id.selectedDate);
        datePicker = findViewById(R.id.datePicker);

        db = new DBHandler(this);
        //Cursor cursor = db.fetch();

        ratings = db.getAllRatings();

        // Setting click listener for the date picker button
        datePicker.setOnClickListener(view -> DatePickerdialog());

        SharedPreferences divPrefs = getSharedPreferences("DIVPREFS", MODE_PRIVATE);
        SharedPreferences userPrefs = getSharedPreferences("USERPREFS",MODE_PRIVATE);
        SharedPreferences ratePrefs = getSharedPreferences("RATEPREFS",MODE_PRIVATE);

        Button Resetbutton = (Button) findViewById(R.id.reset);

        bChart = (HorizontalBarChart) findViewById(R.id.barchart);
        //bChart.setMaxVisibleValueCount(100);

        //bChart.animateXY(2000, 2000);
        //bChart.invalidate();

        create_graph(getXAxisValues(), getYAxisValues());


        Resetbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("BUTTONS", "User tapped the Reset");
                //Snackbar.make(v, "reset tapped", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                selectedDate.setText("Overall");
                ratings = db.getAllRatings();
                create_graph(getXAxisValues(), getYAxisValues());

            }
        });

    }

    public void DatePickerdialog()
    {
        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("Filter");

        MaterialDatePicker<Pair<Long, Long>> datePicker = builder.build();
        datePicker.addOnPositiveButtonClickListener(selection -> {

            Long startDate = selection.first;
            Long endDate = selection.second;

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String startDateString = sdf.format(new Date(startDate));
            String endDateString = sdf.format(new Date(endDate));

            String selectedDateRange = startDateString + " - " + endDateString;

            selectedDate.setText(selectedDateRange);

            //update graph
            ratings = db.getRangeRatings(startDateString, endDateString);
            create_graph(getXAxisValues(), getYAxisValues());

        });

        datePicker.show(getSupportFragmentManager(), "DATE_PICKER");
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> labels = new ArrayList();
        labels.add("BAD");
        labels.add("BG");
        labels.add("BKK");
        labels.add("BP");
        labels.add("BSM");
        labels.add("JB");
        labels.add("JS");
        labels.add("PNC");
        labels.add("PP");
        labels.add("PTNC");
        labels.add("PPA");
        labels.add("UI");
        labels.add("WEZ");
        return labels;
    }


    private ArrayList<ArrayList<Integer> > getYAxisValues() {

        ArrayList<ArrayList<Integer> > yList =
                new ArrayList<>();

        ArrayList<Integer> poorList = new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
        ArrayList<Integer> avgList = new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
        ArrayList<Integer> excList = new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));

        for (Rating cn : ratings) {

            for (int i=0 ; i < getXAxisValues().size() ; i++)
            {
                if (cn.getDiv().equals(getXAxisValues().get(i)))
                {
                    if (cn.getRate().equals("Poor"))
                    {
                        poorList.set(i, poorList.get(i) + 1);
                    }
                    else if (cn.getRate().equals("Average"))
                    {
                        avgList.set(i, avgList.get(i) + 1);
                    }
                    else if (cn.getRate().equals("Excellent"))
                    {
                        excList.set(i, excList.get(i) + 1);
                    }
                }
            }
        }

        //Toast.makeText(Graph.this, poorList + " X " + avgList + " X " + excList,Toast.LENGTH_LONG).show();

        for (int i=0 ; i < getXAxisValues().size() ; i++)
        {
            ArrayList<Integer> yVals = new ArrayList<>();

            yVals.add(poorList.get(i));
            yVals.add(avgList.get(i));
            yVals.add(excList.get(i));
            yList.add(yVals);
        }
        return yList;
    }

    public void create_graph(ArrayList<String> graph_label, ArrayList<ArrayList<Integer> > qty) {

        try {
            bChart.setDrawBarShadow(false);
            bChart.setDrawValueAboveBar(true);
            bChart.getDescription().setEnabled(false);
            bChart.setPinchZoom(false);

            bChart.setDrawGridBackground(false);
            bChart.getDescription().setTextColor(Color.WHITE);


            YAxis yAxis = bChart.getAxisLeft();
            yAxis.setValueFormatter(new ValueFormatter() {

                @Override
                public String getFormattedValue(float value) {

                    int val = (int) value;
                    return String.valueOf(val);
                }

            });

            yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);


            yAxis.setGranularity(1f);
            yAxis.setGranularityEnabled(true);
            yAxis.setTextColor(Color.WHITE);

            bChart.getAxisRight().setEnabled(false);


            XAxis xAxis = bChart.getXAxis();
            xAxis.setGranularity(1f);
            xAxis.setGranularityEnabled(true);
            //xAxis.setCenterAxisLabels(true);
            xAxis.setDrawGridLines(true);
            xAxis.setTextColor(Color.WHITE);
            xAxis.setLabelCount(qty.size(), false);

            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setValueFormatter(new IndexAxisValueFormatter(graph_label));


            ArrayList<BarEntry> yVals1 = new ArrayList<>();

            for (int i = 0; i < getXAxisValues().size(); i++) {
                yVals1.add(new BarEntry(i, new float[] {qty.get(i).get(0), qty.get(i).get(1), qty.get(i).get(2)}));
            }


            BarDataSet set1;

            if (bChart.getData() != null && bChart.getData().getDataSetCount() > 0) {
                set1 = (BarDataSet) bChart.getData().getDataSetByIndex(0);
                set1.setValues(yVals1);
                bChart.getData().notifyDataChanged();
                bChart.notifyDataSetChanged();
            } else {
                // create 2 datasets with different types
                set1 = new BarDataSet(yVals1, "Rating");
                set1.setColors(new int[] {Color.RED, Color.YELLOW, Color.GREEN});
                set1.setStackLabels(new String[] {"Poor", "Average", "Excellent"});


                ArrayList<IBarDataSet> dataSets = new ArrayList<>();
                dataSets.add(set1);

                BarData data = new BarData(dataSets);
                bChart.setData(data);
            }

            bChart.setFitBars(true);


            Legend l = bChart.getLegend();
            l.setFormSize(12f); // set the size of the legend forms/shapes
            l.setForm(Legend.LegendForm.SQUARE); // set what type of form/shape should be used

            //l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_INSIDE);
            l.setTextSize(10f);
            l.setTextColor(Color.WHITE);
            l.setXEntrySpace(5f); // set the space between the legend entries on the x-axis
            l.setYEntrySpace(5f); // set the space between the legend entries on the y-axis

            bChart.invalidate();

            bChart.animateY(2000);

        } catch (Exception ignored) {
        }
    }
}