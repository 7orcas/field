package com.sevenorcas.johnnyapp.wrapper;

import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Convenience class to store the graph's state
 */
public class State implements GraphI{

    protected Config config;
    protected LineGraphSeries<DataPoint> series;
    protected List<DataPoint> dataPoints;
    protected int maxX;
    protected int lastX;


    protected State(Config config) {
        this.config = config;
        maxX = 30; //Default (Portrait)
        series = new LineGraphSeries<DataPoint>();
        dataPoints = new ArrayList<>();
        lastX = 0;
    }


    /**
     * Serialize <b>this</b> object to a <code>String</code>
     * @return
     */
    protected String serialize(){
        StringBuffer sb = new StringBuffer();
        sb.append(serializeField(STATE_MAX, maxX));
        sb.append(serializeField(STATE_LAST, lastX,true));

        StringBuffer sbx = new StringBuffer();
        for (int i=0;i<dataPoints.size();i++){
            DataPoint d = dataPoints.get(i);
            sbx.append((i!=0? STATE_DELIMIT_3 : "") + d.getY());
        }
        sb.append(serializeField(STATE_DATA_POINTS, sbx.toString(),true));

        GraphWrapper.log("state serialize: " + sb.toString());
        return sb.toString();
    }

    private String serializeField(String key, Object value){
        return serializeField(key, value, false);
    }
    private String serializeField(String key, Object value, boolean delimiter){
        return (delimiter?STATE_DELIMIT_1:"") + key + STATE_DELIMIT_2 + value;
    }


    private String toStringX(){
        StringBuffer sb = new StringBuffer();
        sb.append("max=" + maxX);
        sb.append(", last=" + lastX);
        sb.append(", dp=");
        for (int i=0;i<dataPoints.size();i++){
            DataPoint d = dataPoints.get(i);
            sb.append((i!=0? "|" : "") + d.getY());
        }
        return sb.toString();
    }


    protected void deserialize(String s){

        if (s == null || s.isEmpty()){
            GraphWrapper.log("state deserialize: " + (s == null?"null" : "empty"));
            return;
        }

        String[] sx = s.split(STATE_DELIMIT_1);
        String dps = null;
        for (int i=0; i<sx.length; i++){
            String[] sx1 = sx[i].split(STATE_DELIMIT_2);
            String k = sx1[0];
            String v = sx1[1];

            if (k.equals(STATE_DATA_POINTS) && v.length()>0){
                dps = v;
            }
            else if (k.equals(STATE_LAST)){
                lastX = Integer.parseInt(v);
            }
        }

        //add data points last
        if (dps != null) {
            String[] sx2 = dps.split("\\" + STATE_DELIMIT_3);
            for (int i=0; i<sx2.length; i++){
                DataPoint dp = new DataPoint(i, Double.parseDouble(sx2[i]));
                dataPoints.add(dp);
                series.appendData(dp, i > maxX ? true : false, maxX);
            }
        }

        GraphWrapper.log("state deserialize: " + toStringX());
    }

    protected void addDataPoint (DataPoint dp){
        dataPoints.add(dp);
        series.appendData(dp, lastX > maxX ? true : false, maxX);
        lastX++;
    }


}
