package com.a3bradesco.api.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.a3bradesco.api.entities.CnpjReport;
import com.a3bradesco.api.repositories.CnpjReportRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CnpjReportService extends AbstractReportService<CnpjReport>{
    
    @Autowired
    CnpjReportRepository cnpjReportReporitory;

    @Override
    protected JpaRepository<CnpjReport, String> getRepository() {
        return cnpjReportReporitory;
    }

    @Override
    public CnpjReport saveNewReport(String cnpj){
        CnpjReport cnpjInDatabase = findById(cnpj);

        if(cnpjInDatabase == null){
            return insert(new CnpjReport(cnpj, 1, LocalDate.now()));
        } else {
            CnpjReport newReport = new CnpjReport(
                cnpjInDatabase.getCnpj(),
                cnpjInDatabase.getReportQuantity() + 1,
                LocalDate.now()
            );
            return insert(newReport);
        }
    }

    @Override
    public void deleteReport(String cnpj) {
        CnpjReport cnpjInDatabase = findById(cnpj);

        if (cnpjInDatabase == null) {
            throw new EntityNotFoundException("Celular não encontrado");
        }
        if(cnpjInDatabase.getReportQuantity() <= 1){
            deleteById(cnpj);
        } else {
            CnpjReport updatedReport = new CnpjReport(
                cnpjInDatabase.getCnpj(),
                cnpjInDatabase.getReportQuantity() -1,
                cnpjInDatabase.getLastTimeReported()
            );
            insert(updatedReport);
        }
    }
}
