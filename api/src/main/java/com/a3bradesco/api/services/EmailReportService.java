package com.a3bradesco.api.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.a3bradesco.api.entities.EmailReport;
import com.a3bradesco.api.repositories.EmailReportRepository;

@Service
public class EmailReportService extends AbstractReportService<EmailReport>{
    
    @Autowired
    EmailReportRepository emailReportReporitory;

    @Override
    protected JpaRepository<EmailReport, String> getRepository() {
        return emailReportReporitory;
    }

    @Override
    protected EmailReport createNewReport(String id){
        return new EmailReport(id, 1, LocalDate.now());
    }

}
