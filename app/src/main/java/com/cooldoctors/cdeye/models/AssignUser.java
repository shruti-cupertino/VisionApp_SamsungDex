package com.cooldoctors.cdeye.models;

/**
 * Created by shree on 3/17/16.
 */
public class AssignUser {

    User user;
    User familyMemberUser;
    User editFamilyMember;
    Appointment appointmentInstance;

    private static AssignUser userInstance = null;

    public static AssignUser getInstance() {
        if (userInstance == null) {
            userInstance = new AssignUser();
        }
        return userInstance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getFamilyMemberUser() {
        return familyMemberUser;
    }

    public void setFamilyMemberUser(User familyMemberUser) {
        this.familyMemberUser = familyMemberUser;
    }

    public User getEditFamilyMember() {
        return editFamilyMember;
    }

    public void setEditFamilyMember(User editFamilyMember) {
        this.editFamilyMember = editFamilyMember;
    }

    public Appointment getAppointmentInstance() {
        return appointmentInstance;
    }

    public void setAppointmentInstance(Appointment appointmentInstance) {
        this.appointmentInstance = appointmentInstance;
    }


}
