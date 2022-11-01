package com.iot.iotapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.iot.iotapplication.database.ReadingsDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DisplayActivity extends AppCompatActivity {

    @BindView(R.id.graphView) LineChart graphView;
    @BindView(R.id.check_btn) Button checkBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        ButterKnife.bind(this);

        setGraphData();

        checkBtn.setOnClickListener(view -> {
            Intent intent = new Intent(DisplayActivity.this, DataActivity.class);
            startActivity(intent);
        });
    }

    private void setGraphData() {
        List<Reading> graphPoints = ReadingsDatabase.getInstance(this).readingDao().loadAllReadings();
        int listSize = graphPoints.size() - 1;

        graphView.setDragEnabled(true);
        graphView.setScaleEnabled(true);
        graphView.getAxisRight().setDrawGridLines(false);
        graphView.getAxisLeft().setDrawGridLines(false);
        graphView.getXAxis().setDrawGridLines(false);
        graphView.getAxisLeft().setDrawLabels(false);
        graphView.getAxisRight().setDrawLabels(false);
        graphView.getXAxis().setDrawLabels(false);

        List<Entry> tempValues = new ArrayList<>();
        List<Entry> humValues = new ArrayList<>();
        if (listSize != -1) {
            for (int i = listSize; i >= 0; i--) {
                try {
                    tempValues.add(new Entry(listSize - i, Integer.parseInt(graphPoints.get(i).getField1())));
                } catch (Exception e) {
                    tempValues.add(new Entry(listSize - i, 0));
                }

                try {
                    humValues.add(new Entry(listSize - i, Integer.parseInt(graphPoints.get(i).getField2())));
                } catch (Exception e) {
                    humValues.add(new Entry(listSize - i, 0));
                }
            }
        }

        LineDataSet set = new LineDataSet(tempValues, "Temperature");
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_graph);
        set.setFillAlpha(110);
        set.setDrawFilled(true);
        set.setFillDrawable(drawable);
        set.setColor(Color.rgb(0,119,182));
        set.setLineWidth(1.5f);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        LineDataSet set2 = new LineDataSet(humValues, "Humidity");
        Drawable drawable2 = ContextCompat.getDrawable(this, R.drawable.fade_graph_2);
        set2.setFillAlpha(110);
        set2.setDrawFilled(true);
        set2.setFillDrawable(drawable2);
        set2.setColor(Color.rgb(188,58,77));
        set2.setLineWidth(1.5f);
        set2.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set);
        dataSets.add(set2);

        LineData data = new LineData(dataSets);
        graphView.setData(data);
    }
}