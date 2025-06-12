package com.a3bradesco.api.entities;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CellphoneReportTest {

    @Test
    void testConstructorAndGetters() {
        LocalDate now = LocalDate.now();
        CellphoneReport report = new CellphoneReport("11992078740", 3, now);

        assertEquals("11992078740", report.getCellphone());
        assertEquals(3, report.getReportQuantity());
        assertEquals(now, report.getLastTimeReported());
    }

    @Test
    void testSetters() {
        CellphoneReport report = new CellphoneReport();
        LocalDate date = LocalDate.of(2024, 1, 1);

        report.setCellphone("11912345678");
        report.setReportQuantity(2);
        report.setLastTimeReported(date);

        assertEquals("11912345678", report.getCellphone());
        assertEquals(2, report.getReportQuantity());
        assertEquals(date, report.getLastTimeReported());
    }

    @Test
    void testEqualsAndHashCode() {
        LocalDate date = LocalDate.now();

        CellphoneReport r1 = new CellphoneReport("11999999999", 1, date);
        CellphoneReport r2 = new CellphoneReport("11999999999", 2, date);
        CellphoneReport r3 = new CellphoneReport("11888888888", 1, date);
        CellphoneReport r4 = new CellphoneReport(null, 1, date);
        CellphoneReport r5 = new CellphoneReport(null, 2, date);

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
