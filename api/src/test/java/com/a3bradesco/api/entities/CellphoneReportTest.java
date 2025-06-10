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

        CellphoneReport report1 = new CellphoneReport("11999999999", 1, date);
        CellphoneReport report2 = new CellphoneReport("11999999999", 2, date); // reportQuantity diferente, mas cellphone igual
        CellphoneReport report3 = new CellphoneReport("11888888888", 1, date);

        assertEquals(report1, report2); // mesmo cellphone
        assertNotEquals(report1, report3); // cellphone diferente
        assertEquals(report1.hashCode(), report2.hashCode());
        assertNotEquals(report1.hashCode(), report3.hashCode());
    }
}
