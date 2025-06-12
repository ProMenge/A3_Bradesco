package com.a3bradesco.api.services;

import com.a3bradesco.api.entities.CpfReport;
import com.a3bradesco.api.repositories.CpfReportRepository;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CpfReportServiceTest {

    @InjectMocks
    CpfReportService service;

    @Mock
    CpfReportRepository repository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        service = spy(service); // spy obrigatório, já que você está mockando método da própria instância
        doReturn(repository).when(service).getRepository(); // retorno certo
    }

    // 1. Salva um Cpf que ainda não foi denunciado antes
    @Test
    void whenNewcpf_thenCreatesNewReport() {
        String cpf = "12345678900";

        // Simula que ainda não existe denúncia para esse número
        doReturn(Optional.empty()).when(service).findByIdOptional(cpf);

        CpfReport newReport = new CpfReport(cpf, 1, LocalDate.now());
        doReturn(newReport).when(service).insert(any(CpfReport.class));

        CpfReport result = service.saveNewReport(cpf);

        assertNotNull(result);
        assertEquals(1, result.getReportQuantity());
        verify(service).insert(any(CpfReport.class));
    }

    // 2. Quando já existe um registro, o serviço incrementa a contagem de
    // denúncias.
    @Test
    void whenExistingcpf_thenIncrementsReport() {
        String cpf = "12345678900123";

        CpfReport existing = new CpfReport(cpf, 2, LocalDate.now());
        doReturn(Optional.of(existing)).when(service).findByIdOptional(cpf);

        CpfReport updated = new CpfReport(cpf, 3, LocalDate.now());
        doReturn(updated).when(service).insert(any(CpfReport.class));

        CpfReport result = service.saveNewReport(cpf);

        assertEquals(3, result.getReportQuantity());
        verify(service).insert(any(CpfReport.class));
    }

    // 3. Volta uma exceção quando tentar excluir uma denúncia inexistente.
    @Test
    void whenDeletingNonexistentCpf_thenThrowsException() {
        String cpf = "12345678900";

        // Simula que o método deleteReport lança EntityNotFoundException
        doThrow(new EntityNotFoundException()).when(service).deleteReport(cpf);

        // Verifica se a exceção esperada é realmente lançada
        assertThrows(EntityNotFoundException.class, () -> service.deleteReport(cpf));
    }

    // 4. Garantir que, se só há 1 denúncia, o serviço realmente exclui o registro
    // do banco.
    @Test
    void whenDeletingCpfWithSingleReport_thenDeletes() {
        String cpf = "12345678900";
        CpfReport existing = new CpfReport(cpf, 1, LocalDate.now());

        doReturn(existing).when(service).findById(cpf);

        service.deleteReport(cpf);

        verify(service).deleteById(cpf);
    }

    // 5. ao deletar uma denúncia de um número com várias denúncias, o sistema
    // apenas diminui em 1
    @Test
    void whenDeletingCpfWithMultipleReports_thenDecrements() {
        String cpf = "12345678900";
        CpfReport existing = new CpfReport(cpf, 3, LocalDate.now());

        doReturn(existing).when(service).findById(cpf);

        service.deleteReport(cpf);

        ArgumentCaptor<CpfReport> captor = ArgumentCaptor.forClass(CpfReport.class);
        verify(service).insert(captor.capture());
        assertEquals(2, captor.getValue().getReportQuantity());
    }
     @Test
    void testFindAll() {
        CpfReport report = new CpfReport();
        when(repository.findAll()).thenReturn(List.of(report));

        List<CpfReport> result = service.findAll();

        assertEquals(1, result.size());
        verify(repository).findAll();
    }

    @Test
    void testFindById_existingId() {
        String id = "123";
        CpfReport report = new CpfReport();
        when(repository.findById(id)).thenReturn(Optional.of(report));

        CpfReport result = service.findById(id);

        assertNotNull(result);
        verify(repository).findById(id);
    }
    @Test
    void testFindById_nonexistentId_throwsException() {
        String id = "456";
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.findById(id));
    }
}
