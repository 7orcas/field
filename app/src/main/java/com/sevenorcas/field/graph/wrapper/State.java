package com.sevenorcas.field.graph.wrapper;

import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;

/**
 * Convenience class to store the graph's state
 */
public class State extends Base implements GraphI {

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
     * Encode <b>this</b> object to a <code>String</code>
     * @return
     */
    protected String encode(){
        StringBuffer sb = new StringBuffer();

        encodeField(STATE_MAX, maxX, sb);
        encodeField(STATE_LAST, lastX, sb);

        StringBuffer sbx = new StringBuffer();
        for (DataPoint d : dataPoints){
            encodeList(d.getY(), sbx);
        }
        encodeField(STATE_DATA_POINTS, sbx.toString(), sb);

        Wrapper.log("state encode: " + sb.toString());
        return sb.toString();
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


    protected void decode(String s){

        if (s == null || s.isEmpty()){
            Wrapper.log("state decode: " + (s == null?"null" : "empty"));
            return;
        }

        String dps = null;
        try {
            super.decode(s);
            lastX     = getField(STATE_LAST, lastX);
            dps       = getField(STATE_DATA_POINTS, "");
        } catch (Exception x){
            Wrapper.log("Exception: " + x.getMessage());
        }

        //add data points last
        if (dps != null && dps.length()>0) {
            String[] sx2 = dps.split("\\" + DELIMIT_3);
            for (int i=0; i<sx2.length; i++){
                DataPoint dp = new DataPoint(i, Double.parseDouble(sx2[i]));
                dataPoints.add(dp);
                series.appendData(dp, i > maxX ? true : false, maxX);
            }
        }

        Wrapper.log("state decode: " + toStringX());
    }

    protected void addDataPoint (DataPoint dp){
        dataPoints.add(dp);
        series.appendData(dp, lastX > maxX ? true : false, maxX);
        lastX++;
    }

    public void setMaxX(int maxX) {
        this.maxX = maxX;
    }
}
