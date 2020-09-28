package com.sdaemon.oakstudiotv.model;

import java.io.Serializable;

public class ActorList implements Serializable {

    public String actorName;
    public String actorDesc;
    public int actorPic;



    public ActorList() {
    }

    public ActorList(int Image, String actorName, String actorDesc) {
        this.actorName = actorName;
        this.actorDesc = actorDesc;
        this.actorPic = Image;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public String getActorDesc() {
        return actorDesc;
    }

    public void setActorDesc(String actorDesc) {
        this.actorDesc = actorDesc;
    }

    public int getActorPic() {
        return actorPic;
    }

    public void setActorPic(int actorPic) {
        this.actorPic = actorPic;
    }
}
