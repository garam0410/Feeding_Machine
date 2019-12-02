package com.example.feeding_machine_2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class GraphActivity extends AppCompatActivity {

    private int Mon = 2;
    private int Tue = 1;
    private int Wed = 2;
    private int Thr = 0;
    private int Fri = 5;
    private int Sat = 2;
    private int Sun = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        LineChart lineChart = (LineChart) findViewById(R.id.chart);


        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(Mon, 0));
        entries.add(new Entry(Tue, 1));
        entries.add(new Entry(Wed, 2));
        entries.add(new Entry(Thr, 3));
        entries.add(new Entry(Fri, 4));
        entries.add(new Entry(Sat, 5));
        entries.add(new Entry(Sun, 6));

        LineDataSet dataset = new LineDataSet(entries, "잔여 사료량");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Mon");
        labels.add("Tue");
        labels.add("Wed");
        labels.add("Thr");
        labels.add("Fri");
        labels.add("Sat");
        labels.add("Sun");


        LineData data = new LineData(labels, dataset);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
        dataset.setDrawCubic(true); //선 둥글게 만들기
        dataset.setDrawFilled(true); //그래프 밑부분 색칠

        lineChart.setData(data);
        lineChart.animateY(5000);
    }
}
