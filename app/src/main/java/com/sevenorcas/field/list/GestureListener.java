package com.sevenorcas.field.list;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.sevenorcas.field.graph.wrapper.GraphWrapper;


public class GestureListener extends GestureDetector.SimpleOnGestureListener {

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           float velocityX, float velocityY) {

        GraphWrapper.log("onFling: " + event1.toString() + event2.toString());
        return true;
    }
}
