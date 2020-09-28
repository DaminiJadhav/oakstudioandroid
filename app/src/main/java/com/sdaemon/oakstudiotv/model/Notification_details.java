package com.sdaemon.oakstudiotv.model;

import java.io.Serializable;

public class Notification_details implements Serializable {
    public String heading;
    public String desc;


    public Notification_details() {
    }

    public Notification_details(String heading, String desc) {
        this.heading = heading;
        this.desc = desc;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
