package com.sdaemon.oakstudiotv.model;

import java.io.Serializable;

public class CommunicatonPref_list implements Serializable {

    String prefName;


    public CommunicatonPref_list() {
    }

    public CommunicatonPref_list(String prefName) {
        this.prefName = prefName;
    }

    public String getPrefName() {
        return prefName;
    }

    public void setPrefName(String prefName) {
        this.prefName = prefName;
    }
}
