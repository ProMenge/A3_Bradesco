package com.a3bradesco.api.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.a3bradesco.api.entities.CpfReport;
import com.a3bradesco.api.repositories.CpfReportRepository;

@Service
public class CpfReportService extends AbstractReportService<CpfReport>{
    
    @Autowired
    CpfReportRepository cpfReportReporitory;

    @Override
    protected JpaRepository<CpfReport, String> getRepository() {
        return cpfReportReporitory;
    }

    @Override
    protected CpfReport createNewReport(String id){
        return new CpfReport(id, 1, LocalDate.now());
    }

}
