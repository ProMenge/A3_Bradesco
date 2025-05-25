package com.a3bradesco.api.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.a3bradesco.api.entities.CellphoneReport;
import com.a3bradesco.api.repositories.CellphoneReportRepository;

@Service
public class CellphoneReportService extends AbstractReportService<CellphoneReport>{
    
    @Autowired
    CellphoneReportRepository cellphoneReportReporitory;

    @Override
    protected JpaRepository<CellphoneReport, String> getRepository() {
        return cellphoneReportReporitory;
    }

    @Override
    protected CellphoneReport createNewReport(String id){
        return new CellphoneReport(id, 1, LocalDate.now());
    }

}
