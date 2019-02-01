package com.sevenorcas.field.graph;


import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;

import com.sevenorcas.field.graph.wrapper.GraphWrapper;

import java.sql.Wrapper;

import static android.arch.lifecycle.Lifecycle.Event.*;

public class GraphObserver implements LifecycleObserver {

    private Context context;
    private Lifecycle lifecycle;
    private GraphWrapper wrapper;

    public GraphObserver(Context context, Lifecycle lifecycle, GraphWrapper wrapper) {
        this.context = context;
        this.lifecycle = lifecycle;
        this.wrapper = wrapper;
    }

    @OnLifecycleEvent(ON_RESUME)
    public void onResumeListener() {
        if (wrapper.isRunning()){
            wrapper.appendAllDataPoints();
        }
        else {
            wrapper.runTrials(context);
        }
    }
}
