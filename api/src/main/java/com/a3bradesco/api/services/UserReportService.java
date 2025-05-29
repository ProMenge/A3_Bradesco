package com.a3bradesco.api.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a3bradesco.api.dto.UserReportDTO;
import com.a3bradesco.api.entities.User;
import com.a3bradesco.api.entities.UserReport;
import com.a3bradesco.api.repositories.UserReportRepository;

@Service
public class UserReportService {
    
    @Autowired
    UserReportRepository userReportRepository;

    @Autowired
    UserService userService;

    public List<UserReport> findByReporter(User reporter){
        return userReportRepository.findByReporter(reporter);
    }

    public List<UserReport> findAll() {
        return userReportRepository.findAll();
    }

    public UserReport findById(Long id) {
        Optional<UserReport> reportObject = userReportRepository.findById(id);
        return reportObject.orElse(null);
    }

    public UserReport insert(UserReport report){
        return userReportRepository.save(report);
    }

    public void deleteById(Long id){
        userReportRepository.deleteById(id);
    } 

    public UserReport saveNewReport(Long userId, UserReportDTO reportDTO) {
        User user = userService.findById(userId);

        List<UserReport> existingReports = userReportRepository.findByReporter(user);

        //Checa se o usuário já tem uma denúncia vinculada a este tipo com este valor
        boolean reportAlreadyInDatabase = existingReports.stream().anyMatch(report ->
        report.getReportType().equals(reportDTO.getReportType()) &&
        report.getReportValue().equals(reportDTO.getReportValue())
        );

        if (reportAlreadyInDatabase) {
            throw new IllegalArgumentException("Já existe uma denúncia para este " + reportDTO.getReportType() + " relacionada a este usuário.");
        }

        if (existingReports.size() >= 20) {
            throw new IllegalStateException("O usuário já possui o número máximo de 20 denúncias.");
        }

        UserReport report = new UserReport(
            null,
            user,
            reportDTO.getReportType(),
            reportDTO.getReportValue(),
            LocalDateTime.now()
        );

        return insert(report);
    }

    public boolean deleteReport(Long reportId) {
        deleteById(reportId);
        return findById(reportId) == null;
    }
}
