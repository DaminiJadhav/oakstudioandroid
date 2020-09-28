package com.sdaemon.oakstudiotv.dto;

import java.io.Serializable;

/**
 * Created by Sayem on 12/5/2017.
 */

public class PlaybackDTO implements Serializable {

    private String urlId ="";
    private String playBackPosition="";
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getUrlId() {
        return urlId;
    }

    public void setUrlId(String urlId) {
        this.urlId = urlId;
    }

    public String getPlayBackPosition() {
        return playBackPosition;
    }

    public void setPlayBackPosition(String playBackPosition) {
        this.playBackPosition = playBackPosition;
    }
}
