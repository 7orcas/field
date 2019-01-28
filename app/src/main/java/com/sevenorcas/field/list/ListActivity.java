package com.sevenorcas.field.list;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.sevenorcas.field.MainActivity;
import com.sevenorcas.field.R;
import com.sevenorcas.field.db.Graph;
import com.sevenorcas.field.db.GraphRepo;
import com.sevenorcas.field.graph.wrapper.GraphI;
import com.sevenorcas.field.result.ResultActivity;

import java.util.List;


/**
 * Display a trial run result
 */
public class ListActivity extends AppCompatActivity implements GraphI {

    private ListView listView;
    private ListActivity thisObject;
    private List<Graph> list;
    private GestureDetectorCompat mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mDetector = new GestureDetectorCompat(this, new GestureListener());

        Toolbar tb = findViewById(R.id.app_toolbar);
        setSupportActionBar(tb);

        thisObject = this;
        listView = (ListView)findViewById(R.id.listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Intent in = new Intent(getApplicationContext(), ResultActivity.class);
                Graph g = list.get(i);

                in.putExtra(GRAPH_RESULT, g.getResult());
                in.putExtra(GRAPH_DATA, g.getData());
                in.putExtra(GRAPH_CONFIG, g.getConfig());
                in.putExtra(GRAPH_ID, g.getId());

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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.app_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            startActivity(new Intent(ListActivity.this, ListActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setLabel(int id, String sting){
        TextView txt = findViewById(id);
        txt.setText(sting);
        txt.setFocusable(false);
    }
}

