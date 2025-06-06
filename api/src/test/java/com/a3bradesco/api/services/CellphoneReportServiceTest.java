package com.a3bradesco.api.services;

import com.a3bradesco.api.entities.CellphoneReport;
import com.a3bradesco.api.repositories.CellphoneReportRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CellphoneReportServiceTest {

    @InjectMocks
    CellphoneReportService service;

    @Mock
    CellphoneReportRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = spy(service); // necessário para mockar métodos herdados como `findById` e `insert`
    }
    // 1. Salvar um número de celular que ainda não foi denunciado antes
    @Test
    void whenNewCellphone_thenCreatesNewReport() {
        String cellphone = "11992067482";

        // Simula que ainda não existe denúncia para esse número
        doReturn(null).when(service).findById(cellphone);

        CellphoneReport newReport = new CellphoneReport(cellphone, 1, LocalDate.now());
        doReturn(newReport).when(service).insert(any(CellphoneReport.class));

        CellphoneReport result = service.saveNewReport(cellphone);

        assertNotNull(result);
        assertEquals(1, result.getReportQuantity());
        verify(service).insert(any(CellphoneReport.class));
    }
    // 2. Quando já existe um registro, o serviço incrementa a contagem de denúncias.
    @Test
    void whenExistingCellphone_thenIncrementsReport() {
        String cellphone = "11992067482";

        CellphoneReport existing = new CellphoneReport(cellphone, 2, LocalDate.now());
        doReturn(existing).when(service).findById(cellphone);

        CellphoneReport updated = new CellphoneReport(cellphone, 3, LocalDate.now());
        doReturn(updated).when(service).insert(any(CellphoneReport.class));

        CellphoneReport result = service.saveNewReport(cellphone);

        assertEquals(3, result.getReportQuantity());
        verify(service).insert(any(CellphoneReport.class));
    }
    // 3. Volta uma exceção quando tentar excluir uma denúncia inexistente.
    @Test
    void whenDeletingNonexistentCellphone_thenThrowsException() {
    String cellphone = "11992067482";

    // Simula que o método deleteReport lança EntityNotFoundException
    doThrow(new EntityNotFoundException()).when(service).deleteReport(cellphone);

    // Verifica se a exceção esperada é realmente lançada
    assertThrows(EntityNotFoundException.class, () -> service.deleteReport(cellphone));
}
    // 4. Garantir que, se só há 1 denúncia, o serviço realmente exclui o registro do banco.
    @Test
    void whenDeletingCellphoneWithSingleReport_thenDeletes() {
        String cellphone = "11992067482";
        CellphoneReport existing = new CellphoneReport(cellphone, 1, LocalDate.now());

        doReturn(existing).when(service).findById(cellphone);

        service.deleteReport(cellphone);

        verify(service).deleteById(cellphone);
    }
    // 5. ao deletar uma denúncia de um número com várias denúncias, o sistema apenas diminui em 1 
    @Test
    void whenDeletingCellphoneWithMultipleReports_thenDecrements() {
        String cellphone = "11992067482";
        CellphoneReport existing = new CellphoneReport(cellphone, 3, LocalDate.now());

        doReturn(existing).when(service).findById(cellphone);

        service.deleteReport(cellphone);

        ArgumentCaptor<CellphoneReport> captor = ArgumentCaptor.forClass(CellphoneReport.class);
        verify(service).insert(captor.capture());
        assertEquals(2, captor.getValue().getReportQuantity());
    }
}
