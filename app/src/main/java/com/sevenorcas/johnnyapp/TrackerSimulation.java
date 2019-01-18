package com.sevenorcas.johnnyapp;

import android.graphics.Color;

import java.util.Random;

public class TrackerSimulation {

    private double rn;
    private Random rand = new Random();

    /**
     * Generate random numbers<p>
     *
     * Thanks to https://stackoverflow.com/questions/5887709/getting-random-numbers-in-java
     * @return
     */
    public double getPoint (){
        rn = -1D;
        new Thread (){
            public void run(){
                try {sleep(1000);} catch (InterruptedException e) {}
                rn = Double.parseDouble("" + rand.nextInt(1));
            }
        }.start();
        while (rn == -1D){
            //do nothing
        }
        return rn;
    }


}
