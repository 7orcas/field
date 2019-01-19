package com.sevenorcas.johnnyapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 *
 *
 * Thanks to http://www.android-graphview.org/
 *           https://www.ssaurel.com/blog/create-a-real-time-line-graph-in-android-with-graphview/
 *           https://stackoverflow.com/questions/7394447/android-emulator-in-landscape-mode-screen-does-not-rotate
 *           http://code.hootsuite.com/orientation-changes-on-android/
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

        final Button dadBtn = findViewById(R.id.dadBtn);
        dadBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DadActivity.class));
            }
        });

        final Button mumBtn = findViewById(R.id.mumBtn);
        mumBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MumActivity.class));
            }
        });

        final Button isabellaBtn = findViewById(R.id.isabellaBtn);
        isabellaBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, IsabellaActivity.class));
            }
        });

        final Button graphBtn = findViewById(R.id.graphBtn);
        graphBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GraphActivity1.class));
            }
        });

    }
}
