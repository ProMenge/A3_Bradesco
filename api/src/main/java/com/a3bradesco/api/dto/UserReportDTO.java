package com.a3bradesco.api.dto;

import com.a3bradesco.api.entities.enums.ReportType;

public class UserReportDTO {

    private Long reporterId;
    private ReportType reportType;
    String reportValue;

    // Getters e Setters
    public Long getReporterId() {
        return reporterId;
    }

    public void setReporterId(Long reporterId) {
        this.reporterId = reporterId;
    }

    public String getReportValue() {
        return reportValue;
    }

    public void setReportValue(String reportValue) {
        this.reportValue = reportValue;
    }

    public ReportType getReportType() {
        return reportType;
    }

    public void setReportType(ReportType reportType) {
        this.reportType = reportType;
    }

}

