package com.sevenorcas.johnnyapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class IsabellaActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isabella);

        ImageView iv = (ImageView)findViewById(R.id.isabellaImg);
        ScaleImage.scale(iv, R.drawable.isabella, this);
    }
}
