package com.sdaemon.oakstudiotv.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotificationDTO {

    @SerializedName("NotificationId")
    @Expose
    private Integer NotificationId;

    @SerializedName("CustId")
    @Expose
    private Integer CustId;

    @SerializedName("Title")
    @Expose
    private String Title;

    @SerializedName("Massage")
    @Expose
    private String Massage;

    @SerializedName("ReadStatus")
    @Expose
    private Boolean ReadStatus;

    @SerializedName("NotifyDate")
    @Expose
    private String NotifyDate;
    public Integer getNotificationId() {
        return NotificationId;
    }

    public Integer getCustId() {
        return CustId;
    }

    public void setCustId(Integer custId) {
        CustId = custId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getMassage() {
        return Massage;
    }

    public void setMassage(String massage) {
        Massage = massage;
    }

    public Boolean getReadStatus() {
        return ReadStatus;
    }

    public void setReadStatus(Boolean readStatus) {
        ReadStatus = readStatus;
    }

    public void setNotificationId(Integer notificationId) {
        NotificationId = notificationId;
    }

    public String getNotifyDate() {
        return NotifyDate;
    }

    public void setNotifyDate(String notifyDate) {
        NotifyDate = notifyDate;
    }
}
