package com.a3bradesco.api.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.a3bradesco.api.entities.SiteReport;
import com.a3bradesco.api.repositories.SiteReportRepository;

@Service
public class SiteReportService extends AbstractReportService<SiteReport>{
    
    @Autowired
    SiteReportRepository siteReportReporitory;

    @Override
    protected JpaRepository<SiteReport, String> getRepository() {
        return siteReportReporitory;
    }

    @Override
    protected SiteReport createNewReport(String id){
        return new SiteReport(id, 1, LocalDate.now());
    }

}
