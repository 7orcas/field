package com.sevenorcas.johnnyapp;

import android.app.Activity;
import android.os.Bundle;

import com.sevenorcas.johnnyapp.GraphWrapper.State;

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
        State state = null;
        if (savedInstanceState != null) {
            System.out.println("...returning from saved state");
            state = (State)savedInstanceState.getSerializable(GRAPH_STATE);
        }

        gw = new GraphWrapper(this, state);
    }

    @Override
    protected void onResume() {
        super.onResume();

System.out.println("...onResume called");

        // we're going to simulate real time with thread that append data to the graph
        new Thread(new Runnable() {

            @Override
            public void run() {
                // we add 100 new entries
                for (int i = 0; i < 50; i++) {
                    if (gw.isStop()){
System.out.println("...run stopped");
                        return;
                    }

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            gw.addEntry();
                        }
                    });

                    // sleep to slow down the add of entries
                    try {
                        Thread.sleep(gw.getSampleMilliseconds());
                    } catch (InterruptedException e) {
                        // manage error ...
                    }
                }
System.out.println("...finished run");
            }
        }).start();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Stop wrapper and save state
        outState.putSerializable(GRAPH_STATE, gw.getStateAndStop());
    }

}

