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
import com.sevenorcas.field.graph.wrapper.Config;
import com.sevenorcas.field.graph.wrapper.GraphI;
import com.sevenorcas.field.graph.wrapper.State;
import com.sevenorcas.field.graph.wrapper.Wrapper;

import java.lang.ref.WeakReference;
import java.util.List;


/**
 * Display a trial run result
 */
public class ListActivity extends BaseActivity implements GraphI {

    private ListView listView;
    private ListActivity thisObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thisObject = this;
        setContentView(R.layout.activity_list);

        listView = (ListView)findViewById(R.id.listView);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                GraphRepo repo = new GraphRepo(getApplicationContext());
                List<Graph> list = repo.getAll();

                ItemAdapter ia = new ItemAdapter(thisObject, list);
                listView.setAdapter(ia);
            }
        });

    }
}

