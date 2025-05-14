package com.a3bradesco.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a3bradesco.api.entities.Report;
import com.a3bradesco.api.repositories.ReportRepository;

@Service
public class ReportService {
    
    @Autowired
    ReportRepository reportRepository;

    public List<Report> findAll() {
        return reportRepository.findAll();
    }

    public Report findById(Long id) {
        Optional<Report> reportObject = reportRepository.findById(id);
        return reportObject.get();
    }

    public Report insert(Report report){
        return reportRepository.save(report);
    }
}
