package com.sdaemon.oakstudiotv.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Periodlist implements Parcelable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("periodid")
    @Expose
    private String periodid;


    protected Periodlist(Parcel in) {
        name = in.readString();
        amount = in.readString();
        periodid = in.readString();
    }

    public static final Creator<Periodlist> CREATOR = new Creator<Periodlist>() {
        @Override
        public Periodlist createFromParcel(Parcel in) {
            return new Periodlist(in);
        }

        @Override
        public Periodlist[] newArray(int size) {
            return new Periodlist[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPeriodid() {
        return periodid;
    }

    public void setPeriodid(String periodid) {
        this.periodid = periodid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(amount);
        dest.writeString(periodid);
    }
}
