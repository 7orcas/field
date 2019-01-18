package com.sevenorcas.johnnyapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class MumActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mum);

        ImageView iv = (ImageView)findViewById(R.id.jennyImg);
        ScaleImage.scale(iv, R.drawable.jenny, this);
    }
}
