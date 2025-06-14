package com.a3bradesco.api.entities;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CpfReportTest {

    @Test
    void testConstructorAndGetters() {
        LocalDate now = LocalDate.now();
        CpfReport report = new CpfReport("12345678901", 4, now);

        assertEquals("12345678901", report.getCpf());
        assertEquals(4, report.getReportQuantity());
        assertEquals(now, report.getLastTimeReported());
    }

    @Test
    void testSetters() {
        CpfReport report = new CpfReport();
        LocalDate date = LocalDate.of(2023, 12, 31);

        report.setCpf("98765432100");
        report.setReportQuantity(5);
        report.setLastTimeReported(date);

        assertEquals("98765432100", report.getCpf());
        assertEquals(5, report.getReportQuantity());
        assertEquals(date, report.getLastTimeReported());
    }

    @Test
    void testEqualsAndHashCode() {
        LocalDate date = LocalDate.now();

        CpfReport r1 = new CpfReport("12345678901", 1, date);
        CpfReport r2 = new CpfReport("12345678901", 2, date);
        CpfReport r3 = new CpfReport("09876543210", 1, date);
        CpfReport r4 = new CpfReport(null, 1, date);
        CpfReport r5 = new CpfReport(null, 2, date);

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
