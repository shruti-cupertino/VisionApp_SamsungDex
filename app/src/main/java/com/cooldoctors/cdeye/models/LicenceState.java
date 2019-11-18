package com.cooldoctors.cdeye.models;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by Ulhas on 2/26/16.
 */
public class LicenceState implements Serializable {
    @Expose
    String id, state;

    private static LicenceState instance = null;

    public static LicenceState getInstance() {
        if (instance == null) {
            instance = new LicenceState();
        }
        return instance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
