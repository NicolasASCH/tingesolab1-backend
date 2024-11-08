package nasch.prestabanco_backend.services;

import nasch.prestabanco_backend.entities.LoanEntity;
import nasch.prestabanco_backend.repositories.LoanRepository;
import nasch.prestabanco_backend.services.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoanServiceTest {

    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private LoanService loanService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllLoans_NoLoans_ReturnsEmptyList() {
        when(loanRepository.findAll()).thenReturn(new ArrayList<>());
        ArrayList<LoanEntity> result = loanService.getAllLoans();
        assertTrue(result.isEmpty());
    }

    @Test
    public void getAllLoans_OneLoan_ReturnsSingleLoanList() {
        LoanEntity loan = new LoanEntity(1L, "12345678-9", "Mortgage", 50000000, 20000000, 240, 3.5f, 1000000, 5, 35, "Revisión inicial", new byte[]{}, new byte[]{}, new byte[]{}, new byte[]{});
        when(loanRepository.findAll()).thenReturn(new ArrayList<>(List.of(loan)));
        ArrayList<LoanEntity> result = loanService.getAllLoans();
        assertEquals(1, result.size());
        assertEquals("12345678-9", result.get(0).getRut());
    }

    @Test
    public void getAllLoans_MultipleLoans_ReturnsAllLoans() {
        LoanEntity loan1 = new LoanEntity(1L, "12345678-9", "Mortgage", 50000000, 20000000, 240, 3.5f, 1000000, 5, 35, "Revisión inicial", new byte[]{}, new byte[]{}, new byte[]{}, new byte[]{});
        LoanEntity loan2 = new LoanEntity(2L, "98765432-1", "Personal", 0, 5000000, 60, 4.0f, 800000, 3, 28, "Revisión inicial", new byte[]{}, new byte[]{}, new byte[]{}, new byte[]{});
        when(loanRepository.findAll()).thenReturn(new ArrayList<>(List.of(loan1, loan2)));
        ArrayList<LoanEntity> result = loanService.getAllLoans();
        assertEquals(2, result.size());
        assertEquals("12345678-9", result.get(0).getRut());
        assertEquals("98765432-1", result.get(1).getRut());
    }

    @Test
    public void getAllLoans_ValidateLoanFields_ReturnsCorrectFields() {
        LoanEntity loan = new LoanEntity(1L, "12345678-9", "Mortgage", 50000000, 20000000, 240, 3.5f, 1000000, 5, 35, "Revisión inicial", new byte[]{1}, new byte[]{2}, new byte[]{3}, new byte[]{4});
        when(loanRepository.findAll()).thenReturn(new ArrayList<>(List.of(loan)));
        ArrayList<LoanEntity> result = loanService.getAllLoans();
        assertEquals(1L, result.get(0).getId());
        assertEquals("Mortgage", result.get(0).getType());
        assertEquals(50000000, result.get(0).getProperty_price());
        assertEquals(20000000, result.get(0).getAmount());
    }

    @Test
    public void getAllLoans_DocumentFields_ReturnsCorrectDocuments() {
        LoanEntity loan = new LoanEntity(1L, "12345678-9", "Mortgage", 50000000, 20000000, 240, 3.5f, 1000000, 5, 35, "Revisión inicial", new byte[]{1}, new byte[]{2}, new byte[]{3}, new byte[]{4});
        when(loanRepository.findAll()).thenReturn(new ArrayList<>(List.of(loan)));
        ArrayList<LoanEntity> result = loanService.getAllLoans();
        assertArrayEquals(new byte[]{1}, result.get(0).getDocument1());
        assertArrayEquals(new byte[]{2}, result.get(0).getDocument2());
    }

    @Test
    public void getAllLoans_NullRepositoryResponse_ReturnsNull() {
        when(loanRepository.findAll()).thenReturn(null);
        ArrayList<LoanEntity> result = loanService.getAllLoans();
        assertNull(result);
    }

    @Test
    public void getByRut_ExistingRut_ReturnsLoan() {
        LoanEntity loan = new LoanEntity(1L, "12345678-9", "Mortgage", 50000000, 20000000, 240, 3.5f, 1000000, 5, 35, "Revisión inicial", new byte[]{}, new byte[]{}, new byte[]{}, new byte[]{});
        when(loanRepository.findByRut("12345678-9")).thenReturn(loan);
        LoanEntity result = loanService.getByRut("12345678-9");
        assertNotNull(result);
        assertEquals("12345678-9", result.getRut());
    }

    @Test
    public void getByRut_NonExistingRut_ReturnsNull() {
        when(loanRepository.findByRut("98765432-1")).thenReturn(null);
        LoanEntity result = loanService.getByRut("98765432-1");
        assertNull(result);
    }

    @Test
    public void getByRut_ValidateLoanFields_ReturnsCorrectFields() {
        LoanEntity loan = new LoanEntity(1L, "12345678-9", "Mortgage", 50000000, 20000000, 240, 3.5f, 1000000, 5, 35, "Revisión inicial", new byte[]{1}, new byte[]{2}, new byte[]{3}, new byte[]{4});
        when(loanRepository.findByRut("12345678-9")).thenReturn(loan);
        LoanEntity result = loanService.getByRut("12345678-9");
        assertEquals(1L, result.getId());
        assertEquals("Mortgage", result.getType());
        assertEquals(50000000, result.getProperty_price());
        assertEquals(20000000, result.getAmount());
    }

    @Test
    public void getByRut_DocumentFields_ReturnsCorrectDocuments() {
        LoanEntity loan = new LoanEntity(1L, "12345678-9", "Mortgage", 50000000, 20000000, 240, 3.5f, 1000000, 5, 35, "Revisión inicial", new byte[]{1}, new byte[]{2}, new byte[]{3}, new byte[]{4});
        when(loanRepository.findByRut("12345678-9")).thenReturn(loan);
        LoanEntity result = loanService.getByRut("12345678-9");
        assertArrayEquals(new byte[]{1}, result.getDocument1());
        assertArrayEquals(new byte[]{2}, result.getDocument2());
    }

    @Test
    public void getByRut_NullRut_ReturnsNull() {
        when(loanRepository.findByRut(null)).thenReturn(null);
        LoanEntity result = loanService.getByRut(null);
        assertNull(result);
    }

    @Test
    public void getByRut_SpecificState_ReturnsLoanWithState() {
        LoanEntity loan = new LoanEntity(1L, "12345678-9", "Mortgage", 50000000, 20000000, 240, 3.5f, 1000000, 5, 35, "Revisión inicial", new byte[]{}, new byte[]{}, new byte[]{}, new byte[]{});
        when(loanRepository.findByRut("12345678-9")).thenReturn(loan);
        LoanEntity result = loanService.getByRut("12345678-9");
        assertNotNull(result);
        assertEquals("Revisión inicial", result.getState());
    }

    @Test
    public void getByState_ExistingState_ReturnsLoan() {
        LoanEntity loan = new LoanEntity(1L, "12345678-9", "Mortgage", 50000000, 20000000, 240, 3.5f, 1000000, 5, 35, "En Revisión", new byte[]{}, new byte[]{}, new byte[]{}, new byte[]{});
        when(loanRepository.findByState("En Revisión")).thenReturn(loan);
        LoanEntity result = loanService.getByState("En Revisión");
        assertNotNull(result);
        assertEquals("En Revisión", result.getState());
    }

    @Test
    public void getByState_NonExistingState_ReturnsNull() {
        when(loanRepository.findByState("Aprobado")).thenReturn(null);
        LoanEntity result = loanService.getByState("Aprobado");
        assertNull(result);
    }

    @Test
    public void getByState_ValidateLoanFields_ReturnsCorrectFields() {
        LoanEntity loan = new LoanEntity(1L, "12345678-9", "Mortgage", 50000000, 20000000, 240, 3.5f, 1000000, 5, 35, "Pendiente de Documentación", new byte[]{1}, new byte[]{2}, new byte[]{3}, new byte[]{4});
        when(loanRepository.findByState("Pendiente de Documentación")).thenReturn(loan);
        LoanEntity result = loanService.getByState("Pendiente de Documentación");
        assertEquals(1L, result.getId());
        assertEquals("Mortgage", result.getType());
        assertEquals(50000000, result.getProperty_price());
        assertEquals(20000000, result.getAmount());
    }

    @Test
    public void getByState_DocumentFields_ReturnsCorrectDocuments() {
        LoanEntity loan = new LoanEntity(1L, "12345678-9", "Mortgage", 50000000, 20000000, 240, 3.5f, 1000000, 5, 35, "En Evaluación", new byte[]{1}, new byte[]{2}, new byte[]{3}, new byte[]{4});
        when(loanRepository.findByState("En Evaluación")).thenReturn(loan);
        LoanEntity result = loanService.getByState("En Evaluación");
        assertArrayEquals(new byte[]{1}, result.getDocument1());
        assertArrayEquals(new byte[]{2}, result.getDocument2());
    }

    @Test
    public void getByState_NullState_ReturnsNull() {
        when(loanRepository.findByState(null)).thenReturn(null);
        LoanEntity result = loanService.getByState(null);
        assertNull(result);
    }

    @Test
    public void getByState_SpecificStateAndRut_ReturnsLoanWithCorrectRut() {
        LoanEntity loan = new LoanEntity(1L, "98765432-1", "Personal", 0, 5000000, 60, 4.0f, 800000, 3, 28, "Aprobada", new byte[]{}, new byte[]{}, new byte[]{}, new byte[]{});
        when(loanRepository.findByState("Aprobada")).thenReturn(loan);
        LoanEntity result = loanService.getByState("Aprobada");
        assertNotNull(result);
        assertEquals("98765432-1", result.getRut());
        assertEquals("Aprobada", result.getState());
    }

    @Test
    public void saveLoan_ValidData_SavesLoan() {
        LoanEntity expectedLoan = new LoanEntity(1L, "12345678-9", "Mortgage", 50000000, 20000000, 240, 3.5f, 1000000, 5, 35, "En Revisión", new byte[]{1}, new byte[]{2}, new byte[]{3}, new byte[]{4});
        when(loanRepository.save(expectedLoan)).thenReturn(expectedLoan);

        LoanEntity result = loanService.saveLoan(1L, "12345678-9", "Mortgage", 50000000, 20000000, 240, 3.5f, 1000000, 5, 35, "En Revisión", new byte[]{1}, new byte[]{2}, new byte[]{3}, new byte[]{4});

        assertNotNull(result);
        assertEquals("12345678-9", result.getRut());
        assertEquals("Mortgage", result.getType());
        assertEquals(50000000, result.getProperty_price());
    }

    @Test
    public void saveLoan_NoDocuments_SavesLoanWithoutDocuments() {
        LoanEntity expectedLoan = new LoanEntity(2L, "98765432-1", "Personal", 0, 5000000, 36, 2.5f, 750000, 3, 30, "Aprobada", null, null, null, null);
        when(loanRepository.save(expectedLoan)).thenReturn(expectedLoan);

        LoanEntity result = loanService.saveLoan(2L, "98765432-1", "Personal", 0, 5000000, 36, 2.5f, 750000, 3, 30, "Aprobada", null, null, null, null);

        assertNotNull(result);
        assertEquals("98765432-1", result.getRut());
        assertNull(result.getDocument1());
        assertNull(result.getDocument2());
    }

    @Test
    public void saveLoan_PartialDocuments_SavesLoanWithPartialDocuments() {
        LoanEntity expectedLoan = new LoanEntity(3L, "24681357-0", "Auto", 10000000, 3000000, 24, 5.0f, 500000, 2, 40, "Pendiente de Documentación", new byte[]{1}, null, new byte[]{3}, null);
        when(loanRepository.save(expectedLoan)).thenReturn(expectedLoan);

        LoanEntity result = loanService.saveLoan(3L, "24681357-0", "Auto", 10000000, 3000000, 24, 5.0f, 500000, 2, 40, "Pendiente de Documentación", new byte[]{1}, null, new byte[]{3}, null);

        assertNotNull(result);
        assertArrayEquals(new byte[]{1}, result.getDocument1());
        assertNull(result.getDocument2());
        assertArrayEquals(new byte[]{3}, result.getDocument3());
    }

    @Test
    public void saveLoan_ValidateFields_SavesCorrectFields() {
        LoanEntity expectedLoan = new LoanEntity(4L, "12345678-9", "Home Improvement", 8000000, 3000000, 12, 4.5f, 1200000, 10, 28, "En Evaluación", new byte[]{1}, new byte[]{2}, new byte[]{3}, new byte[]{4});
        when(loanRepository.save(expectedLoan)).thenReturn(expectedLoan);

        LoanEntity result = loanService.saveLoan(4L, "12345678-9", "Home Improvement", 8000000, 3000000, 12, 4.5f, 1200000, 10, 28, "En Evaluación", new byte[]{1}, new byte[]{2}, new byte[]{3}, new byte[]{4});

        assertEquals("Home Improvement", result.getType());
        assertEquals(8000000, result.getProperty_price());
        assertEquals(3000000, result.getAmount());
        assertEquals(12, result.getTerm());
    }

    @Test
    public void saveLoan_NullId_SavesNewLoan() {
        LoanEntity expectedLoan = new LoanEntity(null, "12312312-3", "Business", 0, 10000000, 48, 6.5f, 2000000, 7, 45, "En Revisión", new byte[]{1}, null, null, null);
        when(loanRepository.save(expectedLoan)).thenReturn(expectedLoan);

        LoanEntity result = loanService.saveLoan(null, "12312312-3", "Business", 0, 10000000, 48, 6.5f, 2000000, 7, 45, "En Revisión", new byte[]{1}, null, null, null);

        assertNotNull(result);
        assertNull(result.getId());
        assertEquals("Business", result.getType());
    }

    @Test
    public void SaveLoan_WithZeroTerm_SavesNewLoan() {
        LoanEntity loan = new LoanEntity(3L, "12345678-9", "Mortgage", 30000000, 10000000, 0, 3.5f, 1500000, 36, 30, "APPROVED", new byte[]{1}, new byte[]{2}, new byte[]{3}, new byte[]{4});
        when(loanRepository.save(any(LoanEntity.class))).thenReturn(loan);

        LoanEntity result = loanService.saveLoan(3L, "12345678-9", "Mortgage", 30000000, 10000000, 0, 3.5f, 1500000, 36, 30, "APPROVED", new byte[]{1}, new byte[]{2}, new byte[]{3}, new byte[]{4});

        assertNotNull(result);
        assertEquals(0, result.getTerm());
        verify(loanRepository, times(1)).save(any(LoanEntity.class));
    }

    @Test
    public void updateLoan_ValidLoan_ReturnsUpdatedLoan() {
        LoanEntity loan = new LoanEntity();
        when(loanRepository.save(any(LoanEntity.class))).thenReturn(loan);

        LoanEntity result = loanService.updateLoan(null, null, null, 0, 0, 0, 0.0f, 0, 0, 0, null, null, null, null, null);

        assertNotNull(result);
        verify(loanRepository, times(1)).save(any(LoanEntity.class));
    }

    @Test
    public void updateLoan_WithNegativeAmount_ReturnsUpdatedLoan() {
        LoanEntity loan = new LoanEntity();
        when(loanRepository.save(any(LoanEntity.class))).thenReturn(loan);

        LoanEntity result = loanService.updateLoan(null, null, null, 0, -1, 0, 0.0f, 0, 0, 0, null, null, null, null, null);

        assertNotNull(result);
        verify(loanRepository, times(1)).save(any(LoanEntity.class));
    }

    @Test
    public void updateLoan_WithHighInterestRate_ReturnsUpdatedLoan() {
        LoanEntity loan = new LoanEntity();
        when(loanRepository.save(any(LoanEntity.class))).thenReturn(loan);

        LoanEntity result = loanService.updateLoan(null, null, null, 0, 0, 0, 100.0f, 0, 0, 0, null, null, null, null, null);

        assertNotNull(result);
        verify(loanRepository, times(1)).save(any(LoanEntity.class));
    }

    @Test
    public void updateLoan_WithNullDocuments_ReturnsUpdatedLoan() {
        LoanEntity loan = new LoanEntity();
        when(loanRepository.save(any(LoanEntity.class))).thenReturn(loan);

        LoanEntity result = loanService.updateLoan(null, null, null, 0, 0, 0, 0.0f, 0, 0, 0, null, null, null, null, null);

        assertNotNull(result);
        verify(loanRepository, times(1)).save(any(LoanEntity.class));
    }

    @Test
    public void updateLoan_WithMinimumAge_ReturnsUpdatedLoan() {
        LoanEntity loan = new LoanEntity();
        when(loanRepository.save(any(LoanEntity.class))).thenReturn(loan);

        LoanEntity result = loanService.updateLoan(null, null, null, 0, 0, 0, 0.0f, 0, 0, 18, null, null, null, null, null);

        assertNotNull(result);
        verify(loanRepository, times(1)).save(any(LoanEntity.class));
    }

    @Test
    public void updateLoan_WithLongTerm_ReturnsUpdatedLoan() {
        LoanEntity loan = new LoanEntity();
        when(loanRepository.save(any(LoanEntity.class))).thenReturn(loan);

        LoanEntity result = loanService.updateLoan(null, null, null, 0, 0, Integer.MAX_VALUE, 0.0f, 0, 0, 0, null, null, null, null, null);

        assertNotNull(result);
        verify(loanRepository, times(1)).save(any(LoanEntity.class));
    }

    @Test
    public void deleteLoan_ExistingId_ReturnsTrue() throws Exception {
        doNothing().when(loanRepository).deleteById(anyLong());

        boolean result = loanService.deleteLoan(1L);

        assertTrue(result);
        verify(loanRepository, times(1)).deleteById(1L);
    }

    @Test
    public void deleteLoan_ValidId_DeletionSuccessful_ReturnsTrue() throws Exception {
        Long validId = 1L;
        doNothing().when(loanRepository).deleteById(validId);

        boolean result = loanService.deleteLoan(validId);

        assertTrue(result);
        verify(loanRepository, times(1)).deleteById(validId);
    }

    @Test
    public void deleteLoan_NonExistingId_ThrowsException() {
        doThrow(new RuntimeException("Entity not found")).when(loanRepository).deleteById(anyLong());

        Exception exception = assertThrows(Exception.class, () -> loanService.deleteLoan(999L));

        assertEquals("Entity not found", exception.getMessage());
        verify(loanRepository, times(1)).deleteById(999L);
    }

    @Test
    public void deleteLoan_RepositoryThrowsException_ThrowsException() {
        doThrow(new RuntimeException("Database error")).when(loanRepository).deleteById(anyLong());

        Exception exception = assertThrows(Exception.class, () -> loanService.deleteLoan(1L));

        assertEquals("Database error", exception.getMessage());
        verify(loanRepository, times(1)).deleteById(1L);
    }

    @Test
    public void deleteLoan_MultipleDeletions_VerifiesRepositoryInteraction() throws Exception {
        doNothing().when(loanRepository).deleteById(anyLong());

        loanService.deleteLoan(1L);
        loanService.deleteLoan(2L);

        verify(loanRepository, times(1)).deleteById(1L);
        verify(loanRepository, times(1)).deleteById(2L);
    }

    @Test
    public void deleteLoan_InvalidId_ThrowsException() {
        doThrow(new RuntimeException("Invalid ID")).when(loanRepository).deleteById(anyLong());

        Exception exception = assertThrows(Exception.class, () -> loanService.deleteLoan(-1L));

        assertEquals("Invalid ID", exception.getMessage());
        verify(loanRepository, times(1)).deleteById(-1L);
    }

    @Test
    public void mortgageCreditSimulation_StandardValues_ReturnsCorrectMonthlyPayment() {
        double result = loanService.mortgageCreditSimulation(2000000, 5.0f, 30);
        assertEquals(10736.38, result, 0.01);
    }

    @Test
    public void mortgageCreditSimulation_VeryLowInterestRate_ApproximatesPrincipalDividedByTerm() {
        double result = loanService.mortgageCreditSimulation(2000000, 0.0001f, 30);
        assertEquals(3883, result, 1.0);
    }

    @Test
    public void mortgageCreditSimulation_ShortTermHighInterest_ReturnsHigherMonthlyPayment() {
        double result = loanService.mortgageCreditSimulation(2000000, 10.0f, 5);
        assertTrue(result > 40000);
    }

    @Test
    public void mortgageCreditSimulation_LongTermLowInterest_ReturnsLowerMonthlyPayment() {
        double result = loanService.mortgageCreditSimulation(2000000, 3.0f, 40);
        assertTrue(result < 8000);
    }

    @Test
    public void mortgageCreditSimulation_MinimumAmount_ReturnsCorrectMonthlyPayment() {
        double result = loanService.mortgageCreditSimulation(1, 5.0f, 30);
        assertTrue(result > 0);
    }

    @Test
    public void mortgageCreditSimulation_NegativeInterestRate_ReturnsValidPayment() {
        double result = loanService.mortgageCreditSimulation(2000000, -5.0f, 30);
        assertTrue(result > 0);
    }

    @Test
    public void totalCostCalculation_StandardValues_ReturnsCorrectTotalCost() {
        double result = loanService.totalCostCalculation(2000000, 5.0f, 30, 0.01f, 0.005f);
        assertEquals(40736.37, result, 0.01);
    }

    @Test
    public void totalCostCalculation_WithDesgravament_ReturnsTotalCostIncludingDesgravament() {
        double result = loanService.totalCostCalculation(2000000, 5.0f, 30, 0.01f, 0.005f, 1000);
        assertEquals(41736.37, result, 0.01);
    }

    @Test
    public void totalCostCalculation_WithMultipleSecure_ReturnsCorrectTotalCost() {
        double result = loanService.totalCostCalculation(2000000, 5.0f, 30, 0.01f, 0.005f, 1000, 2000, 1500);
        assertEquals(45236.37, result, 0.01);
    }

    @Test
    public void totalCostCalculation_WithZeroInterestRate_ReturnsNaN() {
        double result = loanService.totalCostCalculation(2000000, 0.0f, 30, 0.01f, 0.005f);
        assertTrue(Double.isNaN(result), "Expected the result to be NaN when interest rate is 0.");
    }

    @Test
    public void totalCostCalculation_WithZeroDesgravament_ReturnsTotalCostExcludingDesgravament() {
        double result = loanService.totalCostCalculation(2000000, 5.0f, 30, 0.0f, 0.005f);
        assertEquals(20736.37, result, 0.01);
    }

    @Test
    public void totalCostCalculation_WithNegativeInterestRate_ReturnsValidTotalCost() {
        double result = loanService.totalCostCalculation(2000000, -5.0f, 30, 0.01f, 0.005f);
        assertTrue(result > 0, "Expected a valid total cost even with negative interest rate");
    }
}
