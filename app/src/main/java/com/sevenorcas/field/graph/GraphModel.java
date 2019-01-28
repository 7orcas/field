package com.sevenorcas.field.graph;

import android.arch.lifecycle.ViewModel;

import com.sevenorcas.field.graph.wrapper.Config;
import com.sevenorcas.field.graph.wrapper.GraphWrapper;

public class GraphModel extends ViewModel {

    private GraphWrapper wrapper;

    public GraphModel() {
        Config config = new Config().set10Seconds();
        wrapper = new GraphWrapper(config);
    }

    public GraphWrapper getWrapper() {
        return wrapper;
    }

}

