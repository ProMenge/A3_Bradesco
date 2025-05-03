package com.a3bradesco.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.a3bradesco.api.entities.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long>{
    
}
