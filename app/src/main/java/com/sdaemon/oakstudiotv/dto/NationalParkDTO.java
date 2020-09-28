package com.sdaemon.oakstudiotv.dto;

/**
 * Created by lenovo-aio on 2/6/17.
 */

public class NationalParkDTO {

    String title;
    String link;

    String titleHindi, linkHindi;
    String titleEn, linkEn;
    String messageEN = "",messageHI = "",type="";

    String lat = "",lang = "",address = "";


    private int itemViewType = 0;

    public NationalParkDTO(int itemViewType) {
        this.itemViewType = itemViewType;
    }

    public NationalParkDTO() {
    }

    public int getItemViewType() {
        return itemViewType;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitleHindi() {
        return titleHindi;
    }

    public void setTitleHindi(String titleHindi) {
        this.titleHindi = titleHindi;
    }

    public String getLinkHindi() {
        return linkHindi;
    }

    public void setLinkHindi(String linkHindi) {
        this.linkHindi = linkHindi;
    }

    public String getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }

    public String getLinkEn() {
        return linkEn;
    }

    public void setLinkEn(String linkEn) {
        this.linkEn = linkEn;
    }

    public String getMessageEN() {
        return messageEN;
    }

    public void setMessageEN(String messageEN) {
        this.messageEN = messageEN;
    }

    public String getMessageHI() {
        return messageHI;
    }

    public void setMessageHI(String messageHI) {
        this.messageHI = messageHI;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
