package com.sevenorcas.johnnyapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.sevenorcas.johnnyapp.wrapper.GraphWrapper;
import com.sevenorcas.johnnyapp.wrapper.State;


/**
 *
 * Thanks to https://www.ssaurel.com/blog/create-a-real-time-line-graph-in-android-with-graphview/
 */
public class GraphActivity extends Activity {

    private static final String GRAPH_STATE = "com.sevenorcas.johnnyapp.graphstate";

    private GraphWrapper gw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        String s = null;
        if (savedInstanceState != null) {
            s = savedInstanceState.getString(GRAPH_STATE);
        }

        gw = new GraphWrapper(this, s);

        final Button stopBtn = findViewById(R.id.stopBtn);
        stopBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                gw.stop();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        // Save state
        outState.putString(GRAPH_STATE, gw.getStateAsStringAndStop());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (gw != null){
                gw.stop();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}

