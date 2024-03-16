package com.yaru.TimeBank.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class AdminRequirementDTO {
    private Long requestId;
    private String serviceName;
    private String serviceContent;
    private String elderName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createTime;
    private Long durationHours;
    private String status;

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

