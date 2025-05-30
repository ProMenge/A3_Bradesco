package com.a3bradesco.api.services;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.a3bradesco.api.entities.SiteReport;
import com.a3bradesco.api.repositories.SiteReportRepository;

@Service
public class SiteReportService extends AbstractReportService<SiteReport>{
    
    @Autowired
    SiteReportRepository siteReportReporitory;

    @Override
    protected JpaRepository<SiteReport, String> getRepository() {
        return siteReportReporitory;
    }

    @Override
    public SiteReport saveNewReport(String site){
        Optional<SiteReport> siteInDatabase = findByIdOptional(site);

        if(siteInDatabase.isEmpty()){
            return insert(new SiteReport(site, 1, LocalDate.now()));
        } else {
            SiteReport newReport = new SiteReport(
                siteInDatabase.get().getSite(),
                siteInDatabase.get().getReportQuantity() + 1,
                LocalDate.now()
            );
            return insert(newReport);
        }
    }

    @Override
    public void deleteReport(String site) {
        SiteReport siteInDatabase = findById(site);

        if(siteInDatabase.getReportQuantity() <= 1){
            deleteById(site);
        } else {
            SiteReport updatedReport = new SiteReport(
                siteInDatabase.getSite(),
                siteInDatabase.getReportQuantity() -1,
                siteInDatabase.getLastTimeReported()
            );
            insert(updatedReport);
        }
    }
}
