package com.a3bradesco.api.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.a3bradesco.api.entities.User;
import com.a3bradesco.api.entities.enums.ReportType;
import com.a3bradesco.api.repositories.ReportRepository;
import com.a3bradesco.api.repositories.UserRepository;

import com.a3bradesco.api.entities.Report;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
	private UserRepository userRepository;

    @Autowired
    private ReportRepository reportRepository;

	@Override
	public void run(String... args) throws Exception {
		User user1 = new User(null, "Igor", "11111111111",  "11999999999", "igor@gmail.com", "123");
        User user2 = new User(null, "Fred", "22222222222",  "11988888888", "fred@gmail.com", "123");
        User user3 = new User(null, "Caue", "33333333333",  "11977777777", "caue@gmail.com", "123");

        Report report1 = new Report(null, user1, ReportType.CPF, "12312312312");
        Report report2 = new Report(null, user1, ReportType.CNPJ, "92.221.230000107");
        Report report3 = new Report(null, user2, ReportType.EMAIL, "mockemail@gmail.com");
        Report report4 = new Report(null, user2, ReportType.SITE, "mocksite.com.br");
        Report report5 = new Report(null, user3, ReportType.CELLPHONE, "11988887777");

		userRepository.saveAll(Arrays.asList(user1, user2, user3));
        
        reportRepository.saveAll(Arrays.asList(report1, report2, report3, report4, report5));
	}
}
