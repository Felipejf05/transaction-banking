package com.transaction.banking.service;

import com.transaction.banking.domain.BankAccount;
import com.transaction.banking.domain.Transaction;
import com.transaction.banking.dto.request.TransactionRequestDTO;
import com.transaction.banking.enums.TransactionStatus;
import com.transaction.banking.exceptions.TransactionValidationException;
import com.transaction.banking.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TransactionServiceTest {

    private TransactionRepository transactionRepository;
    private TransactionValidator transactionValidator;
    private BankAccountService bankAccountService;
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        transactionRepository = mock(TransactionRepository.class);
        transactionValidator = mock(TransactionValidator.class);
        bankAccountService = mock(BankAccountService.class);
        transactionService = new TransactionService(
                transactionRepository,
                transactionValidator,
                bankAccountService
        );
    }

    @Test
    void testCreateTransaction_Success() {
        // Arrange
        UUID fromAccountId = UUID.randomUUID();
        UUID toAccountId = UUID.randomUUID();
        BigDecimal amount = new BigDecimal("100.00");

        TransactionRequestDTO requestDTO = new TransactionRequestDTO(
                fromAccountId.toString(),
                toAccountId.toString(),
                amount
        );

        BankAccount fromAccount = new BankAccount(fromAccountId, "Cliente A", new BigDecimal("500.00"));
        BankAccount toAccount = new BankAccount(toAccountId, "Cliente B", new BigDecimal("300.00"));

        Transaction savedTransaction = new Transaction();
        savedTransaction.setId(UUID.randomUUID());
        savedTransaction.setFromAccountId(fromAccountId);
        savedTransaction.setToAccountId(toAccountId);
        savedTransaction.setAmount(amount);
        savedTransaction.setTransactionStatus(TransactionStatus.COMPLETED);
        savedTransaction.setTimestamp(LocalDateTime.now());

        when(bankAccountService.getAccountById(fromAccountId)).thenReturn(fromAccount);
        when(bankAccountService.getAccountById(toAccountId)).thenReturn(toAccount);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(savedTransaction);

        // Act
        Transaction result = transactionService.createTransaction(requestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(savedTransaction.getId(), result.getId());
        assertEquals(amount, result.getAmount());

        verify(transactionValidator).validateForTransfer(fromAccount, toAccount, amount);
        verify(bankAccountService, times(1)).save(fromAccount);
        verify(bankAccountService, times(1)).save(toAccount);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }


    @Test
    void testGetTransactions_ReturnList() {
        // Arrange
        Transaction transaction = new Transaction();
        transaction.setId(UUID.randomUUID());
        transaction.setAmount(new BigDecimal("200.00"));
        transaction.setFromAccountId(UUID.randomUUID());
        transaction.setToAccountId(UUID.randomUUID());
        transaction.setTimestamp(LocalDateTime.now());

        when(transactionRepository.findAll()).thenReturn(List.of(transaction));

        // Act
        List<Transaction> result = transactionService.getTransactions();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(transaction.getId(), result.get(0).getId());
    }


    @Test
    void testGetTransactionById_Success() {
        // Arrange
        UUID transactionId = UUID.randomUUID();
        Transaction transaction = new Transaction();
        transaction.setId(transactionId);
        transaction.setAmount(new BigDecimal("150.00"));
        transaction.setFromAccountId(UUID.randomUUID());
        transaction.setToAccountId(UUID.randomUUID());
        transaction.setTimestamp(LocalDateTime.now());

        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(transaction));

        // Act
        Transaction result = transactionService.getTransactionById(transactionId);

        // Assert
        assertNotNull(result);
        assertEquals(transactionId, result.getId());
    }


    @Test
    void testGetTransactionById_NotFound() {
        // Arrange
        UUID transactionId = UUID.randomUUID();
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.empty());

        // Act & Assert
        TransactionValidationException exception = assertThrows(
                TransactionValidationException.class,
                () -> transactionService.getTransactionById(transactionId)
        );

        assertEquals("Transação não encontrada com ID: " + transactionId, exception.getMessage());
    }
}
