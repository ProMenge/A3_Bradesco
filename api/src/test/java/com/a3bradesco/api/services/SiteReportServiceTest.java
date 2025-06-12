package com.a3bradesco.api.services;

import com.a3bradesco.api.entities.SiteReport;
import com.a3bradesco.api.repositories.SiteReportRepository;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SiteReportServiceTest {

    @InjectMocks
    SiteReportService service;

    @Mock
    SiteReportRepository repository;

    @BeforeEach
    void setup() {
    MockitoAnnotations.openMocks(this);
    service = spy(service); // spy obrigatório, já que você está mockando método da própria instância
    doReturn(repository).when(service).getRepository(); // retorno certo
}
    // 1. Salvar um Site que ainda não foi denunciado antes
    @Test
void whenNewSite_thenCreatesNewReport() {
    String site = "https://github.com";

    // Simula que ainda não existe denúncia para esse número
    doReturn(Optional.empty()).when(service).findByIdOptional(site);

    SiteReport newReport = new SiteReport(site, 1, LocalDate.now());
    doReturn(newReport).when(service).insert(any(SiteReport.class));

    SiteReport result = service.saveNewReport(site);

    assertNotNull(result);
    assertEquals(1, result.getReportQuantity());
    verify(service).insert(any(SiteReport.class));
}

    // 2. Quando já existe um registro, o serviço incrementa a contagem de denúncias.
    @Test
void whenExistingSite_thenIncrementsReport() {
    String site = "https://github.com";

    SiteReport existing = new SiteReport(site, 2, LocalDate.now());
    doReturn(Optional.of(existing)).when(service).findByIdOptional(site);

    SiteReport updated = new SiteReport(site, 3, LocalDate.now());
    doReturn(updated).when(service).insert(any(SiteReport.class));

    SiteReport result = service.saveNewReport(site);

    assertEquals(3, result.getReportQuantity());
    verify(service).insert(any(SiteReport.class));
}

    // 3. Volta uma exceção quando tentar excluir uma denúncia inexistente.
    @Test
    void whenDeletingNonexistentSite_thenThrowsException() {
    String site = "https://github.com";

    // Simula que o método deleteReport lança EntityNotFoundException
    doThrow(new EntityNotFoundException()).when(service).deleteReport(site);

    // Verifica se a exceção esperada é realmente lançada
    assertThrows(EntityNotFoundException.class, () -> service.deleteReport(site));
}
    // 4. Garantir que, se só há 1 denúncia, o serviço realmente exclui o registro do banco.
    @Test
    void whenDeletingSiteWithSingleReport_thenDeletes() {
        String site = "https://github.com";
        SiteReport existing = new SiteReport(site, 1, LocalDate.now());

        doReturn(existing).when(service).findById(site);

        service.deleteReport(site);

        verify(service).deleteById(site);
    }
    // 5. ao deletar uma denúncia de um número com várias denúncias, o sistema apenas diminui em 1 
    @Test
    void whenDeletingSiteWithMultipleReports_thenDecrements() {
        String site = "https://github.com";
        SiteReport existing = new SiteReport(site, 3, LocalDate.now());

        doReturn(existing).when(service).findById(site);

        service.deleteReport(site);

        ArgumentCaptor<SiteReport> captor = ArgumentCaptor.forClass(SiteReport.class);
        verify(service).insert(captor.capture());
        assertEquals(2, captor.getValue().getReportQuantity());
    }
}
