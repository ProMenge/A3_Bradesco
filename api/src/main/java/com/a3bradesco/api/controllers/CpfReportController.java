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

import com.a3bradesco.api.dto.CpfDTO;
import com.a3bradesco.api.entities.CpfReport;
import com.a3bradesco.api.services.CpfReportService;


@RestController
@RequestMapping("/cpf-reports")
public class CpfReportController {
    
    @Autowired
    CpfReportService cpfReportService;

    @GetMapping
    public ResponseEntity<List<CpfReport>> findAll() {
        List<CpfReport> cpfReportList = cpfReportService.findAll();
        return ResponseEntity.ok().body(cpfReportList);
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<CpfReport> findById(@PathVariable String cpf){
        CpfReport report = cpfReportService.findById(cpf);
        return ResponseEntity.ok().body(report);
    }

    @PostMapping()
    public ResponseEntity<CpfReport> saveNewReport(@RequestBody CpfDTO dto) {

        CpfReport cpfInDatabase = cpfReportService.findById(dto.getCpf());
        CpfReport report;
        
        if(cpfInDatabase == null){
            report = new CpfReport(dto.getCpf(), 1, LocalDate.now());
        } else{
            report = new CpfReport(cpfInDatabase.getCpf(), cpfInDatabase.getReportQuantity() + 1, LocalDate.now());
        }

        CpfReport saved = cpfReportService.insert(report);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                  .path("/{id}").buildAndExpand(saved.getCpf()).toUri();

        return ResponseEntity.created(uri).body(saved);
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<String> deleteReport(@PathVariable String cpf){
        cpfReportService.deleteById(cpf);
        CpfReport isDeleted = cpfReportService.findById(cpf);
        if(isDeleted == null){
            return ResponseEntity.ok("Denúncia retirada com sucesso!");
        } else {
            return ResponseEntity.badRequest().build();
        }
        
    }
    
}
