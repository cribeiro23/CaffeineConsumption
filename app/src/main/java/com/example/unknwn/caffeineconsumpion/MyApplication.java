package com.example.unknwn.caffeineconsumpion;

import android.app.Application;

/**
 * Created by UNKNWN on 10/21/2017.
 */

public class MyApplication extends Application {

    private int consumedCaff = 0;
    private int userWeight = 50;
    private int mgCaffeine = userWeight * 6;
    private double caffeineMeta = 1;

    public int getConsumedCaff() {
        return consumedCaff;
    }

    public void setConsumedCaff(int caffeine) {
        consumedCaff = caffeine;
    }

    public void addConsumedCaff(int caffeine) {
        consumedCaff = consumedCaff + caffeine;
    }

    public int getUserWeight() {
        return userWeight;
    }

    public void setUserWeight(int weight) {
        userWeight = weight;
    }

    public int getmgCaffeine() {
        return mgCaffeine;
    }

    public void setmgCaffeine(int phenotype) {
        switch (phenotype) {
            case 0: mgCaffeine = (int) (mgCaffeine * .75);
                break;
            case 1: mgCaffeine = (int)(mgCaffeine * .875);
                break;
            case 2: mgCaffeine = (int) (mgCaffeine);
                break;
            case 3: mgCaffeine = (int) (mgCaffeine * 1.25);
                break;
            case 4: mgCaffeine = (int) (mgCaffeine * 1.5);
                break;
            default: mgCaffeine = 400;
        }
    }

    public double getCaffeineMeta() {
        return caffeineMeta;
    }

    public void setCaffeineMeta(int phenotype) {
        switch (phenotype) {
            case 0: caffeineMeta = .6;
                break;
            case 1: caffeineMeta = .8;
                break;
            case 2: caffeineMeta = 1;
                break;
            case 3: caffeineMeta = 1.25;
                break;
            case 4: caffeineMeta = 1.5;
                break;
            default: caffeineMeta = 1;
        }
    }
}
