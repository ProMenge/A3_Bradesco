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

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserReportService {

    @Autowired
    UserReportRepository userReportRepository;

    @Autowired
    CpfReportService cpfReportService;

    @Autowired
    CnpjReportService cnpjReportService;

    @Autowired
    CellphoneReportService cellphoneReportService;

    @Autowired
    EmailReportService emailReportService;

    @Autowired
    SiteReportService siteReportService;

    @Autowired
    UserService userService;

    public List<UserReport> findByReporter(User reporter) {
        return userReportRepository.findByReporter(reporter);
    }

    public List<UserReport> findAll() {
        return userReportRepository.findAll();
    }

    public UserReport findById(Long id) {
        Optional<UserReport> reportObject = userReportRepository.findById(id);
        return reportObject.orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
    }

    public UserReport insert(UserReport report) {
        return userReportRepository.save(report);
    }

    public void deleteById(Long id) {
        userReportRepository.deleteById(id);
    }

    public UserReport saveNewReport(Long userId, UserReportDTO reportDTO) {
        User user = userService.findById(userId);

        List<UserReport> existingReports = userReportRepository.findByReporter(user);

        boolean isDtoValid = false;

        switch (reportDTO.getReportType()) {
            case CELLPHONE:
                isDtoValid = reportDTO.getReportValue().matches("\\d{11}");
                break;
            case CNPJ:
                isDtoValid = reportDTO.getReportValue().matches("\\d{14}");
                break;
            case CPF:
                isDtoValid = reportDTO.getReportValue().matches("\\d{11}");
                break;
            case EMAIL:
                isDtoValid = reportDTO.getReportValue().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-z]+");
                break;
            case SITE:
                isDtoValid = reportDTO.getReportValue()
                        .matches("^(https?:\\/\\/)?(www\\.)?[a-zA-Z0-9\\-]+\\.[a-zA-Z0-9\\-\\.]{2,}(\\/.*)?$");
                break;
        }

        // Checa se o usuário já tem uma denúncia vinculada a este tipo com este valor
        boolean reportAlreadyInDatabase = existingReports.stream()
                .anyMatch(report -> report.getReportType().equals(reportDTO.getReportType()) &&
                        report.getReportValue().equals(reportDTO.getReportValue()));

        if (reportAlreadyInDatabase) {
            throw new IllegalArgumentException(
                    "Já existe uma denúncia para este " + reportDTO.getReportType() + " relacionada a este usuário.");
        }

        if (existingReports.size() >= 20) {
            throw new IllegalStateException("O usuário já possui o número máximo de 20 denúncias.");
        }

        if (isDtoValid == false) {
            throw new IllegalArgumentException("O valor enviado é incompatível com o tipo de denúncia.");
        }

        UserReport report = new UserReport(
                null,
                user,
                reportDTO.getReportType(),
                reportDTO.getReportValue(),
                LocalDateTime.now());

        switch (reportDTO.getReportType()) {
            case CPF:
                cpfReportService.saveNewReport(reportDTO.getReportValue());
                break;
            case CNPJ:
                cnpjReportService.saveNewReport(reportDTO.getReportValue());
                break;
            case CELLPHONE:
                cellphoneReportService.saveNewReport(reportDTO.getReportValue());
                break;
            case EMAIL:
                emailReportService.saveNewReport(reportDTO.getReportValue());
                break;
            case SITE:
                siteReportService.saveNewReport(reportDTO.getReportValue());
                break;
        }

        return insert(report);
    }

    public void deleteReport(Long reportId) {
        UserReport reportToDelete = findById(reportId);

        switch (reportToDelete.getReportType()) {
            case CPF:
                cpfReportService.deleteReport(reportToDelete.getReportValue());
                break;
            case CNPJ:
                cnpjReportService.deleteReport(reportToDelete.getReportValue());
                break;
            case CELLPHONE:
                cellphoneReportService.deleteReport(reportToDelete.getReportValue());
                break;
            case EMAIL:
                emailReportService.deleteReport(reportToDelete.getReportValue());
                break;
            case SITE:
                siteReportService.deleteReport(reportToDelete.getReportValue());
                break;
        }

        deleteById(reportId);
    }
}