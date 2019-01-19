package com.sevenorcas.johnnyapp;

import android.app.Activity;
import android.os.Bundle;

import com.sevenorcas.johnnyapp.wrapper.GraphWrapper;
import com.sevenorcas.johnnyapp.wrapper.State;


/**
 *
 * Thanks to https://www.ssaurel.com/blog/create-a-real-time-line-graph-in-android-with-graphview/
 */
public class GraphActivity1 extends Activity {

    private static final String GRAPH_STATE = "com.sevenorcas.johnnyapp.graphstate";

    private GraphWrapper gw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

System.out.println("...onCreate called");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);


        // If we have a saved state then we can restore it now
        State s = null;
        if (savedInstanceState != null) {
System.out.println("...returning from saved state");
            s = GraphWrapper.getState(savedInstanceState.getString(GRAPH_STATE));
        }

        gw = new GraphWrapper(this, s);
    }

    @Override
    protected void onResume() {
        super.onResume();

System.out.println("...onResume called");

        // we're going to simulate real time with thread that append data to the graph
        new Thread(new Runnable() {
            @Override
            public void run() {
                gw.runTrial();
            }
        }).start();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Stop wrapper and save state
        outState.putString(GRAPH_STATE, gw.getStateAsStringAndStop());
    }

}

