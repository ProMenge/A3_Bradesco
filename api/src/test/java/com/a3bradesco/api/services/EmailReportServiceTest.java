package com.a3bradesco.api.services;

import com.a3bradesco.api.entities.EmailReport;
import com.a3bradesco.api.repositories.EmailReportRepository;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EmailReportServiceTest {

    @InjectMocks
    EmailReportService service;

    @Mock
    EmailReportRepository repository;

    @BeforeEach
    void setup() {
    MockitoAnnotations.openMocks(this);
    service = spy(service); // spy obrigatório, já que você está mockando método da própria instância
    doReturn(repository).when(service).getRepository(); // retorno certo
}
    // 1. Salva um Email que ainda não foi denunciado antes
    @Test
    void whenNewEmail_thenCreatesNewReport() {
        String email = "12345678900123";

        // Simula que ainda não existe denúncia para esse número
        doReturn(null).when(service).findById(email);

        EmailReport newReport = new EmailReport(email, 1, LocalDate.now());
        doReturn(newReport).when(service).insert(any(EmailReport.class));

        EmailReport result = service.saveNewReport(email);

        assertNotNull(result);
        assertEquals(1, result.getReportQuantity());
        verify(service).insert(any(EmailReport.class));
    }
    // 2. Quando já existe um registro, o serviço incrementa a contagem de denúncias.
    @Test
    void whenExistingEmail_thenIncrementsReport() {
        String email = "12345678900123";

        EmailReport existing = new EmailReport(email, 2, LocalDate.now());
        doReturn(existing).when(service).findById(email);

        EmailReport updated = new EmailReport(email, 3, LocalDate.now());
        doReturn(updated).when(service).insert(any(EmailReport.class));

        EmailReport result = service.saveNewReport(email);

        assertEquals(3, result.getReportQuantity());
        verify(service).insert(any(EmailReport.class));
    }
    // 3. Volta uma exceção quando tentar excluir uma denúncia inexistente.
    @Test
    void whenDeletingNonexistentEmail_thenThrowsException() {
    String email = "12345678900123";

    // Simula que o método deleteReport lança EntityNotFoundException
    doThrow(new EntityNotFoundException()).when(service).deleteReport(email);

    // Verifica se a exceção esperada é realmente lançada
    assertThrows(EntityNotFoundException.class, () -> service.deleteReport(email));
}
    // 4. Garantir que, se só há 1 denúncia, o serviço realmente exclui o registro do banco.
    @Test
    void whenDeletingEmailWithSingleReport_thenDeletes() {
        String email = "12345678900123";
        EmailReport existing = new EmailReport(email, 1, LocalDate.now());

        doReturn(existing).when(service).findById(email);

        service.deleteReport(email);

        verify(service).deleteById(email);
    }
    // 5. ao deletar uma denúncia de um número com várias denúncias, o sistema apenas diminui em 1 
    @Test
    void whenDeletingEmailWithMultipleReports_thenDecrements() {
        String email = "12345678900123";
        EmailReport existing = new EmailReport(email, 3, LocalDate.now());

        doReturn(existing).when(service).findById(email);

        service.deleteReport(email);

        ArgumentCaptor<EmailReport> captor = ArgumentCaptor.forClass(EmailReport.class);
        verify(service).insert(captor.capture());
        assertEquals(2, captor.getValue().getReportQuantity());
    }
}
