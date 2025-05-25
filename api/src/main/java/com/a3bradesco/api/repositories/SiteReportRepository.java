package com.a3bradesco.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.a3bradesco.api.entities.SiteReport;

@Repository
public interface SiteReportRepository extends JpaRepository<SiteReport, String>{
    
}
