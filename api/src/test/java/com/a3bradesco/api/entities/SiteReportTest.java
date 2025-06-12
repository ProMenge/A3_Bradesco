package com.a3bradesco.api.entities;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class SiteReportTest {

    @Test
    void testConstructorAndGetters() {
        LocalDate now = LocalDate.now();
        SiteReport report = new SiteReport("www.exemplo.com", 5, now);

        assertEquals("www.exemplo.com", report.getSite());
        assertEquals(5, report.getReportQuantity());
        assertEquals(now, report.getLastTimeReported());
    }

    @Test
    void testSetters() {
        SiteReport report = new SiteReport();
        LocalDate date = LocalDate.of(2025, 1, 1);

        report.setSite("www.teste.com");
        report.setReportQuantity(3);
        report.setLastTimeReported(date);

        assertEquals("www.teste.com", report.getSite());
        assertEquals(3, report.getReportQuantity());
        assertEquals(date, report.getLastTimeReported());
    }

    @Test
    void testEqualsAndHashCode() {
        LocalDate date = LocalDate.now();

        SiteReport r1 = new SiteReport("example.com", 1, date);
        SiteReport r2 = new SiteReport("example.com", 2, date);
        SiteReport r3 = new SiteReport("another.com", 1, date);
        SiteReport r4 = new SiteReport(null, 1, date);
        SiteReport r5 = new SiteReport(null, 2, date);

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
