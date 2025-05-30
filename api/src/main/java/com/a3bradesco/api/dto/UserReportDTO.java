package com.a3bradesco.api.dto;

import com.a3bradesco.api.entities.enums.ReportType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserReportDTO {

    @NotNull
    private ReportType reportType;
    @NotBlank
    String reportValue;

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

