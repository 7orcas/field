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

    protected String formatTime (int seconds){
        if (seconds == 0){
            return "-";
        }
        if (seconds < 60){
            return String.format("%02d", seconds);
        }
        if (seconds < 360){
            return String.format("%02d:%02d", seconds / 60, seconds % 60);
        }
        int hrs = seconds / 360;
        int r = seconds - 360;
        return String.format("%02d:%02d:%02d", hrs, seconds / 60, seconds % 60);
    }


}
