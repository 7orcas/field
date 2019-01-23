package com.sevenorcas.field.result;

import android.app.Activity;
import android.os.Bundle;

import com.sevenorcas.field.R;
import com.sevenorcas.field.graph.wrapper.Config;
import com.sevenorcas.field.graph.wrapper.GraphI;
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
        wrapper.getState().setMaxX(999999);
        wrapper.deserialize((String)getIntent().getSerializableExtra(GRAPH_RESULT));
    }

}

