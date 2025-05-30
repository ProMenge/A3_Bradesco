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

import com.a3bradesco.api.dto.UserReportDTO;
import com.a3bradesco.api.entities.User;
import com.a3bradesco.api.entities.UserReport;
import com.a3bradesco.api.services.UserReportService;
import com.a3bradesco.api.services.UserService;


@RestController
@RequestMapping("/users/{userId}/user-reports")
public class UserReportController {
    
    @Autowired
    UserReportService userReportService;

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<List<UserReport>> findAll(@PathVariable Long userId) {
        User user = userService.findById(userId);
        List<UserReport> reportList = userReportService.findByReporter(user);
        return ResponseEntity.ok().body(reportList);
    }

    @PostMapping
    public ResponseEntity<?> saveNewReport(@PathVariable Long userId, @RequestBody UserReportDTO reportDTO) {
        //TODO: Atribuir report ao usuário logado
        //pega o usuário passado no dto (pelo id) no banco e atribui o report a ele
        try {
            UserReport saved = userReportService.saveNewReport(userId, reportDTO);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                      .path("/{id}").buildAndExpand(saved.getId()).toUri();
    
            return ResponseEntity.created(uri).body(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{reportId}")
    public ResponseEntity<String> deleteReport(@PathVariable Long reportId) {
        userReportService.deleteReport(reportId);
        return ResponseEntity.ok("Denúncia retirada com sucesso!");
    }
}
