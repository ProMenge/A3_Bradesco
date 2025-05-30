package com.a3bradesco.api.services;

import com.a3bradesco.api.entities.CpfReport;
import com.a3bradesco.api.repositories.CpfReportRepository;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CpfReportServiceTest {

    @InjectMocks
    private CpfReportService service;

    @Mock
    private CpfReportRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
void shouldSaveNewCpfReportWhenNotExists() {
    String cpf = "12345678900";
    when(repository.findById(cpf)).thenReturn(Optional.empty());

    CpfReport savedReport = new CpfReport(cpf, 1, LocalDate.now());
    when(repository.save(any(CpfReport.class))).thenReturn(savedReport);

    CpfReport result = service.saveNewReport(cpf);

    assertEquals(cpf, result.getCpf());
    assertEquals(1, result.getReportQuantity());
    assertEquals(LocalDate.now(), result.getLastTimeReported());
    verify(repository).save(any(CpfReport.class));
}


    @Test
void shouldUpdateCpfReportWhenExists() {
    String cpf = "12345678900";
    CpfReport existing = new CpfReport(cpf, 2, LocalDate.now().minusDays(1));
    when(repository.findById(cpf)).thenReturn(Optional.of(existing));
    when(repository.save(any(CpfReport.class))).thenAnswer(invocation -> invocation.getArgument(0)); // <-- adicionado

    CpfReport result = service.saveNewReport(cpf);

    assertEquals(3, result.getReportQuantity());
    assertEquals(LocalDate.now(), result.getLastTimeReported());
    verify(repository).save(any(CpfReport.class));
}

    @Test
    void shouldDeleteCpfReportWhenQuantityIsOne() {
        String cpf = "12345678900";
        CpfReport existing = new CpfReport(cpf, 1, LocalDate.now());
        when(repository.findById(cpf)).thenReturn(Optional.of(existing));

        service.deleteReport(cpf);

        verify(repository).deleteById(cpf);
    }

    @Test
    void shouldDecreaseReportQuantityWhenMoreThanOne() {
        String cpf = "12345678900";
        CpfReport existing = new CpfReport(cpf, 3, LocalDate.now());
        when(repository.findById(cpf)).thenReturn(Optional.of(existing));

        service.deleteReport(cpf);

        ArgumentCaptor<CpfReport> captor = ArgumentCaptor.forClass(CpfReport.class);
        verify(repository).save(captor.capture());
        assertEquals(2, captor.getValue().getReportQuantity());
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonexistentCpf() {
        String cpf = "00000000000";
        when(repository.findById(cpf)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.deleteReport(cpf));
    }
}
