package com.sevenorcas.field.graph.wrapper;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.text.PrecomputedText;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.sevenorcas.field.R;
import com.sevenorcas.field.graph.GraphActivity;
import com.sevenorcas.field.result.ResultActivity;

import java.net.URL;
import java.util.concurrent.ThreadLocalRandom;

/**
 * <i>GraphModel</i> wrapper with <b>this</b> app's settings
 */
public class GraphWrapper implements GraphI {

    private State state;
    private Config config;
    private boolean stop;
    private boolean running;

    /**
     * Graph wrapper with data and methods
     *
     * @param config
     */
    public GraphWrapper(Config config) {
        this.config = config;
        stop = false;
        running = false;
        state = new State(config);
    }


    /**
     * Deserialise the passed in graph state
     *
     * @param result
     * @param data
     */
    public State deserialize(String result, String data){
        state.decodeResult(result);
        state.decodeData(data);
        return state;
    }


    /**
     * Trial Run of Random Numbers
     */
    public void runTrials() {

        if (running || isStop()){
            return;
        }
        running = true;
        log("start runTrails");

        new RunTrialsX().execute();
    }

    private class RunTrialsX extends AsyncTask{

        private Handler mainHandler;

        public RunTrialsX() {
            mainHandler = new Handler(Looper.getMainLooper());
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            while (!isStop()){

                mainHandler.post(new Runnable() {
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

            log("finished runTrails");
            return null;
        }

    }


    public void putExtraInIntent (Intent i){
        i.putExtra(GRAPH_RESULT, state.encodeResult());
        i.putExtra(GRAPH_DATA, state.encodeData());
        i.putExtra(GRAPH_CONFIG, config.encode());
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

    public GraphView createGraph(Activity activity){

        if (activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            state.setMaxX(60);
        }

        GraphView g = (GraphView) activity.findViewById(R.id.graph);

        // customize viewport
        Viewport vp = g.getViewport();
        vp.setYAxisBoundsManual(true);
        vp.setXAxisBoundsManual(true);
        vp.setMinY(config.getMinY());
        vp.setMaxY(config.getMaxY());
        vp.setMinX(config.getMinX());
        vp.setMaxX(state.getMaxX());
        vp.setScrollable(true);

        // customize y axis grid
        GridLabelRenderer r = g.getGridLabelRenderer();
        r.setNumVerticalLabels(5);
        r.setVerticalAxisTitle(activity.getResources().getString(R.string.axis_y_label));
        r.setHorizontalAxisTitle(activity.getResources().getString(R.string.axis_x_label));

        r.setPadding(48);

        return g;
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

    static public void log(String m){
        System.out.println("..." + m);
    }

    public State getState() {
        return state;
    }

    public Config getConfig() { return config; }

    public LineGraphSeries<DataPoint> getSeries() {
        return state.getSeries();
    }
}
