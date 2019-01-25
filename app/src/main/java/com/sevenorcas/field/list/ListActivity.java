package com.sevenorcas.field.list;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.sevenorcas.field.BaseActivity;
import com.sevenorcas.field.R;
import com.sevenorcas.field.db.AppDatabase;
import com.sevenorcas.field.db.Graph;
import com.sevenorcas.field.db.GraphRepo;
import com.sevenorcas.field.graph.GraphActivity;
import com.sevenorcas.field.graph.wrapper.Config;
import com.sevenorcas.field.graph.wrapper.GraphI;
import com.sevenorcas.field.graph.wrapper.State;
import com.sevenorcas.field.graph.wrapper.Wrapper;
import com.sevenorcas.field.result.ResultActivity;

import java.lang.ref.WeakReference;
import java.util.List;


/**
 * Display a trial run result
 */
public class ListActivity extends BaseActivity implements GraphI {

    private ListView listView;
    private ListActivity thisObject;
    private List<Graph> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thisObject = this;
        setContentView(R.layout.activity_list);
        listView = (ListView)findViewById(R.id.listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Intent in = new Intent(getApplicationContext(), ResultActivity.class);
                Graph g = list.get(i);

                in.putExtra(GRAPH_RESULT, g.getState());
                in.putExtra(GRAPH_CONFIG, g.getConfig());

                startActivity(in);
            }
        });

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                GraphRepo repo = new GraphRepo(getApplicationContext());
                list = repo.getAll();

                ItemAdapter ia = new ItemAdapter(thisObject, list);
                listView.setAdapter(ia);
            }
        });

        final Button deleteBtn = findViewById(R.id.deleteBtn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Graph g = list.get(i);
                GraphRepo repo = new GraphRepo(getApplicationContext());
            }
        });


    }
}

