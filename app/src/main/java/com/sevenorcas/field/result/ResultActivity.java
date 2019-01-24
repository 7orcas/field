package com.sevenorcas.field.result;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.sevenorcas.field.R;
import com.sevenorcas.field.graph.wrapper.Config;
import com.sevenorcas.field.graph.wrapper.GraphI;
import com.sevenorcas.field.graph.wrapper.State;
import com.sevenorcas.field.graph.wrapper.Wrapper;


/**
 * Display a trial run result
 */
public class ResultActivity extends Activity implements GraphI {

    private Wrapper wrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Config config = new Config();
        config.decode((String)getIntent().getSerializableExtra(GRAPH_CONFIG));

        wrapper = new Wrapper(this, config);
        wrapper.deserialize((String)getIntent().getSerializableExtra(GRAPH_RESULT));
        State state = wrapper.getState();
        wrapper.createGraph(state.getLastX()).addSeries(state.addAllDataPoints());

        ((TextView)findViewById(R.id.minYTxt)).setText(R.string.min_y);
        ((TextView)findViewById(R.id.minYTxtV)).setText("" + state.getMinY());
        ((TextView)findViewById(R.id.maxYTxt)).setText(R.string.max_y);
        ((TextView)findViewById(R.id.maxYTxtV)).setText("" + state.getMaxY());

        Button saveBtn = findViewById(R.id.saveBtn);
        saveBtn.setText(R.string.save);

    }

}

