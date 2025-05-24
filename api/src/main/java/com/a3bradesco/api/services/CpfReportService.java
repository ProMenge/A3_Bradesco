package com.a3bradesco.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a3bradesco.api.entities.CpfReport;
import com.a3bradesco.api.repositories.CpfReportRepository;

@Service
public class CpfReportService {
    
    @Autowired
    CpfReportRepository cpfReportReporitory;

    public List<CpfReport> findAll() {
        return cpfReportReporitory.findAll();
    }

    public CpfReport findById(String cpf) {
        Optional<CpfReport> reportObject = cpfReportReporitory.findById(cpf);
        return reportObject.orElse(null);
    }

    public CpfReport insert(CpfReport report){
        return cpfReportReporitory.save(report);
    }
}
