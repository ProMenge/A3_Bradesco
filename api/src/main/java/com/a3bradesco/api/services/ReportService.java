package com.a3bradesco.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a3bradesco.api.entities.UserReport;
import com.a3bradesco.api.repositories.ReportRepository;

@Service
public class ReportService {
    
    @Autowired
    ReportRepository reportRepository;

    public List<UserReport> findAll() {
        return reportRepository.findAll();
    }

    public UserReport findById(Long id) {
        Optional<UserReport> reportObject = reportRepository.findById(id);
        return reportObject.get();
    }

    public UserReport insert(UserReport report){
        return reportRepository.save(report);
    }
}
