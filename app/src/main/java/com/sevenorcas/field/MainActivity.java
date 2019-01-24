package com.sevenorcas.field;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sevenorcas.field.graph.GraphActivity;

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
 *           https://sourceforge.net/projects/jrandtest/ ????
 *           https://github.com/stamfest/randomtests ????
 *
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setLabel (R.id.startTxt, getResources().getString(R.string.start_label));

        Button graphBtn = findViewById(R.id.graphBtn);
        graphBtn.setText(getResources().getString(R.string.start));
        graphBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GraphActivity.class));
            }
        });

    }


    private void setLabel(int id, String sting){
        TextView txt = findViewById(id);
        txt.setText(sting);
        txt.setFocusable(false);
    }
}
