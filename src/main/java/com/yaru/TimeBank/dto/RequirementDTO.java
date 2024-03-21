package com.yaru.TimeBank.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class RequirementDTO {
    private Long requestId;
    private String serviceName;
    private String serviceContent;
    private String elderName;
    private String elderAddress;
    private String elderPhone;


    private String elderChildrenPhone;
    private String elderPhysical;
    private String status;
    private String volunteerName;

    private String volunteerAddress;
    private String volunteerPhone;

    public String getVolunteerPhysical() {
        return volunteerPhysical;
    }

    public void setVolunteerPhysical(String volunteerPhysical) {
        this.volunteerPhysical = volunteerPhysical;
    }

    private String volunteerPhysical;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createTime;

    private LocalDate lastTime;
    private Long durationHours;


    public String getElderAddress() {
        return elderAddress;
    }

    public void setElderAddress(String elderAddress) {
        this.elderAddress = elderAddress;
    }

    public String getElderPhone() {
        return elderPhone;
    }

    public void setElderPhone(String elderPhone) {
        this.elderPhone = elderPhone;
    }

    public String getElderChildrenPhone() {
        return elderChildrenPhone;
    }

    public void setElderChildrenPhone(String elderChildrenPhone) {
        this.elderChildrenPhone = elderChildrenPhone;
    }

    public String getElderPhysical() {
        return elderPhysical;
    }

    public void setElderPhysical(String elderPhysical) {
        this.elderPhysical = elderPhysical;
    }

    public String getVolunteerAddress() {
        return volunteerAddress;
    }

    public void setVolunteerAddress(String volunteerAddress) {
        this.volunteerAddress = volunteerAddress;
    }

    public String getVolunteerPhone() {
        return volunteerPhone;
    }

    public void setVolunteerPhone(String volunteerPhone) {
        this.volunteerPhone = volunteerPhone;
    }

    public String getVolunteerName() {
        return volunteerName;
    }

    public void setVolunteerName(String volunteerName) {
        this.volunteerName = volunteerName;
    }







    public LocalDate getLastTime() {
        return lastTime;
    }

    public void setLastTime(LocalDate lastTime) {
        this.lastTime = lastTime;
    }


    // Getter 和 Setter 方法
    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceContent() {
        return serviceContent;
    }

    public void setServiceContent(String serviceContent) {
        this.serviceContent = serviceContent;
    }

    public String getElderName() {
        return elderName;
    }

    public void setElderName(String elderName) {
        this.elderName = elderName;
    }

    public LocalDate getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDate createTime) {
        this.createTime = createTime;
    }

    public Long getDurationHours() {
        return durationHours;
    }

    public void setDurationHours(Long durationHours) {
        this.durationHours = durationHours;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

