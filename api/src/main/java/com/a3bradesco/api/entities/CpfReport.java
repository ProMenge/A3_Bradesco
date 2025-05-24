package com.a3bradesco.api.entities;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_cpf_reports")
public class CpfReport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String cpf;
    private int reportQuantity;
    private LocalDate lastTimeReported;

    public CpfReport(){
    }

    public CpfReport(String cpf, int reportQuantity, LocalDate lastTimeReported){
        this.cpf = cpf;
        this.reportQuantity = reportQuantity;
        this.lastTimeReported = lastTimeReported;
    }

    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public int getReportQuantity() {
        return reportQuantity;
    }
    public void setReportQuantity(int reportQuantity) {
        this.reportQuantity = reportQuantity;
    }
    public LocalDate getLastTimeReported() {
        return lastTimeReported;
    }
    public void setLastTimeReported(LocalDate lastTimeReported) {
        this.lastTimeReported = lastTimeReported;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
        result = prime * result + reportQuantity;
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
        CpfReport other = (CpfReport) obj;
        if (cpf == null) {
            if (other.cpf != null)
                return false;
        } else if (!cpf.equals(other.cpf))
            return false;
        if (reportQuantity != other.reportQuantity)
            return false;
        return true;
    }

    
}
