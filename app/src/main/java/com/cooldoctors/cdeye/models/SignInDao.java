package com.cooldoctors.cdeye.models;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by Ulhas on 2/23/16.
 */
public class SignInDao implements Serializable {

    @Expose
    String email;

    @Expose
    String password;

    @Expose
    String ssnId;

    @Expose
    String token;

    @Expose
    ContactInfo contactInfo;

    @Expose
    Role role;

    @Expose
    Timezone timezone;

    @Expose
    AboutMe aboutMe;

    private static SignInDao instance = null;

    public static SignInDao getInstance() {
        if (instance == null) {
            instance = new SignInDao();
        }
        return instance;
    }

    public AboutMe getAboutMeDao() {
        return aboutMe;
    }

    public void setAboutMeDao(AboutMe aboutMeDao) {
        this.aboutMe = aboutMeDao;
    }

    public Role getRoleDao() {
        return role;
    }

    public void setRoleDao(Role role) {
        this.role = role;
    }

    public Timezone getTimeZone() {
        return timezone;
    }

    public void setTimeZone(Timezone timezone) {
        this.timezone = timezone;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSsnId() {
        return ssnId;
    }

    public void setSsnId(String ssnId) {
        this.ssnId = ssnId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
