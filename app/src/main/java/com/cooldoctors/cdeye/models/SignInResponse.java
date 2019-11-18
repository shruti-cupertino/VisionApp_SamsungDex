package com.cooldoctors.cdeye.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignInResponse {

    @Expose
    @SerializedName("tearmsFlagForDoctor")
    private boolean tearmsFlagForDoctor;
    @Expose
    @SerializedName("lastActiveTime")
    private String lastActiveTime;
    @Expose
    @SerializedName("dateTime")
    private String dateTime;
    @Expose
    @SerializedName("token")
    private String token;
    @Expose
    @SerializedName("userId")
    private String userId;
    @Expose
    @SerializedName("id")
    private String id;
    @Expose
    @SerializedName("url")
    private String url;
    @Expose
    @SerializedName("message")
    private String message;

    public boolean getTearmsFlagForDoctor() {
        return tearmsFlagForDoctor;
    }

    public void setTearmsFlagForDoctor(boolean tearmsFlagForDoctor) {
        this.tearmsFlagForDoctor = tearmsFlagForDoctor;
    }

    public String getLastActiveTime() {
        return lastActiveTime;
    }

    public void setLastActiveTime(String lastActiveTime) {
        this.lastActiveTime = lastActiveTime;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
