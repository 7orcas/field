package com.sevenorcas.field.graph.wrapper;

import android.app.Activity;

import com.sevenorcas.field.R;

/**
 * Convenience class to store <b>this</b> app's configuration
 */
public class Config extends Base{
    /** Number random numbers generated per data point   */ protected int rngPerRun;
    /** Sampling delay (milli seconds)                   */ protected int delayMS;
    /** Sampling factor                                  */ protected int delayFactor;

    /** Minimum Y axis value                             */ protected double minY;
    /** Maximum Y axis value                             */ protected double maxY;
    /** Minimum X axis value                             */ protected double minX;

    /** Graph description                                */ protected String descr;


    public Config() {
        rngPerRun = 1000;
        delayFactor = 30;
        set30Seconds();

        minY = 0.4;
        maxY = 0.6;
        minX = 1;
        descr = "";
    }

    public Config setSeconds(){ return setTime(1); }
    public boolean isSeconds(){ return isTime(1); }

    public Config set10Seconds(){ return setTime(10); }
    public boolean is10Seconds(){
        return isTime(10);
    }

    public Config set30Seconds(){ return setTime(30); }
    public boolean is30Seconds(){
        return isTime(30);
    }

    public Config setMinutes(){ return setTime(60); }
    public boolean isMinutes(){
        return isTime(60);
    }

    public String getFrequencyAsString(Activity activity){
        switch (delayFactor){
            case 1 : return activity.getResources().getString(R.string.per_second);
            case 10 : return activity.getResources().getString(R.string.per_second_10);
            case 30 : return activity.getResources().getString(R.string.per_second_30);
            case 60 : return activity.getResources().getString(R.string.per_minute);
            default: return "?";
        }
    }


    private Config setTime(int t){
        delayFactor = t;
        delayMS = 1000 * t;
        return this;
    }
    private boolean isTime(int t){
        return delayFactor == t;
    }

    private String toStringX(){
        StringBuffer sb = new StringBuffer();
        sb.append("rngPerRun=" + rngPerRun);
        sb.append(", delayMS=" + delayMS);
        sb.append(", delayFactor=" + delayFactor);
        sb.append(", minY=" + minY);
        sb.append(", maxY=" + maxY);
        sb.append(", minX=" + minX);
        sb.append(", descr=" + descr);
        return sb.toString();
    }

    /**
     * Encode <b>this</b> object to a <code>String</code>
     * @return
     */
    public String encode(){
        StringBuffer sb = new StringBuffer();
        encodeField(CONFIG_RNG_PER_RUN, rngPerRun, sb);
        encodeField(CONFIG_DEPLAY_MS, delayMS, sb);
        encodeField(CONFIG_DEPLAY_FACTOR, delayFactor, sb);
        encodeField(CONFIG_MIN_Y, minY, sb);
        encodeField(CONFIG_MAX_Y, maxY, sb);
        encodeField(CONFIG_MIN_X, minX, sb);
        encodeField(CONFIG_DESCRIPTION, descr, sb);
        return sb.toString();
    }

    public void decode(String s){

        if (s == null || s.isEmpty()){
            return;
        }

        try {
            super.decode(s);
            rngPerRun   = getField(CONFIG_RNG_PER_RUN, rngPerRun);
            delayMS     = getField(CONFIG_DEPLAY_MS, delayMS);
            delayFactor = getField(CONFIG_DEPLAY_FACTOR, delayFactor);
            minY        = getField(CONFIG_MIN_Y, minY);
            maxY        = getField(CONFIG_MAX_Y, maxY);
            minX        = getField(CONFIG_MIN_X, minX);
            descr       = getField(CONFIG_DESCRIPTION, descr);

        } catch (Exception x){
            GraphWrapper.log("Exception: " + x.getMessage());
        }
    }

    public String getDescription() {
        return descr;
    }
    public void setDescription(String descr) {
        this.descr = descr;
    }

    public int getRngPerRun() {
        return rngPerRun;
    }

    public int getDelayMS() {
        return delayMS;
    }

    public int getDelayFactor() {
        return delayFactor;
    }

    public double getMinY() {
        return minY;
    }

    public double getMaxY() {
        return maxY;
    }

    public double getMinX() {
        return minX;
    }
}

