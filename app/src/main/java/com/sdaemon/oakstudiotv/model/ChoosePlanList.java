package com.sdaemon.oakstudiotv.model;

import java.io.Serializable;

public class ChoosePlanList implements Serializable {
    private String planName;
    private double planAmount;
    private int hdAvailable;
    private int Ultra_hdAvailable;
    private int noScreens;
    private int watchOn_TV_Phone;
    private int UnLim_tv_movies;
    private int cancelAnyTime;
    private int firstFree;
    private double qraterPrice;
    private double anualPrice;


    public ChoosePlanList() {
    }

    public ChoosePlanList(String planName, double planAmount, int hdAvailable, int ultra_hdAvailable, int noScreens, int watchOn_TV_Phone, int unLim_tv_movies, int cancelAnyTime, int firstFree, double qraterPrice, double anualPrice) {
        this.planName = planName;
        this.planAmount = planAmount;
        this.hdAvailable = hdAvailable;
        Ultra_hdAvailable = ultra_hdAvailable;
        this.noScreens = noScreens;
        this.watchOn_TV_Phone = watchOn_TV_Phone;
        UnLim_tv_movies = unLim_tv_movies;
        this.cancelAnyTime = cancelAnyTime;
        this.firstFree = firstFree;
        this.qraterPrice = qraterPrice;
        this.anualPrice = anualPrice;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public double getPlanAmount() {
        return planAmount;
    }

    public void setPlanAmount(double planAmount) {
        this.planAmount = planAmount;
    }

    public int getHdAvailable() {
        return hdAvailable;
    }

    public void setHdAvailable(int hdAvailable) {
        this.hdAvailable = hdAvailable;
    }

    public int getUltra_hdAvailable() {
        return Ultra_hdAvailable;
    }

    public void setUltra_hdAvailable(int ultra_hdAvailable) {
        Ultra_hdAvailable = ultra_hdAvailable;
    }

    public int getNoScreens() {
        return noScreens;
    }

    public void setNoScreens(int noScreens) {
        this.noScreens = noScreens;
    }

    public int getWatchOn_TV_Phone() {
        return watchOn_TV_Phone;
    }

    public void setWatchOn_TV_Phone(int watchOn_TV_Phone) {
        this.watchOn_TV_Phone = watchOn_TV_Phone;
    }

    public int getUnLim_tv_movies() {
        return UnLim_tv_movies;
    }

    public void setUnLim_tv_movies(int unLim_tv_movies) {
        UnLim_tv_movies = unLim_tv_movies;
    }

    public int getCancelAnyTime() {
        return cancelAnyTime;
    }

    public void setCancelAnyTime(int cancelAnyTime) {
        this.cancelAnyTime = cancelAnyTime;
    }

    public int getFirstFree() {
        return firstFree;
    }

    public void setFirstFree(int firstFree) {
        this.firstFree = firstFree;
    }

    public double getQraterPrice() {
        return qraterPrice;
    }

    public void setQraterPrice(double qraterPrice) {
        this.qraterPrice = qraterPrice;
    }

    public double getAnualPrice() {
        return anualPrice;
    }

    public void setAnualPrice(double anualPrice) {
        this.anualPrice = anualPrice;
    }
}
