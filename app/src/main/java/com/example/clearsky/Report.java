package com.example.clearsky;

import android.util.Log;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by ofek13 on 07/06/2017.
 */

public class Report {

    private String birdKind;
    private String birdPlace;
    private String birdAzimuth;
    private Long date;
    private String birdCount;
    private String reporter;
    private String height;

    public Report() {}

    public Report(String birdKind, String birdPlace, String birdAzimuth, Long date, String birdCount,String reporter,String height) {
        this.birdKind = birdKind;
        this.birdPlace = birdPlace;
        this.birdAzimuth = birdAzimuth;
        this.date = date;
        this.birdCount = birdCount;
        this.reporter = reporter;
        this.height = height;
    }

    public String getBirdKind() {
        return birdKind;
    }

    public void setBirdKind(String birdKind) {
        this.birdKind = birdKind;
    }

    public String getBirdPlace() {
        return birdPlace;
    }

    public void setBirdPlace(String birdPlace) {
        this.birdPlace = birdPlace;
    }

    public String getBirdAzimuth() {
        return birdAzimuth;
    }

    public void setBirdAzimuth(String birdAzimuth) {
        this.birdAzimuth = birdAzimuth;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getBirdCount() {
        return birdCount;
    }

    public void setBirdCount(String birdCount) {
        this.birdCount = birdCount;
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void addToList(ArrayList<String> lst) {
        lst.add(this.birdKind);
        lst.add(this.birdPlace);
        lst.add(this.birdAzimuth);
        lst.add(Long.toString(this.date));
        lst.add(this.birdCount);
        lst.add(this.reporter);
        lst.add(this.height);
    }


}