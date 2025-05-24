package com.a3bradesco.api.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.a3bradesco.api.dto.ReportDTO;
import com.a3bradesco.api.entities.Report;
import com.a3bradesco.api.entities.User;
import com.a3bradesco.api.entities.enums.ReportType;
import com.a3bradesco.api.services.ReportService;
import com.a3bradesco.api.services.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/reports")
public class ReportController {
    
    @Autowired
    ReportService reportService;

    @Autowired
    UserService userService;

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

    @PostMapping()
    public ResponseEntity<Report> saveNewReport(@RequestBody ReportDTO reportDTO) {
        //TODO: Atribuir report ao usuário logado
        //pega o usuário passado no dto (pelo id) no banco e atribui o report a ele
        User user = userService.findById(reportDTO.getReporterId());

        Report report = new Report(
            null,
            user,
            ReportType.valueOf(reportDTO.getReportType()),
            reportDTO.getReportValue()
        );

        Report saved = reportService.insert(report);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                  .path("/{id}").buildAndExpand(saved.getId()).toUri();

        return ResponseEntity.created(uri).body(saved);
    }
    
}
