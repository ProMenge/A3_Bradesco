package com.a3bradesco.api.controllers;

import java.net.URI;
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

import com.a3bradesco.api.dto.CnpjDTO;
import com.a3bradesco.api.entities.CnpjReport;
import com.a3bradesco.api.services.CnpjReportService;


@RestController
@RequestMapping("/cnpj-reports")
public class CnpjReportController {
    
    @Autowired
    CnpjReportService cnpjReportService;

    @GetMapping
    public ResponseEntity<List<CnpjReport>> findAll() {
        List<CnpjReport> cnpjReportList = cnpjReportService.findAll();
        return ResponseEntity.ok().body(cnpjReportList);
    }

    @GetMapping("/{cnpj}")
    public ResponseEntity<CnpjReport> findById(@PathVariable String cnpj){
        CnpjReport report = cnpjReportService.findById(cnpj);
        return ResponseEntity.ok().body(report);
    }

    @PostMapping()
    public ResponseEntity<CnpjReport> saveNewReport(@RequestBody CnpjDTO dto) {
        CnpjReport saved = cnpjReportService.saveNewReport(dto.getCnpj());

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                  .path("/{id}").buildAndExpand(saved.getCnpj()).toUri();

        return ResponseEntity.created(uri).body(saved);
    }

    @DeleteMapping("/{cnpj}")
    public ResponseEntity<String> deleteReport(@PathVariable String cnpj){
        cnpjReportService.deleteReport(cnpj);
        return ResponseEntity.ok("Denúncia retirada com sucesso!");
    }
}
