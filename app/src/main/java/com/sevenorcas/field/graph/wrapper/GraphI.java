package com.sevenorcas.field.graph.wrapper;

public interface GraphI {
    String GRAPH_STATE          = "com.sevenorcas.field.graphstate";
    String GRAPH_RESULT         = "com.sevenorcas.field.graphresult";
    String GRAPH_DATA           = "com.sevenorcas.field.graphdata";
    String GRAPH_CONFIG         = "com.sevenorcas.field.graphconfig";

    String DELIMIT_1            = ",";
    String DELIMIT_2            = "=";
    String DELIMIT_3            = "|";

    String STATE_MAX            = "mx";
    String STATE_LAST           = "lx";
    String STATE_DATA_POINTS    = "dp";
    String STATE_MIN_Y          = "x1";
    String STATE_MAX_Y          = "x2";

    String CONFIG_RNG_PER_RUN   = "c1";
    String CONFIG_DEPLAY_MS     = "c2";
    String CONFIG_DEPLAY_FACTOR = "c6";
    String CONFIG_MIN_Y         = "c3";
    String CONFIG_MAX_Y         = "c4";
    String CONFIG_MIN_X         = "c5";
    String CONFIG_DESCRIPTION   = "c7";
}
