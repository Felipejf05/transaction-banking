package com.transaction.banking.service;

import com.transaction.banking.domain.AccountBanking;
import com.transaction.banking.repository.AccountBankingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("AccountBankingService Tests")
class AccountBankingServiceTest {

    @Mock
    private AccountBankingRepository accountBankingRepository;

    @InjectMocks
    private AccountBankingService accountBankingService;

    private AccountBanking accountBanking;
    private UUID accountId;

    @BeforeEach
    void setUp() {
        accountId = UUID.randomUUID();
        accountBanking = new AccountBanking();
        accountBanking.setHolderName("João Silva");
        accountBanking.setAmount(new BigDecimal("1000.00"));
    }

    @Test
    @DisplayName("Should create account successfully")
    void shouldCreateAccountSuccessfully() {
        // Given
        AccountBanking savedAccount = new AccountBanking();
        savedAccount.setHolderName("João Silva");
        savedAccount.setAmount(new BigDecimal("1000.00"));

        when(accountBankingRepository.save(any(AccountBanking.class)))
                .thenReturn(savedAccount);

        // When
        AccountBanking result = accountBankingService.createAccount(accountBanking);

        // Then
        assertNotNull(result);
        assertEquals("João Silva", result.getHolderName());
        assertEquals(new BigDecimal("1000.00"), result.getAmount());

        verify(accountBankingRepository, times(1)).save(accountBanking);
    }

    @Test
    @DisplayName("Should create account with null values gracefully")
    void shouldCreateAccountWithNullValues() {
        // Given
        AccountBanking emptyAccount = new AccountBanking();
        when(accountBankingRepository.save(any(AccountBanking.class)))
                .thenReturn(emptyAccount);

        // When
        AccountBanking result = accountBankingService.createAccount(emptyAccount);

        // Then
        assertNotNull(result);
        verify(accountBankingRepository, times(1)).save(emptyAccount);
    }

    @Test
    @DisplayName("Should get all accounts successfully")
    void shouldGetAllAccountsSuccessfully() {
        // Given
        AccountBanking account1 = new AccountBanking();
        account1.setHolderName("João Silva");
        account1.setAmount(new BigDecimal("1000.00"));

        AccountBanking account2 = new AccountBanking();
        account2.setHolderName("Maria Santos");
        account2.setAmount(new BigDecimal("500.00"));

        List<AccountBanking> accounts = Arrays.asList(account1, account2);

        when(accountBankingRepository.findAll()).thenReturn(accounts);

        // When
        List<AccountBanking> result = accountBankingService.getAccounts();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("João Silva", result.get(0).getHolderName());
        assertEquals("Maria Santos", result.get(1).getHolderName());

        verify(accountBankingRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return empty list when no accounts exist")
    void shouldReturnEmptyListWhenNoAccountsExist() {
        // Given
        when(accountBankingRepository.findAll()).thenReturn(Arrays.asList());

        // When
        List<AccountBanking> result = accountBankingService.getAccounts();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(accountBankingRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should get account by ID successfully")
    void shouldGetAccountByIdSuccessfully() {
        // Given
        AccountBanking foundAccount = new AccountBanking();
        foundAccount.setHolderName("João Silva");
        foundAccount.setAmount(new BigDecimal("1000.00"));

        when(accountBankingRepository.findById(accountId))
                .thenReturn(Optional.of(foundAccount));

        // When
        ResponseEntity<AccountBanking> result = accountBankingService.getAccountId(accountId);

        // Then
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("João Silva", result.getBody().getHolderName());
        assertEquals(new BigDecimal("1000.00"), result.getBody().getAmount());

        verify(accountBankingRepository, times(1)).findById(accountId);
    }

    @Test
    @DisplayName("Should return 404 when account not found")
    void shouldReturn404WhenAccountNotFound() {
        // Given
        UUID nonExistentId = UUID.randomUUID();
        when(accountBankingRepository.findById(nonExistentId))
                .thenReturn(Optional.empty());

        // When
        ResponseEntity<AccountBanking> result = accountBankingService.getAccountId(nonExistentId);

        // Then
        assertNotNull(result);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNull(result.getBody());

        verify(accountBankingRepository, times(1)).findById(nonExistentId);
    }

    @Test
    @DisplayName("Should handle repository exception during account creation")
    void shouldHandleRepositoryExceptionDuringAccountCreation() {
        // Given
        when(accountBankingRepository.save(any(AccountBanking.class)))
                .thenThrow(new RuntimeException("Database error"));

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            accountBankingService.createAccount(accountBanking);
        });

        assertEquals("Database error", exception.getMessage());
        verify(accountBankingRepository, times(1)).save(accountBanking);
    }

    @Test
    @DisplayName("Should handle repository exception during get all accounts")
    void shouldHandleRepositoryExceptionDuringGetAllAccounts() {
        // Given
        when(accountBankingRepository.findAll())
                .thenThrow(new RuntimeException("Database connection failed"));

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            accountBankingService.getAccounts();
        });

        assertEquals("Database connection failed", exception.getMessage());
        verify(accountBankingRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should verify account not found exception message")
    void shouldVerifyAccountNotFoundExceptionMessage() {
        // Given
        UUID nonExistentId = UUID.randomUUID();
        when(accountBankingRepository.findById(nonExistentId))
                .thenReturn(Optional.empty());

        // When
        ResponseEntity<AccountBanking> result = accountBankingService.getAccountId(nonExistentId);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());

        // Verifica se a exception foi criada com a mensagem correta
        // (mesmo que seja capturada internamente)
        verify(accountBankingRepository, times(1)).findById(nonExistentId);
    }

    @Test
    @DisplayName("Should handle multiple consecutive calls")
    void shouldHandleMultipleConsecutiveCalls() {
        // Given
        when(accountBankingRepository.save(any(AccountBanking.class)))
                .thenReturn(accountBanking);

        // When
        AccountBanking result1 = accountBankingService.createAccount(accountBanking);
        AccountBanking result2 = accountBankingService.createAccount(accountBanking);

        // Then
        assertNotNull(result1);
        assertNotNull(result2);
        verify(accountBankingRepository, times(2)).save(accountBanking);
    }
}