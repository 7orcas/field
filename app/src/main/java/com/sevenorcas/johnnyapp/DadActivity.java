package com.sevenorcas.johnnyapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class DadActivity  extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dad);

        ImageView iv = (ImageView)findViewById(R.id.johnImg);
        ScaleImage.scale(iv, R.drawable.john, this);
    }
}
