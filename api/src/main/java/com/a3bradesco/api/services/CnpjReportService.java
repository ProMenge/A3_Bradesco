package com.a3bradesco.api.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.a3bradesco.api.entities.CnpjReport;
import com.a3bradesco.api.repositories.CnpjReportRepository;

@Service
public class CnpjReportService extends AbstractReportService<CnpjReport>{
    
    @Autowired
    CnpjReportRepository cnpjReportReporitory;

    @Override
    protected JpaRepository<CnpjReport, String> getRepository() {
        return cnpjReportReporitory;
    }

    @Override
    protected CnpjReport createNewReport(String id){
        return new CnpjReport(id, 1, LocalDate.now());
    }

}
