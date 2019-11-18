package com.cooldoctors.cdeye.models;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by Ulhas on 2/23/16.
 */
public class Timezone implements Serializable {
    @Expose
    String id, timezone, displayName;

    private static Timezone instance = null;

    public static Timezone getInstance() {
        if (instance == null) {
            instance = new Timezone();
        }
        return instance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
