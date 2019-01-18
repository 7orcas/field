package com.sevenorcas.johnnyapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class GraphActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
//                new DataPoint(0, 1),
//                new DataPoint(1, 0),
//                new DataPoint(2, 0),
//                new DataPoint(3, 1),
//                new DataPoint(4, 0)
        });
        graph.addSeries(series);

        Viewport vp = graph.getViewport();
        vp.setXAxisBoundsManual(true);
        vp.setMinX(0);
        vp.setMaxX(1000);

        TrackerSimulation ts = new TrackerSimulation();
        for (int i=0;i<10;i++){
            series.appendData(new DataPoint(i, ts.getPoint()), true, 10);
        }

    }
}
