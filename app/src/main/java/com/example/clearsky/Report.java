package com.example.clearsky;

import java.util.ArrayList;

/**
 * Created by ofek13 on 07/06/2017.
 */

public class Report {

    private String birdKind;
    private String birdPlace;
    private String birdAzimuth;
    private String date;
    private int birdCount;

    public Report() {}

    public Report(String birdKind, String birdPlace, String birdAzimuth, String date, int birdCount) {
        this.birdKind = birdKind;
        this.birdPlace = birdPlace;
        this.birdAzimuth = birdAzimuth;
        this.date = date;
        this.birdCount = birdCount;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getBirdCount() {
        return birdCount;
    }

    public void setBirdCount(int birdCount) {
        this.birdCount = birdCount;
    }

    public void addToList(ArrayList<String> lst) {
        lst.add(this.birdKind);
        lst.add(this.birdPlace);
        lst.add(this.birdAzimuth);
        lst.add(this.date);
        lst.add(String.valueOf(this.birdCount));
    }
}