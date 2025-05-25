package com.a3bradesco.api.entities;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_site_reports")
public class SiteReport extends AbstractReport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String site;

    public SiteReport(){
    }

    public SiteReport(String site, int reportQuantity, LocalDate lastTimeReported){
        this.site = site;
        setReportQuantity(reportQuantity);
        setLastTimeReported(lastTimeReported);
    }


    public String getSite() {
        return site;
    }
    public void setSite(String site) {
        this.site = site;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((site == null) ? 0 : site.hashCode());
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
        SiteReport other = (SiteReport) obj;
        if (site == null) {
            if (other.site != null)
                return false;
        } else if (!site.equals(other.site))
            return false;
        return true;
    }
    
    
}
