package com.sevenorcas.johnnyapp.wrapper;

import android.app.Activity;
import android.content.res.Configuration;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.sevenorcas.johnnyapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;

/**
 * <i>GraphView</i> wrapper with <b>this</b> app's settings
 */
public class GraphWrapper {

    private Activity activity;
    private State state;
    private Config config;
    private boolean stop;

    /**
     *
     * Thanks to https://stackoverflow.com/questions/5112118/how-to-detect-orientation-of-android-device
     *
     * @param activity
     * @param graphState (can be null)
     */
    public GraphWrapper(@NotNull Activity activity, String graphState) {

        log("state is " + (graphState==null?"null":"instantiated"));

        this.activity = activity;
        GraphView g = (GraphView) activity.findViewById(R.id.graph);

        // customize viewport
        Viewport vp = g.getViewport();
        vp.setYAxisBoundsManual(true);
        vp.setXAxisBoundsManual(true);
        vp.setMinY(0);
        vp.setMaxY(1);
        vp.setMinX(1);
        vp.setScrollable(true);

        // customize y axis grid
        GridLabelRenderer r = g.getGridLabelRenderer();
        r.setNumVerticalLabels(3);
        r.setVerticalAxisTitle("Average RN's");
        r.setHorizontalAxisTitle("Time / half mins");
        r.setPadding(48);

        config = new Config();
        stop = false;
        state = new State(config);

        if (activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            state.maxX = 60;
        }
        g.addSeries(state.series);
        vp.setMaxX(state.maxX);

        if (graphState != null){
            state.deserialize(graphState);
        }
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


    public String getStateAsStringAndStop() {
//        stop = true;
        return state.serialize();
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


}
