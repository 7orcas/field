package com.sevenorcas.field.result;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sevenorcas.field.BaseActivity;
import com.sevenorcas.field.MainActivity;
import com.sevenorcas.field.R;
import com.sevenorcas.field.db.GraphRepo;
import com.sevenorcas.field.graph.wrapper.Config;
import com.sevenorcas.field.graph.wrapper.GraphI;
import com.sevenorcas.field.graph.wrapper.State;
import com.sevenorcas.field.graph.wrapper.GraphWrapper;


/**
 * Display a trial run result
 *
 * Thanks to https://codereview.stackexchange.com/questions/59784/integer-seconds-to-formated-string-mmss
 */
public class ResultActivity extends BaseActivity implements GraphI {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        final Config config = new Config((String)getIntent().getSerializableExtra(GRAPH_CONFIG));

        GraphWrapper wrapper = new GraphWrapper(config);
        wrapper.deserialize((String)getIntent().getSerializableExtra(GRAPH_RESULT), (String)getIntent().getSerializableExtra(GRAPH_DATA));
        final State state = wrapper.getState();
        wrapper.createGraph(this, state.getLastX()).addSeries(state.addAllDataPoints());

        final Long graphId = (Long)getIntent().getSerializableExtra(GRAPH_ID);

        setLabel(R.id.descrTxt, R.string.descr);
        setEditText(R.id.descrTxtV, "" + config.getDescription());

        setLabel(R.id.frequencyTxt, R.string.frequency);
        setLabel(R.id.frequencyTxtV, "" + config.getFrequencyAsString(this));

        setLabel(R.id.durationTxt, R.string.duration);
        setLabel(R.id.durationTxtV, "" + formatTime(state.getDuration()));

        setLabel(R.id.minYTxt, R.string.min_y);
        setLabel(R.id.minYTxtV, "" + state.getMinY());

        setLabel(R.id.maxYTxt, R.string.max_y);
        setLabel(R.id.maxYTxtV, "" + state.getMaxY());

        Button saveBtn = findViewById(R.id.saveBtn);
        saveBtn.setText(R.string.save);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView txt = findViewById(R.id.descrTxtV);
                String descr = txt.getText().toString();
                descr = descr != null? descr : "";
                config.setDescription(descr);

                GraphRepo repo = new GraphRepo(getApplicationContext());
                if (graphId == null){
                    repo.insert(config, state, descr, 1);
                }
                else{
                    repo.update(graphId, descr);
                }

                startActivity(new Intent(ResultActivity.this, MainActivity.class));
            }
        });
    }


}

