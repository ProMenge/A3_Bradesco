package com.a3bradesco.api.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.a3bradesco.api.entities.EmailReport;
import com.a3bradesco.api.repositories.EmailReportRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EmailReportService extends AbstractReportService<EmailReport>{
    
    @Autowired
    EmailReportRepository emailReportReporitory;

    @Override
    protected JpaRepository<EmailReport, String> getRepository() {
        return emailReportReporitory;
    }

    @Override
    public EmailReport saveNewReport(String email){
        EmailReport emailInDatabase = findById(email);

        if(emailInDatabase == null){
            return insert(new EmailReport(email, 1, LocalDate.now()));
        } else {
            EmailReport newReport = new EmailReport(
                emailInDatabase.getEmail(),
                emailInDatabase.getReportQuantity() + 1,
                LocalDate.now()
            );
            return insert(newReport);
        }
    }

    @Override
    public void deleteReport(String email) {
        EmailReport emailInDatabase = findById(email);

        if (emailInDatabase == null) {
            throw new EntityNotFoundException("Celular não encontrado");
        }
        if(emailInDatabase.getReportQuantity() <= 1){
            deleteById(email);
        } else {
            EmailReport updatedReport = new EmailReport(
                emailInDatabase.getEmail(),
                emailInDatabase.getReportQuantity() -1,
                emailInDatabase.getLastTimeReported()
            );
            insert(updatedReport);
        }
    }
}
