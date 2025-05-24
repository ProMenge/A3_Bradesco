package com.a3bradesco.api.entities;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_cnpj_reports")
public class CnpjReport extends AbstractReport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String cnpj;

    public CnpjReport(){
    }

    public CnpjReport(String cnpj, int reportQuantity, LocalDate lastTimeReported){
        this.cnpj = cnpj;
        setReportQuantity(reportQuantity);
        setLastTimeReported(lastTimeReported);
    }

    public String getCnpj() {
        return cnpj;
    }
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cnpj == null) ? 0 : cnpj.hashCode());
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
        CnpjReport other = (CnpjReport) obj;
        if (cnpj == null) {
            if (other.cnpj != null)
                return false;
        } else if (!cnpj.equals(other.cnpj))
            return false;
        return true;
    }
    
    
}
