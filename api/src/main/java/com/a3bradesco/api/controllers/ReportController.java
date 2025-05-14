package com.a3bradesco.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.a3bradesco.api.entities.Report;
import com.a3bradesco.api.services.ReportService;

@RestController
@RequestMapping("/reports")
public class ReportController {
    
    @Autowired
    ReportService reportService;

    @GetMapping
    public ResponseEntity<List<Report>> findAll() {
        List<Report> reportList = reportService.findAll();
        return ResponseEntity.ok().body(reportList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Report> findById(@PathVariable Long id){
        Report report = reportService.findById(id);
        return ResponseEntity.ok().body(report);
    }
}
