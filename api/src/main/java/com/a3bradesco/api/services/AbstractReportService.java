package com.a3bradesco.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.a3bradesco.api.entities.AbstractReport;

public abstract class AbstractReportService<T extends AbstractReport> {
    protected abstract JpaRepository<T, String> getRepository();

    public List<T> findAll() {
        return getRepository().findAll();
    }

    public T findById(String id) {
        Optional<T> reportObject = getRepository().findById(id);
        return reportObject.orElse(null);
    }

    public T insert(T report){
        return getRepository().save(report);
    }

    public void deleteById(String id){
        getRepository().deleteById(id);
    }

    protected abstract T saveNewReport(String id);

    protected abstract void deleteReport(String id);
}
