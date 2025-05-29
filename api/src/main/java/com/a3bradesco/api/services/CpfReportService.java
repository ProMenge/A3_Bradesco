package com.a3bradesco.api.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.a3bradesco.api.entities.CpfReport;
import com.a3bradesco.api.repositories.CpfReportRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CpfReportService extends AbstractReportService<CpfReport>{
    
    @Autowired
    CpfReportRepository cpfReportReporitory;

    @Override
    protected JpaRepository<CpfReport, String> getRepository() {
        return cpfReportReporitory;
    }

    @Override
    public CpfReport saveNewReport(String cpf){
        CpfReport cpfInDatabase = findById(cpf);

        if(cpfInDatabase == null){
            return insert(new CpfReport(cpf, 1, LocalDate.now()));
        } else {
            CpfReport newReport = new CpfReport(
                cpfInDatabase.getCpf(),
                cpfInDatabase.getReportQuantity() + 1,
                LocalDate.now()
            );
            return insert(newReport);
        }
    }

    @Override
    public void deleteReport(String cpf) {
        CpfReport cpfInDatabase = findById(cpf);

        if (cpfInDatabase == null) {
            throw new EntityNotFoundException("Celular não encontrado");
        }
        if(cpfInDatabase.getReportQuantity() <= 1){
            deleteById(cpf);
        } else {
            CpfReport updatedReport = new CpfReport(
                cpfInDatabase.getCpf(),
                cpfInDatabase.getReportQuantity() -1,
                cpfInDatabase.getLastTimeReported()
            );
            insert(updatedReport);
        }
    }
}
