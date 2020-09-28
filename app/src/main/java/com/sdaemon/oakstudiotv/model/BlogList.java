package com.sdaemon.oakstudiotv.model;

public class BlogList {

    public String name;
    public int image;
    public String desc;
    public String date;

    public BlogList(String name,int image,String desc,String date) {
        this.name = name;
        this.image=image;
        this.desc = desc;
        this.date=date;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
