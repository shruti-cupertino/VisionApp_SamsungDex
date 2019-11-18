package com.cooldoctors.cdeye.models;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by shree on 3/23/16.
 */
public class Pharmacy implements Serializable {


    @Expose
    private String id;

    @Expose
    private String name;

    @Expose
    private String address;

    @Expose
    private String email;

    @Expose(serialize = false)
    private boolean isValidated;

    @Expose
    private String city;

    @Expose
    private String state;

    @Expose
    private String country;

    @Expose
    private String zipCode;

    @Expose
    private String phoneNumber;


    public Pharmacy() {
    }

    private static Pharmacy instance = null;

    public static Pharmacy getInstance() {
        if (instance == null) {
            instance = new Pharmacy();
        }
        return instance;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isValidated() {
        return isValidated;
    }

    public void setIsValidated(boolean isValidated) {
        this.isValidated = isValidated;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
