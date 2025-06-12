package com.a3bradesco.api.entities;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EmailReportTest {

    @Test
    void testConstructorAndGetters() {
        LocalDate now = LocalDate.now();
        EmailReport report = new EmailReport("caue@gmail.com", 1, now);

        assertEquals("caue@gmail.com", report.getEmail());
        assertEquals(1, report.getReportQuantity());
        assertEquals(now, report.getLastTimeReported());
    }

    @Test
    void testSetters() {
        EmailReport report = new EmailReport();
        LocalDate date = LocalDate.of(2025, 1, 1);

        report.setEmail("caue@gmail.com");
        report.setReportQuantity(2);
        report.setLastTimeReported(date);

        assertEquals("caue@gmail.com", report.getEmail());
        assertEquals(2, report.getReportQuantity());
        assertEquals(date, report.getLastTimeReported());
    }

    @Test
    void testEqualsAndHashCode() {
        LocalDate date = LocalDate.now();

        EmailReport r1 = new EmailReport("teste@exemplo.com", 1, date);
        EmailReport r2 = new EmailReport("teste@exemplo.com", 2, date);
        EmailReport r3 = new EmailReport("outro@exemplo.com", 1, date);
        EmailReport r4 = new EmailReport(null, 1, date);
        EmailReport r5 = new EmailReport(null, 2, date);

        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());

        assertNotEquals(r1, r3);
        assertNotEquals(r1.hashCode(), r3.hashCode());

        assertEquals(r4, r5);
        assertEquals(r4.hashCode(), r5.hashCode());

        assertNotEquals(r1, null);
        assertNotEquals(r1, "string");
        assertEquals(r1, r1);
    }
}
