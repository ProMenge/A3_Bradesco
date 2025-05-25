package com.a3bradesco.api.entities;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_cellphone_reports")
public class CellphoneReport extends AbstractReport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String cellphone;

    public CellphoneReport(){
    }

    public CellphoneReport(String cellphone, int reportQuantity, LocalDate lastTimeReported){
        this.cellphone = cellphone;
        setReportQuantity(reportQuantity);
        setLastTimeReported(lastTimeReported);
    }

    public String getCellphone() {
        return cellphone;
    }
    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cellphone == null) ? 0 : cellphone.hashCode());
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
        CellphoneReport other = (CellphoneReport) obj;
        if (cellphone == null) {
            if (other.cellphone != null)
                return false;
        } else if (!cellphone.equals(other.cellphone))
            return false;
        return true;
    }
    
    
}
