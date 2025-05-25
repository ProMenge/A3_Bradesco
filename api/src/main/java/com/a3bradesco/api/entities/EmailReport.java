package com.a3bradesco.api.entities;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_email_reports")
public class EmailReport extends AbstractReport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String email;

    public EmailReport(){
    }

    public EmailReport(String email, int reportQuantity, LocalDate lastTimeReported){
        this.email = email;
        setReportQuantity(reportQuantity);
        setLastTimeReported(lastTimeReported);
    }


    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((email == null) ? 0 : email.hashCode());
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
        EmailReport other = (EmailReport) obj;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        return true;
    }
    
    
}
