package com.sevenorcas.johnnyapp.wrapper;

/**
 * Convenience class to store <b>this</b> app's configuration
 */
public class Config {
    /** Number random numbers generated per data point   */ protected int rngPerRun;
    /** Sampling delay (milli seconds)                   */ protected int delayMS;

    protected Config() {
        rngPerRun = 10000;
        delayMS = 1000 * 60;
    }
}
