package com.sevenorcas.field.result;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sevenorcas.field.BaseActivity;
import com.sevenorcas.field.MainActivity;
import com.sevenorcas.field.R;
import com.sevenorcas.field.db.AppDatabase;
import com.sevenorcas.field.db.GraphRepo;
import com.sevenorcas.field.graph.GraphActivity;
import com.sevenorcas.field.graph.wrapper.Config;
import com.sevenorcas.field.graph.wrapper.GraphI;
import com.sevenorcas.field.graph.wrapper.State;
import com.sevenorcas.field.graph.wrapper.Wrapper;


/**
 * Display a trial run result
 */
public class ResultActivity extends BaseActivity implements GraphI {

    private Wrapper wrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        final Config config = new Config();
        config.decode((String)getIntent().getSerializableExtra(GRAPH_CONFIG));

        wrapper = new Wrapper(this, config);
        wrapper.deserialize((String)getIntent().getSerializableExtra(GRAPH_RESULT));
        final State state = wrapper.getState();
        wrapper.createGraph(state.getLastX()).addSeries(state.addAllDataPoints());

        setLabel(R.id.minYTxt, R.string.min_y);
        setLabel(R.id.minYTxtV, "" + state.getMinY());

        setLabel(R.id.maxYTxt, R.string.max_y);
        setLabel(R.id.maxYTxtV, "" + state.getMaxY());

        Button saveBtn = findViewById(R.id.saveBtn);
        saveBtn.setText(R.string.save);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                GraphRepo repo = new GraphRepo(getApplicationContext());
                repo.insert(config, state, "test", 1);
            }
        });
    }

}

