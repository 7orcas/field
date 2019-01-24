package com.sevenorcas.field;

import android.app.Activity;
import android.widget.TextView;

public class BaseActivity extends Activity {

    protected void setLabel(int id, int stingId){
        TextView txt = findViewById(id);
        txt.setText(stingId);
        txt.setFocusable(false);
    }

    protected void setLabel(int id, String sting){
        TextView txt = findViewById(id);
        txt.setText(sting);
        txt.setFocusable(false);
    }
}
