package com.sevenorcas.johnnyapp;

import android.app.Activity;
import android.content.res.Configuration;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

/**
 * <i>GraphView</i> wrapper with <b>this</b> app's settings
 */
public class GraphWrapper {

    private State state;
    private boolean stop;

    /**
     *
     * Thanks to https://stackoverflow.com/questions/5112118/how-to-detect-orientation-of-android-device
     *
     * @param activity
     * @param graph state (can be null)
     */
    public GraphWrapper(@NotNull Activity activity, State graphWrapperState) {

System.out.println("...state is " + (graphWrapperState==null?"null":"instantiated"));

        state = graphWrapperState != null? graphWrapperState : new State();
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


    public int getSampleMilliseconds() {
        return state.sampleMS;
    }

    public State getStateAndStop() {
        stop = true;
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public boolean isStop() {
        return stop;
    }

    /**
     * Convenience class to store the graph's state
     */
    public class State implements Serializable{
        private LineGraphSeries<DataPoint> series;
        private int maxX;
        private int lastX;
        private int sampleMS;

        public State() {
            series = new LineGraphSeries<DataPoint>();
            maxX = 30; //Default (Portrait)
            lastX = 0;
            sampleMS = 300;
        }
    }


}
