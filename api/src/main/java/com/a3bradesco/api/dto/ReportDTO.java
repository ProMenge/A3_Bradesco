package com.a3bradesco.api.dto;

public class ReportDTO {

    private Long reporterId;
    private int reportTypeCode;
    String reportValue;

    // Getters e Setters
    public Long getReporterId() {
        return reporterId;
    }

    public void setReporterId(Long reporterId) {
        this.reporterId = reporterId;
    }

    public int getReportType() {
        return reportTypeCode;
    }

    public void setReportType(int reportTypeCode) {
        this.reportTypeCode = reportTypeCode;
    }

    public String getReportValue() {
        return reportValue;
    }

    public void setReportValue(String reportValue) {
        this.reportValue = reportValue;
    }

}

