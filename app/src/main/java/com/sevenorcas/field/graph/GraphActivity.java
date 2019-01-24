package com.sevenorcas.field.graph;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.sevenorcas.field.R;
import com.sevenorcas.field.graph.wrapper.Config;
import com.sevenorcas.field.graph.wrapper.State;
import com.sevenorcas.field.graph.wrapper.Wrapper;
import com.sevenorcas.field.graph.wrapper.GraphI;
import com.sevenorcas.field.result.ResultActivity;


/**
 * Start a trial run
 */
public class GraphActivity extends Activity implements GraphI {

    private Wrapper wrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        final Config config = new Config().set30Seconds();
        wrapper = new Wrapper(this, config);
        State state = wrapper.getState();
        wrapper.createGraph(state.getMaxX()).addSeries(wrapper.getSeries());

        if (savedInstanceState != null) {
            wrapper.deserialize(savedInstanceState.getString(GRAPH_STATE))
                   .appendAllDataPoints();
        }

        final Button stopBtn = findViewById(R.id.stopBtn);
        stopBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                wrapper.stop();
                Intent i = new Intent(GraphActivity.this, ResultActivity.class);
                i.putExtra(GRAPH_RESULT, wrapper.getStateAsString());
                i.putExtra(GRAPH_CONFIG, config.getConfigAsString());
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Thread(new Runnable() {
            @Override
            public void run() {
                wrapper.runTrial();
            }
        }).start();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save state
        outState.putString(GRAPH_STATE, wrapper.getStateAsString());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (wrapper != null){
                wrapper.stop();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}

