package com.sevenorcas.field.graph.wrapper;

import android.app.Activity;
import android.content.res.Configuration;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.sevenorcas.field.R;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;

/**
 * <i>GraphView</i> wrapper with <b>this</b> app's settings
 */
public class Wrapper {

    private Activity activity;
    private State state;
    private Config config;
    private boolean stop;

    /**
     * Graph wrapper with data and methods
     *
     * @param activity
     * @param config
     */
    public Wrapper(@NotNull Activity activity, Config config) {

        this.activity = activity;
        this.config = config;

        stop = false;
        state = new State(config);

        if (activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            state.maxX = 60;
        }
    }

    public GraphView createGraph(int max){
        GraphView g = (GraphView) activity.findViewById(R.id.graph);

        // customize viewport
        Viewport vp = g.getViewport();
        vp.setYAxisBoundsManual(true);
        vp.setXAxisBoundsManual(true);
        vp.setMinY(config.minY);
        vp.setMaxY(config.maxY);
        vp.setMinX(config.minX);
        vp.setMaxX(max);
        vp.setScrollable(true);

        // customize y axis grid
        GridLabelRenderer r = g.getGridLabelRenderer();
        r.setNumVerticalLabels(5);
        r.setVerticalAxisTitle(activity.getResources().getString(R.string.axis_y_label));
        r.setHorizontalAxisTitle(activity.getResources().getString(R.string.axis_x_label));

        r.setPadding(48);

        return g;
    }


    /**
     * Deserialise the passed in graph state
     *
     * @param graphState
     */
    public State deserialize(String graphState){
        state.decode(graphState);
        return state;
    }


    /**
     * Trial Run of Random Numbers
     */
    public void runTrial() {

        while (!isStop()){

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    addEntry();
                }
            });

            // pause between trial runs
            try {
                Thread.sleep(config.delayMS);
            } catch (InterruptedException e) {
                // manage error ...
            }
        }
        log("finished run");
    }



    /**
     * Add random data to graph
     *
     * Thanks to https://codereview.stackexchange.com/questions/146034/testing-a-random-number-generator
     */
    private void addEntry() {
        if (stop){
            return;
        }

        int tot = 0;
        for (int i = 0; i < config.rngPerRun; i++) {
            tot += ThreadLocalRandom.current().nextInt(2);
        }

        double ave = (double)tot / (double)config.rngPerRun;
        DataPoint dp = new DataPoint(state.lastX, ave);
        state.addDataPoint(dp);
    }


    public String getStateAsString() {
        return state.encode();
    }

    public void setState(State state) {
        this.state = state;
    }

    public boolean isStop() {
        return stop;
    }

    /**
     * Stop trial runs
     */
    public void stop() {
        stop = true;
    }

    static protected void log(String m){
        System.out.println("..." + m);
    }

    public State getState() {
        return state;
    }

    public LineGraphSeries<DataPoint> getSeries() {
        return state.getSeries();
    }
}
