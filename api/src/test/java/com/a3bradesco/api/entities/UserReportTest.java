package com.a3bradesco.api.entities;

import com.a3bradesco.api.entities.enums.ReportType;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserReportTest {

    @Test
    void testConstructorAndGetters() {
        User user = new User(1L, "Carlos", "11122233344", "carlos@email.com", "senha");
        LocalDateTime now = LocalDateTime.now().withNano(0);
        UserReport report = new UserReport(1L, user, ReportType.EMAIL, "spam@email.com", now);

        assertEquals(1L, report.getId());
        assertEquals(user, report.getReporter());
        assertEquals(ReportType.EMAIL, report.getReportType());
        assertEquals("spam@email.com", report.getReportValue());
        assertEquals(now, report.getReportMoment());
    }

    @Test
    void testSetters() {
        UserReport report = new UserReport();
        User user = new User(2L, "Joana", "99988877766", "joana@email.com", "1234");
        LocalDateTime dateTime = LocalDateTime.of(2025, 6, 10, 10, 30).withNano(0);

        report.setId(2L);
        report.setReporter(user);
        report.setReportType(ReportType.SITE);
        report.setReportValue("phishingsite.com");
        report.setReportMoment(dateTime);

        assertEquals(2L, report.getId());
        assertEquals(user, report.getReporter());
        assertEquals(ReportType.SITE, report.getReportType());
        assertEquals("phishingsite.com", report.getReportValue());
        assertEquals(dateTime, report.getReportMoment());
    }

    @Test
    void testEqualsAndHashCode() {
        User user = new User(1L, "Carlos", "11122233344", "carlos@email.com", "senha");

        UserReport r1 = new UserReport(null, user, ReportType.CPF, "12345678900", LocalDateTime.now());
        UserReport r2 = new UserReport(null, user, ReportType.CPF, "12345678900", LocalDateTime.now());
        UserReport r3 = new UserReport(null, user, ReportType.EMAIL, "outro@email.com", LocalDateTime.now());

        assertEquals(r1, r2);
        assertNotEquals(r1, r3);
        assertEquals(r1.hashCode(), r2.hashCode());
        assertNotEquals(r1.hashCode(), r3.hashCode());
    }
}
