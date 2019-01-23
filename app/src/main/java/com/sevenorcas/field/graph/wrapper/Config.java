package com.sevenorcas.field.graph.wrapper;

/**
 * Convenience class to store <b>this</b> app's configuration
 */
public class Config extends Base{
    /** Number random numbers generated per data point   */ protected int rngPerRun;
    /** Sampling delay (milli seconds)                   */ protected int delayMS;

    /** Minimum Y axis value                             */ protected double minY;
    /** Maximum Y axis value                             */ protected double maxY;
    /** Minimum X axis value                             */ protected double minX;


    public Config() {
        rngPerRun = 1000;
        delayMS = 1000;// * 30;

        minY = 0.4;
        maxY = 0.6;
        minX = 1;
    }

    private String toStringX(){
        StringBuffer sb = new StringBuffer();
        sb.append("rngPerRun=" + rngPerRun);
        sb.append(", delayMS=" + delayMS);
        sb.append(", minY=" + minY);
        sb.append(", maxY=" + maxY);
        sb.append(", minX=" + minX);
        return sb.toString();
    }

    /**
     * Serialize <b>this</b> object to a <code>String</code>
     * @return
     */
    public String getConfigAsString(){
        StringBuffer sb = new StringBuffer();
        encodeField(CONFIG_RNG_PER_RUN, rngPerRun, sb);
        encodeField(CONFIG_DEPLAY_MS, delayMS, sb);
        encodeField(CONFIG_MIN_Y, minY, sb);
        encodeField(CONFIG_MAX_Y, maxY, sb);
        encodeField(CONFIG_MIN_X, minX, sb);

        Wrapper.log("config encode: " + sb.toString());

        return sb.toString();
    }

    public void decode(String s){

        if (s == null || s.isEmpty()){
            Wrapper.log("decode: " + (s == null?"null" : "empty"));
            return;
        }

        try {
            super.decode(s);
            rngPerRun = getField(CONFIG_RNG_PER_RUN, rngPerRun);
            delayMS   = getField(CONFIG_DEPLAY_MS, delayMS);
            minY      = getField(CONFIG_MIN_Y, minY);
            maxY      = getField(CONFIG_MAX_Y, maxY);
            minX      = getField(CONFIG_MIN_X, minX);

        } catch (Exception x){
            Wrapper.log("Exception: " + x.getMessage());
        }
        Wrapper.log("config decode: " + toStringX());
    }

}

