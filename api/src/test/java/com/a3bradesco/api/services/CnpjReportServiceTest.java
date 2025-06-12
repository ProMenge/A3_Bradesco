package com.a3bradesco.api.services;

import com.a3bradesco.api.entities.CnpjReport;
import com.a3bradesco.api.repositories.CnpjReportRepository;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CnpjReportServiceTest {

    @InjectMocks
    CnpjReportService service;

    @Mock
    CnpjReportRepository repository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        service = spy(service); // spy obrigatório, já que você está mockando método da própria instância
        doReturn(repository).when(service).getRepository(); // retorno certo
    }

    // 1. Salvar um Cnpj que ainda não foi denunciado antes
    @Test
    void whenNewcnpj_thenCreatesNewReport() {
        String cnpj = "12345678900123";

        // Simula que ainda não existe denúncia para esse número
        doReturn(Optional.empty()).when(service).findByIdOptional(cnpj);

        CnpjReport newReport = new CnpjReport(cnpj, 1, LocalDate.now());
        doReturn(newReport).when(service).insert(any(CnpjReport.class));

        CnpjReport result = service.saveNewReport(cnpj);

        assertNotNull(result);
        assertEquals(1, result.getReportQuantity());
        verify(service).insert(any(CnpjReport.class));
    }

    // 2. Quando já existe um registro, o serviço incrementa a contagem de
    // denúncias.
    @Test
    void whenExistingcnpj_thenIncrementsReport() {
        String cnpj = "12345678900123";

        CnpjReport existing = new CnpjReport(cnpj, 2, LocalDate.now());
        doReturn(Optional.of(existing)).when(service).findByIdOptional(cnpj);

        CnpjReport updated = new CnpjReport(cnpj, 3, LocalDate.now());
        doReturn(updated).when(service).insert(any(CnpjReport.class));

        CnpjReport result = service.saveNewReport(cnpj);

        assertEquals(3, result.getReportQuantity());
        verify(service).insert(any(CnpjReport.class));
    }

    // 3. Volta uma exceção quando tentar excluir uma denúncia inexistente.
    @Test
    void whenDeletingNonexistentcnpj_thenThrowsException() {
        String cnpj = "12345678900123";

        // Simula que o método deleteReport lança EntityNotFoundException
        doThrow(new EntityNotFoundException()).when(service).deleteReport(cnpj);

        // Verifica se a exceção esperada é realmente lançada
        assertThrows(EntityNotFoundException.class, () -> service.deleteReport(cnpj));
    }

    // 4. Garantir que, se só há 1 denúncia, o serviço realmente exclui o registro
    // do banco.
    @Test
    void whenDeletingcnpjWithSingleReport_thenDeletes() {
        String cnpj = "12345678900123";
        CnpjReport existing = new CnpjReport(cnpj, 1, LocalDate.now());

        doReturn(existing).when(service).findById(cnpj);

        service.deleteReport(cnpj);

        verify(service).deleteById(cnpj);
    }

    // 5. Ao deletar uma denúncia de um número com várias denúncias, o sistema
    // apenas diminui em 1
    @Test
    void whenDeletingcnpjWithMultipleReports_thenDecrements() {
        String cnpj = "12345678900123";
        CnpjReport existing = new CnpjReport(cnpj, 3, LocalDate.now());

        doReturn(existing).when(service).findById(cnpj);

        service.deleteReport(cnpj);

        ArgumentCaptor<CnpjReport> captor = ArgumentCaptor.forClass(CnpjReport.class);
        verify(service).insert(captor.capture());
        assertEquals(2, captor.getValue().getReportQuantity());
    }
}
