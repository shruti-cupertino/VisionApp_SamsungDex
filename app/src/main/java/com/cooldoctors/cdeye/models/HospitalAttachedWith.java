package com.cooldoctors.cdeye.models;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by Ulhas on 2/26/16.
 */
public class HospitalAttachedWith implements Serializable {

    @Expose
    String name, city, country;

    private static HospitalAttachedWith instance = null;

    public static HospitalAttachedWith getInstance() {
        if (instance == null) {
            instance = new HospitalAttachedWith();
        }
        return instance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
