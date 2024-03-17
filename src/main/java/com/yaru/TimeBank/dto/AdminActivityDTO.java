package com.yaru.TimeBank.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;


public class AdminActivityDTO {

    private Long activityId;
    private String activityName;
    private String activityContent;
    private String volunteerName;
    private String address;
    private String activityStatus;

    private Long volunteerHours;

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityContent() {
        return activityContent;
    }

    public void setActivityContent(String activityContent) {
        this.activityContent = activityContent;
    }

    public String getVolunteerName() {
        return volunteerName;
    }

    public void setVolunteerName(String volunteerName) {
        this.volunteerName = volunteerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(String activityStatus) {
        this.activityStatus = activityStatus;
    }

    public Long getVolunteerHours() {
        return volunteerHours;
    }

    public void setVolunteerHours(Long volunteerHours) {
        this.volunteerHours = volunteerHours;
    }
}
