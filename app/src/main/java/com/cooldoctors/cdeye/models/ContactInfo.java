package com.cooldoctors.cdeye.models;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by Ulhas on 2/23/16.
 */
public class ContactInfo implements Serializable {


    @Expose
    String email, phone;


    public ContactInfo() {
    }

    private static ContactInfo instance = null;

    public static ContactInfo getInstance() {
        if (instance == null) {
            instance = new ContactInfo();
        }
        return instance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
