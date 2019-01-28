package com.sevenorcas.field.graph;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.sevenorcas.field.R;
import com.sevenorcas.field.graph.wrapper.GraphWrapper;
import com.sevenorcas.field.graph.wrapper.GraphI;
import com.sevenorcas.field.result.ResultActivity;


/**
 * Start a trial run
 */
public class GraphActivity extends AppCompatActivity implements GraphI {

    private GraphWrapper wrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        GraphModel model = ViewModelProviders.of(this).get(GraphModel.class);
        wrapper = model.getWrapper();

        GraphObserver graphObserver = new GraphObserver(this, getLifecycle(), wrapper);
        getLifecycle().addObserver(graphObserver);

        wrapper.createGraph(this, -1)
               .addSeries(wrapper.getSeries());


        final Button stopBtn = findViewById(R.id.stopBtn);
        stopBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                wrapper.stop();
                Intent i = new Intent(GraphActivity.this, ResultActivity.class);
                wrapper.putExtraInIntent(i);
                startActivity(i);
            }
        });
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

