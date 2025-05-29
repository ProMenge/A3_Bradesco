package com.a3bradesco.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.a3bradesco.api.entities.User;
import com.a3bradesco.api.entities.UserReport;
import com.a3bradesco.api.repositories.UserReportRepository;

@Service
public class UserReportService {
    
    @Autowired
    UserReportRepository userReportRepository;

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
}
