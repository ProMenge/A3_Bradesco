package com.a3bradesco.api.config;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.a3bradesco.api.entities.CellphoneReport;
import com.a3bradesco.api.entities.CnpjReport;
import com.a3bradesco.api.entities.CpfReport;
import com.a3bradesco.api.entities.EmailReport;
import com.a3bradesco.api.entities.SiteReport;
import com.a3bradesco.api.entities.User;
import com.a3bradesco.api.entities.UserReport;
import com.a3bradesco.api.entities.enums.ReportType;
import com.a3bradesco.api.repositories.CellphoneReportRepository;
import com.a3bradesco.api.repositories.CnpjReportRepository;
import com.a3bradesco.api.repositories.CpfReportRepository;
import com.a3bradesco.api.repositories.EmailReportRepository;
import com.a3bradesco.api.repositories.SiteReportRepository;
import com.a3bradesco.api.repositories.UserReportRepository;
import com.a3bradesco.api.repositories.UserRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserReportRepository reportRepository;
    @Autowired
    private CpfReportRepository cpfReportRepository;
    @Autowired
    private CnpjReportRepository cnpjReportRepository;
    @Autowired
    private CellphoneReportRepository cellphoneReportRepository;
    @Autowired
    private EmailReportRepository emailReportRepository;
    @Autowired
    private SiteReportRepository siteReportRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        User user1 = new User(null, "Igor", "11111111111", "igor@gmail.com", passwordEncoder.encode("123"));
        User user2 = new User(null, "Fred", "22222222222", "fred@gmail.com",
                passwordEncoder.encode("!2168731713jdAsa"));
        User user3 = new User(null, "Caue", "33333333333", "caue@gmail.com", passwordEncoder.encode("123"));

        UserReport report1 = new UserReport(null, user1, ReportType.CPF, "12312312312", LocalDateTime.now());
        UserReport report2 = new UserReport(null, user1, ReportType.CNPJ, "92221230000107", LocalDateTime.now());
        UserReport report3 = new UserReport(null, user2, ReportType.EMAIL, "mockemail@gmail.com", LocalDateTime.now());
        UserReport report4 = new UserReport(null, user2, ReportType.SITE, "mocksite.com.br", LocalDateTime.now());
        UserReport report5 = new UserReport(null, user3, ReportType.CELLPHONE, "11988887777", LocalDateTime.now());

        CpfReport cpfReport1 = new CpfReport("12312312312", 2, LocalDate.now());
        CpfReport cpfReport2 = new CpfReport("12312312311", 1, LocalDate.now());

        CnpjReport cnpjReport1 = new CnpjReport("77311267000124", 1, LocalDate.now());
        CnpjReport cnpjReport2 = new CnpjReport("64788633000199", 2, LocalDate.now());

        CellphoneReport cellphoneReport1 = new CellphoneReport("11911223344", 3, LocalDate.now());
        CellphoneReport cellphoneReport2 = new CellphoneReport("11955667788", 4, LocalDate.now());

        EmailReport emailReport1 = new EmailReport("igor452@gmail.com", 2, LocalDate.now());
        EmailReport emailReport2 = new EmailReport("fred452@gmail.com", 3, LocalDate.now());

        SiteReport siteReport1 = new SiteReport("sitefraude.com.br", 2, LocalDate.now());
        SiteReport siteReport2 = new SiteReport("fraudesite.com.br", 4, LocalDate.now());

        siteReportRepository.saveAll(Arrays.asList(siteReport1, siteReport2));
        emailReportRepository.saveAll(Arrays.asList(emailReport1, emailReport2));
        cellphoneReportRepository.saveAll(Arrays.asList(cellphoneReport1, cellphoneReport2));
        cnpjReportRepository.saveAll(Arrays.asList(cnpjReport1, cnpjReport2));
        userRepository.saveAll(Arrays.asList(user1, user2, user3));
        reportRepository.saveAll(Arrays.asList(report1, report2, report3, report4, report5));
        cpfReportRepository.saveAll(Arrays.asList(cpfReport1, cpfReport2));
    }
}
