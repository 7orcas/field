package com.sevenorcas.johnnyapp.wrapper;

import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Iterator;

/**
 * Convenience class to store the graph's state
 */
public class State {

    protected LineGraphSeries<DataPoint> series;
    protected int maxX;
    protected int lastX;
    protected int sampleMS;

    protected State() {
        series = new LineGraphSeries<DataPoint>();
        maxX = 30; //Default (Portrait)
        lastX = 0;
        sampleMS = 300;
    }

    protected String serialize(){
        StringBuffer sb = new StringBuffer();
        sb.append("mx=" + maxX);
        sb.append(",lx=" + lastX);
        sb.append(",ms=" + sampleMS);

        if (!series.isEmpty()) {
            sb.append(",dp=");
            Iterator<DataPoint> it = series.getValues(series.getLowestValueX(), series.getHighestValueX());
            while (it.hasNext()) {
                DataPoint d = it.next();
                boolean l = false;
                sb.append((l ? ":" : "") + d.getY());
                l = true;
            }
        }
        GraphWrapper.log("state serialize: " + sb.toString());
        return sb.toString();
    }

    private String toStringX(){
        StringBuffer sb = new StringBuffer();
        sb.append("maxX=" + maxX);
        sb.append(", lastX=" + lastX);
        sb.append(", sampleMS=" + sampleMS);
        sb.append(", dp=");
        if (!series.isEmpty()) {
            Iterator<DataPoint> it = series.getValues(series.getLowestValueX(), series.getHighestValueX());
            boolean l = false;
            while (it.hasNext()) {
                DataPoint d = it.next();
                sb.append((l ? " " : "") + d.getY());
                l = true;
            }
        }
        return sb.toString();
    }


    static protected State deserialize(String s){
        State state = new State();

        if (s == null || s.isEmpty()){
            GraphWrapper.log("state deserialize: " + (s == null?"null" : "empty"));
            return state;
        }

        String[] sx = s.split(",");
        String dps = null;
        for (int i=0; i<sx.length; i++){

            String[] sx1 = sx[i].split("=");

            String k = sx1[0];
            String v = sx1[1];

            if (k.equals("dp")){
                dps = v;
            }
            if (k.equals("mx")){
                state.maxX = Integer.parseInt(v);
            }
            if (k.equals("lx")){
                state.lastX = Integer.parseInt(v);
            }
            if (k.equals("ms")){
                state.sampleMS = Integer.parseInt(v);
            }
        }

        //add data points last
        if (dps != null) {
            String[] sx2 = dps.split(":");
            for (int i=0; i<sx2.length; i++){
                String dp = sx2[i];
                state.series.appendData(new DataPoint(state.lastX++, Double.parseDouble(dp)),
                        i > state.maxX ? true : false,
                        state.maxX);
            }
        }

        GraphWrapper.log("state deserialize: " + state.toStringX());

        return state;

    }
}
