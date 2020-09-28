package com.sdaemon.oakstudiotv.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Datum implements Parcelable {

    @SerializedName("DescriptionList")
    @Expose
    private List<DescriptionList> descriptionLists;
    @SerializedName("Periodlist")
    @Expose
    private List<Periodlist> periodlists;
    @SerializedName("corePlanid")
    @Expose
    private String corePlanid;
    @SerializedName("corePlanName")
    @Expose
    private String corePlanName;

    protected Datum(Parcel in) {
        periodlists = in.createTypedArrayList(Periodlist.CREATOR);
        corePlanid = in.readString();
        corePlanName = in.readString();
        expanded = in.readByte() != 0;
    }

    public static final Creator<Datum> CREATOR = new Creator<Datum>() {
        @Override
        public Datum createFromParcel(Parcel in) {
            return new Datum(in);
        }

        @Override
        public Datum[] newArray(int size) {
            return new Datum[size];
        }
    };

    public List<DescriptionList> getDescriptionLists() {
        return descriptionLists;
    }

    public void setDescriptionLists(List<DescriptionList> descriptionLists) {
        this.descriptionLists = descriptionLists;
    }

    public List<Periodlist> getPeriodlists() {
        return periodlists;
    }

    public void setPeriodlists(List<Periodlist> periodlists) {
        this.periodlists = periodlists;
    }

    public String getCorePlanid() {
        return corePlanid;
    }

    public void setCorePlanid(String corePlanid) {
        this.corePlanid = corePlanid;
    }

    public String getCorePlanName() {
        return corePlanName;
    }

    public void setCorePlanName(String corePlanName) {
        this.corePlanName = corePlanName;
    }
    private boolean expanded;
    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(periodlists);
        dest.writeString(corePlanid);
        dest.writeString(corePlanName);
        dest.writeByte((byte) (expanded ? 1 : 0));
    }
}
