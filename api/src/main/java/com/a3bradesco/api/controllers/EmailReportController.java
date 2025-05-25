package com.a3bradesco.api.controllers;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.a3bradesco.api.dto.EmailDTO;
import com.a3bradesco.api.entities.EmailReport;
import com.a3bradesco.api.services.EmailReportService;


@RestController
@RequestMapping("/email-reports")
public class EmailReportController {
    
    @Autowired
    EmailReportService emailReportService;

    @GetMapping
    public ResponseEntity<List<EmailReport>> findAll() {
        List<EmailReport> emailReportList = emailReportService.findAll();
        return ResponseEntity.ok().body(emailReportList);
    }

    @GetMapping("/{email}")
    public ResponseEntity<EmailReport> findById(@PathVariable String email){
        EmailReport report = emailReportService.findById(email);
        return ResponseEntity.ok().body(report);
    }

    @PostMapping()
    public ResponseEntity<EmailReport> saveNewReport(@RequestBody EmailDTO dto) {

        EmailReport emailInDatabase = emailReportService.findById(dto.getEmail());
        EmailReport report;
        
        if(emailInDatabase == null){
            report = new EmailReport(dto.getEmail(), 1, LocalDate.now());
        } else{
            report = new EmailReport(emailInDatabase.getEmail(), emailInDatabase.getReportQuantity() + 1, LocalDate.now());
        }

        EmailReport saved = emailReportService.insert(report);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                  .path("/{id}").buildAndExpand(saved.getEmail()).toUri();

        return ResponseEntity.created(uri).body(saved);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<String> deleteReport(@PathVariable String email){
        emailReportService.deleteById(email);
        EmailReport isDeleted = emailReportService.findById(email);
        if(isDeleted == null){
            return ResponseEntity.ok("Denúncia retirada com sucesso!");
        } else {
            return ResponseEntity.badRequest().build();
        }
        
    }
    
}
