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
    protected double minY;
    protected double maxY;


    protected State(Config config) {
        this.config = config;
        maxX = 30; //Default (Portrait)
        series = new LineGraphSeries<DataPoint>();
        dataPoints = new ArrayList<>();
        lastX = 0;
        minY = -1;
        maxY = -1;
    }


    /**
     * Encode <b>this</b> object result parameters to a <code>String</code>
     * @return
     */
    public String encodeResult(){
        StringBuffer sb = new StringBuffer();

        encodeField(STATE_MAX, maxX, sb);
        encodeField(STATE_LAST, lastX, sb);
        encodeField(STATE_MIN_Y, minY, sb);
        encodeField(STATE_MAX_Y, maxY, sb);

        return sb.toString();
    }

    /**
     * Encode <b>this</b> object data to a <code>String</code>
     * @return
     */
    public String encodeData(){
        StringBuffer sbx = new StringBuffer();
        for (DataPoint d : dataPoints){
            encodeList(d.getY(), sbx);
        }

        StringBuffer sb = new StringBuffer();
        encodeField(STATE_DATA_POINTS, sbx.toString(), sb);

        return sb.toString();
    }


    private String toStringX(){
        StringBuffer sb = new StringBuffer();
        sb.append("max=" + maxX);
        sb.append(", last=" + lastX);
        sb.append(", minY=" + minY);
        sb.append(", maxY=" + maxY);
        sb.append(", dp=");
        for (int i=0;i<dataPoints.size();i++){
            DataPoint d = dataPoints.get(i);
            sb.append((i!=0? "|" : "") + d.getY());
        }
        return sb.toString();
    }


    protected void decodeResult(String s){
        if (s == null || s.isEmpty()){
            return;
        }

        try {
            super.decode(s);
            lastX     = getField(STATE_LAST, lastX);
            minY      = getField(STATE_MIN_Y, minY);
            maxY      = getField(STATE_MAX_Y, maxY);
        } catch (Exception x){
            GraphWrapper.log("Exception: " + x.getMessage());
        }
    }

    protected void decodeData(String s){
        if (s == null || s.isEmpty()){
            return;
        }

        String dps = null;
        try {
            super.decode(s);
            dps       = getField(STATE_DATA_POINTS, "");
        } catch (Exception x){
            GraphWrapper.log("Exception: " + x.getMessage());
        }

        //add data points last
        if (dps != null && dps.length()>0) {
            String[] sx2 = dps.split("\\" + DELIMIT_3);
            for (int i=0; i<sx2.length; i++){
                DataPoint dp = new DataPoint(i, Double.parseDouble(sx2[i]));
                dataPoints.add(dp);
            }
        }
    }


    public void appendAllDataPoints(){
        for (int i=0; i<dataPoints.size(); i++){
            DataPoint dp = dataPoints.get(i);
            series.appendData(dp, i > maxX, maxX);
        }
    }

    public LineGraphSeries<DataPoint> addAllDataPoints(){
        DataPoint[] points = new DataPoint[dataPoints.size()];
        for (int i=0; i<dataPoints.size(); i++){
            points[i] = dataPoints.get(i);
        }
        series = new LineGraphSeries<>(points);
        return series;
    }

    protected void addDataPoint (DataPoint dp){
        dataPoints.add(dp);
        series.appendData(dp, lastX > maxX, maxX);
        lastX++;

        if (minY == -1 || dp.getY() < minY){
            minY = dp.getY();
        }
        if (maxY == -1 || dp.getY() > maxY){
            maxY = dp.getY();
        }
    }

    public State setMaxX(int maxX) {
        this.maxX = maxX;
        return this;
    }
    public int getMaxX() { return maxX; }

    public int getLastX() { return lastX; }

    public double getMinY() {
        return minY;
    }

    public void setMinY(double minY) {
        this.minY = minY;
    }

    public double getMaxY() {
        return maxY;
    }

    public void setMaxY(double maxY) {
        this.maxY = maxY;
    }

    protected LineGraphSeries<DataPoint> getSeries() {
        return series;
    }

    public int getDuration(){
        return lastX * config.delayFactor;
    }

}
