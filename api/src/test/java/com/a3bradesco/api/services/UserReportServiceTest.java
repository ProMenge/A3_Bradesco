package com.a3bradesco.api.services;

import com.a3bradesco.api.dto.UserReportDTO;
import com.a3bradesco.api.entities.User;
import com.a3bradesco.api.entities.UserReport;
import com.a3bradesco.api.entities.enums.ReportType;
import com.a3bradesco.api.repositories.UserReportRepository;

import jakarta.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserReportServiceTest {

    @InjectMocks
    private UserReportService userReportService;

    @Mock
    private UserReportRepository userReportRepository;

    @Mock
    private UserService userService;

    private AutoCloseable closeable;

    @BeforeEach
    void setup() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveNewReport_ShouldSaveReport_WhenValidEmailReport() {
        // Arrange
        Long userId = 1L;
        String validEmail = "caue@email.com";
        UserReportDTO dto = new UserReportDTO();
        dto.setReportType(ReportType.EMAIL);
        dto.setReportValue(validEmail);

        User user = new User();
        user.setId(userId);

        when(userService.findById(userId)).thenReturn(user);
        when(userReportRepository.findByReporter(user)).thenReturn(Collections.emptyList());

        ArgumentCaptor<UserReport> reportCaptor = ArgumentCaptor.forClass(UserReport.class);
        when(userReportRepository.save(reportCaptor.capture())).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        UserReport result = userReportService.saveNewReport(userId, dto);

        // Assert
        assertNotNull(result);
        assertEquals(ReportType.EMAIL, result.getReportType());
        assertEquals(validEmail, result.getReportValue());
        assertEquals(user, result.getReporter());
        assertTrue(result.getReportMoment().isBefore(LocalDateTime.now().plusSeconds(1)));

        verify(userReportRepository).save(any(UserReport.class));
    }

    @Test
    void saveNewReport_ShouldThrowException_WhenReportAlreadyExists() {
        // Arrange
        Long userId = 1L;
        String validCpf = "12345678901";
        UserReportDTO dto = new UserReportDTO();
        dto.setReportType(ReportType.CPF);
        dto.setReportValue(validCpf);

        User user = new User();
        user.setId(userId);

        UserReport existingReport = new UserReport(null, user, ReportType.CPF, validCpf, LocalDateTime.now());

        when(userService.findById(userId)).thenReturn(user);
        when(userReportRepository.findByReporter(user)).thenReturn(List.of(existingReport));

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> userReportService.saveNewReport(userId, dto));

        assertEquals("Já existe uma denúncia para este CPF relacionada a este usuário.", exception.getMessage());
    }

    @Test
    void saveNewReport_ShouldThrowException_WhenValueIsInvalid() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        UserReportDTO dto = new UserReportDTO();
        dto.setReportType(ReportType.CNPJ);
        dto.setReportValue("invalidCNPJ");

        when(userService.findById(userId)).thenReturn(user);
        when(userReportRepository.findByReporter(user)).thenReturn(Collections.emptyList());

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> userReportService.saveNewReport(userId, dto));

        assertEquals("O valor enviado é incompatível com o tipo de denúncia.", exception.getMessage());
    }

    @Test
    void saveNewReport_ShouldThrowException_WhenUserHas20Reports() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        List<UserReport> reports = Collections.nCopies(20,
                new UserReport(null, user, ReportType.CPF, "12345678901", LocalDateTime.now()));

        UserReportDTO dto = new UserReportDTO();
        dto.setReportType(ReportType.EMAIL);
        dto.setReportValue("caue@email.com");

        when(userService.findById(userId)).thenReturn(user);
        when(userReportRepository.findByReporter(user)).thenReturn(reports);

        // Act & Assert
        Exception exception = assertThrows(IllegalStateException.class,
                () -> userReportService.saveNewReport(userId, dto));

        assertEquals("O usuário já possui o número máximo de 20 denúncias.", exception.getMessage());
    }

    @Test
    void findAll_ShouldReturnAllReports() {
        List<UserReport> reports = List.of(
                new UserReport(1L, new User(), ReportType.CPF, "12345678901", LocalDateTime.now()));

        when(userReportRepository.findAll()).thenReturn(reports);

        List<UserReport> result = userReportService.findAll();

        assertEquals(reports, result);
    }

    @Test
    void findById_ShouldReturnReport_WhenExists() {
        UserReport report = new UserReport(1L, new User(), ReportType.CPF, "12345678901", LocalDateTime.now());

        when(userReportRepository.findById(1L)).thenReturn(Optional.of(report));

        UserReport result = userReportService.findById(1L);

        assertEquals(report, result);
    }

    @Test
    void findById_ShouldThrowException_WhenNotFound() {
        when(userReportRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userReportService.findById(999L));
    }

    @Test
    void deleteById_ShouldCallRepository() {
        userReportService.deleteById(1L);
        verify(userReportRepository).deleteById(1L);
    }

    @Test
    void deleteReport_ShouldDeleteExistingReport() {
        UserReport report = new UserReport(1L, new User(), ReportType.CPF, "12345678901", LocalDateTime.now());

        when(userReportRepository.findById(1L)).thenReturn(Optional.of(report));

        userReportService.deleteReport(1L);

        verify(userReportRepository).deleteById(1L);
    }

}
