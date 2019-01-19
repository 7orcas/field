package com.sevenorcas.johnnyapp.wrapper;

import android.app.Activity;
import android.content.res.Configuration;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.sevenorcas.johnnyapp.R;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Iterator;
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
     * @param graphWrapperState (can be null)
     */
    public GraphWrapper(@NotNull Activity activity, State graphWrapperState) {

        log("state is " + (graphWrapperState==null?"null":"instantiated"));

        this.activity = activity;
        state = graphWrapperState != null? graphWrapperState : new State();
        config = new Config();
        stop = false;

        GraphView g = (GraphView) activity.findViewById(R.id.graph);

        // data
        g.addSeries(state.series);

        // customize viewport
        Viewport vp = g.getViewport();
        vp.setYAxisBoundsManual(true);
        vp.setXAxisBoundsManual(true);
        vp.setMinY(0);
        vp.setMaxY(1);
        vp.setMinX(1);
        vp.setScrollable(true);

        if (activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            state.maxX = 60;
        }

        vp.setMaxX(state.maxX);

        // customize y axis grid
        GridLabelRenderer r = g.getGridLabelRenderer();
        r.setNumVerticalLabels(3);

    }

    /**
     * Trial Run of Random Numbers
     */
    public void runTrial() {
        // we add 100 new entries
        for (int i = 0; i < config.runsPerTrail; i++) {
            if (isStop()){
                log("trial run stopped");
                return;
            }

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    addEntry();
                }
            });

            // sleep to slow down the add of entries
            try {
                Thread.sleep(state.sampleMS);
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
    public void addEntry() {
        if (stop){
            return;
        }

        int n = ThreadLocalRandom.current().nextInt(2);

        state.series.appendData(new DataPoint(state.lastX++, n),
                state.lastX > state.maxX ? true : false,
                state.maxX);
    }


    public String getStateAsStringAndStop() {
        stop = true;
        return state.serialize();
    }

    public void setState(State state) {
        this.state = state;
    }

    public boolean isStop() {
        return stop;
    }

    static protected void log(String m){
        System.out.println("..." + m);
    }

    static public State getState (String s){
        return State.deserialize(s);
    }



}
