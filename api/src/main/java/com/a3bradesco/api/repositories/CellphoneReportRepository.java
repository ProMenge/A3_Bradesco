package com.a3bradesco.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.a3bradesco.api.entities.CellphoneReport;

@Repository
public interface CellphoneReportRepository extends JpaRepository<CellphoneReport, String>{
    
}
