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
        LocalDate now = LocalDate.now();
        SiteReport r1 = new SiteReport("www.site.com", 1, now);
        SiteReport r2 = new SiteReport("www.site.com", 10, now);
        SiteReport r3 = new SiteReport("www.diferente.com", 1, now);

        assertEquals(r1, r2);
        assertNotEquals(r1, r3);
        assertEquals(r1.hashCode(), r2.hashCode());
        assertNotEquals(r1.hashCode(), r3.hashCode());
    }
}
