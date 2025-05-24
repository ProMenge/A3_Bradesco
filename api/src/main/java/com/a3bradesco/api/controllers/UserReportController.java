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
import com.a3bradesco.api.entities.UserReport;
import com.a3bradesco.api.entities.User;
import com.a3bradesco.api.entities.enums.ReportType;
import com.a3bradesco.api.services.UserReportService;
import com.a3bradesco.api.services.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/reports")
public class UserReportController {
    
    @Autowired
    UserReportService reportService;

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<List<UserReport>> findAll() {
        List<UserReport> reportList = reportService.findAll();
        return ResponseEntity.ok().body(reportList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserReport> findById(@PathVariable Long id){
        UserReport report = reportService.findById(id);
        return ResponseEntity.ok().body(report);
    }

    @PostMapping()
    public ResponseEntity<UserReport> saveNewReport(@RequestBody ReportDTO reportDTO) {
        //TODO: Atribuir report ao usuário logado
        //pega o usuário passado no dto (pelo id) no banco e atribui o report a ele
        User user = userService.findById(reportDTO.getReporterId());

        UserReport report = new UserReport(
            null,
            user,
            ReportType.valueOf(reportDTO.getReportType()),
            reportDTO.getReportValue()
        );

        UserReport saved = reportService.insert(report);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                  .path("/{id}").buildAndExpand(saved.getId()).toUri();

        return ResponseEntity.created(uri).body(saved);
    }
    
}
