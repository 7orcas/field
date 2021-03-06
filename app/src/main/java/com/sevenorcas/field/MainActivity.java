package com.sevenorcas.field;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.sevenorcas.field.graph.GraphActivity;
import com.sevenorcas.field.list.ListActivity;

/**
 *
 *
 * Thanks to http://www.android-graphview.org/
 *           https://www.ssaurel.com/blog/create-a-real-time-line-graph-in-android-with-graphview/
 *           https://stackoverflow.com/questions/7394447/android-emulator-in-landscape-mode-screen-does-not-rotate
 *           http://code.hootsuite.com/orientation-changes-on-android/
 *           https://stackoverflow.com/questions/30279536/graphview-how-to-show-x-axis-label
 *           https://stackoverflow.com/questions/36231032/android-graph-view-y-axis-numbers-being-cut-out
 *           https://stackoverflow.com/questions/3614849/intercepting-the-back-button
 *           https://stackoverflow.com/questions/5112118/how-to-detect-orientation-of-android-device
 *           https://stackoverflow.com/questions/7493287/android-how-do-i-get-string-from-resources-using-its-name
 *
 *           Database
 *           https://developer.android.com/training/data-storage/room/referencing-data
 *           https://android.jlelse.eu/5-steps-to-implement-room-persistence-library-in-android-47b10cd47b24
 *           https://stackoverflow.com/questions/44167111/android-room-simple-select-query-cannot-access-database-on-the-main-thread
 *           https://medium.com/androiddevelopers/understanding-migrations-with-room-f01e04b07929
 *
 *           ToolBar
 *           https://developer.android.com/training/appbar/
 *           https://medium.com/@101/android-toolbar-for-appcompatactivity-671b1d10f354
 *
 *           Observers
 *           https://medium.com/@MinaSamy/android-architecture-components-lifecycle-433ace1ec05d
 *
 *           Lists
 *           http://www.vogella.com/tutorials/AndroidListView/article.html
 *           https://demonuts.com/android-listview-swipe-delete/
 *
 *           WakeLock
 *           https://stackoverflow.com/questions/4870667/how-can-i-use-getsystemservice-in-a-non-activity-class-locationmanager
 *
 *           https://sourceforge.net/projects/jrandtest/ ????
 *           https://github.com/stamfest/randomtests ????
 *
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO: Search crashes if no records

        Toolbar tb = findViewById(R.id.app_toolbar);
        setSupportActionBar(tb);

        setLabel (R.id.startTxt, getResources().getString(R.string.start_label));

        Button graphBtn = findViewById(R.id.graphBtn);
        graphBtn.setText(getResources().getString(R.string.start));
        graphBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GraphActivity.class));
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
            startActivity(new Intent(MainActivity.this, ListActivity.class));
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
