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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.a3bradesco.api.dto.SiteDTO;
import com.a3bradesco.api.entities.SiteReport;
import com.a3bradesco.api.services.SiteReportService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/site-reports")
public class SiteReportController {

    @Autowired
    SiteReportService siteReportService;

    @GetMapping
    public ResponseEntity<List<SiteReport>> findAll() {
        List<SiteReport> siteReportList = siteReportService.findAll();
        return ResponseEntity.ok().body(siteReportList);
    }

    @GetMapping("/{site}")
    public ResponseEntity<SiteReport> findById(@PathVariable String site) {
        SiteReport report = siteReportService.findById(site);
        return ResponseEntity.ok().body(report);
    }

    @PostMapping()
    public ResponseEntity<SiteReport> saveNewReport(@RequestBody @Valid SiteDTO dto) {
        SiteReport saved = siteReportService.saveNewReport(dto.getSite());

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(saved.getSite()).toUri();

        return ResponseEntity.created(uri).body(saved);
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteReport(@RequestParam String site) {
        siteReportService.deleteReport(site);
        return ResponseEntity.ok("Denúncia retirada com sucesso!");
    }
}