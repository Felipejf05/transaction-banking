package com.transaction.banking.service;

import com.transaction.banking.domain.BankAccount;
import com.transaction.banking.domain.Transaction;
import com.transaction.banking.enums.TransactionStatus;
import com.transaction.banking.exceptions.AccountNotFoundException;
import com.transaction.banking.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionValidator transactionValidator;

    @Mock
    private BankAccountService bankAccountService;

    private BankAccount fromAccount;
    private BankAccount toAccount;
    private Transaction transaction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        fromAccount = new BankAccount(UUID.randomUUID(), "Felipe", BigDecimal.valueOf(500));
        toAccount = new BankAccount(UUID.randomUUID(), "João", BigDecimal.valueOf(100));
        transaction = new Transaction(
                null,
                fromAccount.getAccountId(),
                toAccount.getAccountId(),
                BigDecimal.valueOf(200),
                null,
                null,
                null
        );
    }

    @Test
    void testCreateTransaction_Success() {
        when(bankAccountService.getAccountById(fromAccount.getAccountId())).thenReturn(fromAccount);
        when(bankAccountService.getAccountById(toAccount.getAccountId())).thenReturn(toAccount);
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(i -> i.getArgument(0));

        Transaction saved = transactionService.createTransaction(transaction);

        assertNotNull(saved.getId());
        assertEquals(TransactionStatus.COMPLETED, saved.getTransactionStatus());
        assertNotNull(saved.getTimestamp());

        assertEquals(BigDecimal.valueOf(300), fromAccount.getAmount());
        assertEquals(BigDecimal.valueOf(300), toAccount.getAmount());

        verify(transactionValidator).validateForTransfer(transaction, fromAccount, toAccount);
        verify(bankAccountService).save(fromAccount);
        verify(bankAccountService).save(toAccount);
        verify(transactionRepository).save(saved);
    }

    @Test
    void testGetTransactions_ShouldReturnList() {
        List<Transaction> mockTransactions = List.of(transaction);
        when(transactionRepository.findAll()).thenReturn(mockTransactions);

        List<Transaction> result = transactionService.getTransactions();

        assertEquals(1, result.size());
        verify(transactionRepository).findAll();
    }

    @Test
    void testGetTransactionId_ReturnsCorrectValue() {
        UUID id = UUID.randomUUID();
        Transaction t = new Transaction(id, fromAccount.getAccountId(), toAccount.getAccountId(),
                BigDecimal.valueOf(123), TransactionStatus.COMPLETED, LocalDateTime.now(), null);

        when(transactionRepository.findById(id)).thenReturn(Optional.of(t));

        BigDecimal result = transactionService.getTransactionId(id);

        assertEquals(BigDecimal.valueOf(123), result);
        verify(transactionRepository).findById(id);
    }

    @Test
    void testGetTransactionId_NotFound_ThrowsException() {
        UUID id = UUID.randomUUID();
        when(transactionRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> transactionService.getTransactionId(id));
        verify(transactionRepository).findById(id);
    }
}
