package com.sevenorcas.johnnyapp;

import java.util.Random;

import android.app.Activity;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

/**
 *
 * Thanks to https://www.ssaurel.com/blog/create-a-real-time-line-graph-in-android-with-graphview/
 */
public class GraphActivity1 extends Activity {
    private static final Random RANDOM = new Random();
    private LineGraphSeries<DataPoint> series;
    private int lastX = 0;
    private int maxX = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        // we get graph view instance
        GraphView graph = (GraphView) findViewById(R.id.graph);
        // data
        series = new LineGraphSeries<DataPoint>();
        graph.addSeries(series);
        // customize a little bit viewport
        Viewport vp = graph.getViewport();
        vp.setYAxisBoundsManual(true);
        vp.setXAxisBoundsManual(true);
        vp.setMinY(0);
        vp.setMaxY(10);
        vp.setMinX(1);
        vp.setMaxX(maxX);
        vp.setScrollable(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // we're going to simulate real time with thread that append data to the graph
        new Thread(new Runnable() {

            @Override
            public void run() {
                // we add 100 new entries
                for (int i = 0; i < 100; i++) {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            addEntry();
                        }
                    });

                    // sleep to slow down the add of entries
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        // manage error ...
                    }
                }
            }
        }).start();
    }

    // add random data to graph
    private void addEntry() {
        // here, we choose to display max 10 points on the viewport and we scroll to end
        series.appendData(new DataPoint(lastX++, RANDOM.nextDouble() * 10d),
                lastX>maxX?true:false,
                maxX);
    }
}

