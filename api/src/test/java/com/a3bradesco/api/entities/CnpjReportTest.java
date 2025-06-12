package com.a3bradesco.api.entities;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CnpjReportTest {

    @Test
    void testConstructorAndGetters() {
        LocalDate now = LocalDate.now();
        CnpjReport report = new CnpjReport("12345678000199", 2, now);

        assertEquals("12345678000199", report.getCnpj());
        assertEquals(2, report.getReportQuantity());
        assertEquals(now, report.getLastTimeReported());
    }

    @Test
    void testSetters() {
        CnpjReport report = new CnpjReport();
        LocalDate date = LocalDate.of(2024, 5, 1);

        report.setCnpj("99887766000155");
        report.setReportQuantity(3);
        report.setLastTimeReported(date);

        assertEquals("99887766000155", report.getCnpj());
        assertEquals(3, report.getReportQuantity());
        assertEquals(date, report.getLastTimeReported());
    }

    @Test
    void testEqualsAndHashCode() {
        LocalDate date = LocalDate.now();

        CnpjReport r1 = new CnpjReport("12345678000199", 1, date);
        CnpjReport r2 = new CnpjReport("12345678000199", 2, date);
        CnpjReport r3 = new CnpjReport("11111111000111", 1, date);
        CnpjReport r4 = new CnpjReport(null, 1, date);
        CnpjReport r5 = new CnpjReport(null, 1, date);

        assertEquals(r1, r1);

        assertNotEquals(r1, null);

        assertNotEquals(r1, "alguma string");

        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());

        assertNotEquals(r1, r3);
        assertNotEquals(r1.hashCode(), r3.hashCode());

        assertNotEquals(r1, r4);

        assertEquals(r4, r5);
        assertEquals(r4.hashCode(), r5.hashCode());
    }
}
