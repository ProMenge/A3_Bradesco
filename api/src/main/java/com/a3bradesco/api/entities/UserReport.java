package com.a3bradesco.api.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.a3bradesco.api.entities.enums.ReportType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_user_report")
public class UserReport implements Serializable {
	private static final long serialVersionUID = 1L;

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User reporter;

    @Enumerated(EnumType.STRING)
    private ReportType reportType;
    
    private String reportValue;
    private LocalDateTime reportMoment;
    

    public UserReport(){}
    
    public UserReport(Long id, User reporter, ReportType type, String reportValue, LocalDateTime reportMoment) {
        this.id = id;
        this.reporter = reporter;
        this.reportType = type;
        this.reportValue = reportValue;
        this.reportMoment = reportMoment;
    }    

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public User getReporter() {
        return reporter;
    }
    public void setReporter(User reporter) {
        this.reporter = reporter;
    }
    public LocalDateTime getReportMoment() {
        return reportMoment.truncatedTo(ChronoUnit.SECONDS);
    }
    public void setReportMoment(LocalDateTime reportMoment) {
        this.reportMoment = reportMoment;
    }
    public ReportType getReportType() {
        return reportType;
    }
    public void setReportType(ReportType reportType) {
        this.reportType = reportType;
    }
    public String getReportValue() {
        return reportValue;
    }
    public void setReportValue(String reportValue) {
        this.reportValue = reportValue;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserReport other = (UserReport) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    
}
