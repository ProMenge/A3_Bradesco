package com.a3bradesco.api.entities;

import java.time.LocalDate;

import jakarta.persistence.MappedSuperclass;

//Classe da qual todos os tipos de report (CPF, CNPJ, Email, Celular e Site) vão
//herdar seus métodos padrões
@MappedSuperclass
public abstract class AbstractReport {
    private int reportQuantity;
    private LocalDate lastTimeReported;
    
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

}
