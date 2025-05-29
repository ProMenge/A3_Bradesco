package com.a3bradesco.api.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.a3bradesco.api.entities.CellphoneReport;
import com.a3bradesco.api.repositories.CellphoneReportRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CellphoneReportService extends AbstractReportService<CellphoneReport>{
    
    @Autowired
    CellphoneReportRepository cellphoneReportReporitory;

    @Override
    protected JpaRepository<CellphoneReport, String> getRepository() {
        return cellphoneReportReporitory;
    }

    @Override
    public CellphoneReport saveNewReport(String cellphone){
        CellphoneReport cellphoneInDatabase = findById(cellphone);

        if(cellphoneInDatabase == null){
            return insert(new CellphoneReport(cellphone, 1, LocalDate.now()));
        } else {
            CellphoneReport newReport = new CellphoneReport(
                cellphoneInDatabase.getCellphone(),
                cellphoneInDatabase.getReportQuantity() + 1,
                LocalDate.now()
            );
            return insert(newReport);
        }
    }

    @Override
    public void deleteReport(String cellphone) {
        CellphoneReport cellphoneInDatabase = findById(cellphone);

        if (cellphoneInDatabase == null) {
            throw new EntityNotFoundException("Celular não encontrado");
        }
        if(cellphoneInDatabase.getReportQuantity() <= 1){
            deleteById(cellphone);
        } else {
            CellphoneReport updatedReport = new CellphoneReport(
                cellphoneInDatabase.getCellphone(),
                cellphoneInDatabase.getReportQuantity() -1,
                cellphoneInDatabase.getLastTimeReported()
            );
            insert(updatedReport);
        }
    }
}
