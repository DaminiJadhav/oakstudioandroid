package com.sdaemon.oakstudiotv.model;

import java.io.Serializable;

public class CreatorsList implements Serializable {

    public String creatorName;
    public String creatorDesc;
    public int creatorPic;



    public CreatorsList() {
    }

    public CreatorsList(int Image,String creatorName, String creatorDesc) {
        this.creatorName = creatorName;
        this.creatorDesc = creatorDesc;
        this.creatorPic = Image;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getCreatorDesc() {
        return creatorDesc;
    }

    public void setCreatorDesc(String creatorDesc) {
        this.creatorDesc = creatorDesc;
    }

    public int getCreatorPic() {
        return creatorPic;
    }

    public void setCreatorPic(int creatorPic) {
        this.creatorPic = creatorPic;
    }
}
