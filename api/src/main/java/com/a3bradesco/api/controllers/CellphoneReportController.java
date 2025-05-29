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

import com.a3bradesco.api.dto.CellphoneDTO;
import com.a3bradesco.api.entities.CellphoneReport;
import com.a3bradesco.api.services.CellphoneReportService;


@RestController
@RequestMapping("/cellphone-reports")
public class CellphoneReportController {
    
    @Autowired
    CellphoneReportService cellphoneReportService;

    @GetMapping
    public ResponseEntity<List<CellphoneReport>> findAll() {
        List<CellphoneReport> cellphoneReportList = cellphoneReportService.findAll();
        return ResponseEntity.ok().body(cellphoneReportList);
    }

    @GetMapping("/{cellphone}")
    public ResponseEntity<CellphoneReport> findById(@PathVariable String cellphone){
        CellphoneReport report = cellphoneReportService.findById(cellphone);
        return ResponseEntity.ok().body(report);
    }

    @PostMapping()
    public ResponseEntity<CellphoneReport> saveNewReport(@RequestBody CellphoneDTO dto) {
        CellphoneReport saved = cellphoneReportService.saveNewReport(dto.getCellphone());

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                  .path("/{id}").buildAndExpand(saved.getCellphone()).toUri();

        return ResponseEntity.created(uri).body(saved);
    }

    @DeleteMapping("/{cellphone}")
    public ResponseEntity<String> deleteReport(@PathVariable String cellphone){
    CellphoneReport currentDatabaseReport = cellphoneReportService.findById(cellphone);

    if(currentDatabaseReport == null) {
        return ResponseEntity.notFound().build();
    }
    if(currentDatabaseReport.getReportQuantity() <= 1){
        cellphoneReportService.deleteById(cellphone);
        return ResponseEntity.ok("Denúncia retirada com sucesso!");
    } else {
        CellphoneReport newDatabaseReport = 
        new CellphoneReport(
            currentDatabaseReport.getCellphone(), 
            currentDatabaseReport.getReportQuantity() - 1, 
            currentDatabaseReport.getLastTimeReported());

        cellphoneReportService.insert(newDatabaseReport);

        return ResponseEntity.ok("Denúncia retirada com sucesso!");
        }
    }
}
