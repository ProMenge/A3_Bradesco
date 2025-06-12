package com.a3bradesco.api.entities;

import com.a3bradesco.api.entities.enums.ReportType;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserReportTest {

    @Test
    void testConstructorAndGetters() {
        User user = new User(1L, "Caue Urbini", "11122233344", "Caue@email.com", "Galinha7");
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
        User user = new User(2L, "Caue Urbini", "99988877766", " caue@email.com", "Galinha7");
        LocalDateTime dateTime = LocalDateTime.of(2025, 6, 10, 10, 30).withNano(0);

        report.setId(2L);
        report.setReporter(user);
        report.setReportType(ReportType.SITE);
        report.setReportValue("google.com");
        report.setReportMoment(dateTime);

        assertEquals(2L, report.getId());
        assertEquals(user, report.getReporter());
        assertEquals(ReportType.SITE, report.getReportType());
        assertEquals("google.com", report.getReportValue());
        assertEquals(dateTime, report.getReportMoment());
    }

    @Test
    void testEqualsAndHashCode() {
        User reporter1 = new User();
        reporter1.setId(1L);
        User reporter2 = new User();
        reporter2.setId(1L);
        User reporter3 = new User();
        reporter3.setId(2L);

        LocalDateTime now = LocalDateTime.now();

        UserReport r1 = new UserReport(reporter1.getId(), reporter1, ReportType.CPF, "123", now);
        UserReport r2 = new UserReport(reporter2.getId(), reporter2, ReportType.CPF, "123", now);
        UserReport r3 = new UserReport(reporter3.getId(), reporter3, ReportType.CPF, "123", now);
        UserReport r4 = new UserReport(reporter1.getId(), reporter1, ReportType.CNPJ, "123", now);
        UserReport r5 = new UserReport(reporter1.getId(), reporter1, ReportType.CPF, "456", now);
        UserReport r6 = new UserReport(null, null, ReportType.CPF, "123", now);
        UserReport r7 = new UserReport(null, null, ReportType.CPF, "123", now);

        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());

        assertNotEquals(r1, r3);
        assertNotEquals(r1, r4);
        assertNotEquals(r1, r5);

        assertEquals(r6, r7);
        assertEquals(r6.hashCode(), r7.hashCode());

        assertNotEquals(r1, null);
        assertNotEquals(r1, "string");
        assertEquals(r1, r1);
    }
}
