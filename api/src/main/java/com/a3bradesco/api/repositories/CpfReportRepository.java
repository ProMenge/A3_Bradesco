package com.a3bradesco.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.a3bradesco.api.entities.CpfReport;

@Repository
public interface CpfReportRepository extends JpaRepository<CpfReport, String>{
    
}
