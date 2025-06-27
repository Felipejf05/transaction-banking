package com.transaction.banking.service;

import com.transaction.banking.domain.BankAccount;
import com.transaction.banking.domain.Transaction;
import com.transaction.banking.enums.TransactionStatus;
import com.transaction.banking.exceptions.TransactionValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TransactionValidatorTest {

    private TransactionValidator validator;
    private BankAccount fromAccount;
    private BankAccount toAccount;
    private Transaction transaction;

    @BeforeEach
    void setUp() {
        validator = new TransactionValidator();
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
    void testValidateForTransfer_Success() {
        assertDoesNotThrow(() -> validator.validateForTransfer(fromAccount, toAccount, transaction.getAmount()));
    }

    @Test
    void testValidateForTransfer_NullAmount_ThrowsException() {
        transaction.setAmount(null);
        TransactionValidationException exception = assertThrows(TransactionValidationException.class,
                () -> validator.validateForTransfer(fromAccount, toAccount, transaction.getAmount()));
        assertEquals("O valor da transferência deve ser maior que zero", exception.getMessage());
    }

    @Test
    void testValidateForTransfer_ZeroAmount_ThrowsException() {
        transaction.setAmount(BigDecimal.ZERO);
        TransactionValidationException exception = assertThrows(TransactionValidationException.class,
                () -> validator.validateForTransfer(fromAccount, toAccount, transaction.getAmount()));
        assertEquals("O valor da transferência deve ser maior que zero", exception.getMessage());
    }

    @Test
    void testValidateForTransfer_NegativeAmount_ThrowsException() {
        transaction.setAmount(BigDecimal.valueOf(-1));
        TransactionValidationException exception = assertThrows(TransactionValidationException.class,
                () -> validator.validateForTransfer(fromAccount, toAccount, transaction.getAmount()));
        assertEquals("O valor da transferência deve ser maior que zero", exception.getMessage());
    }

    @Test
    void testValidateForTransfer_SameAccount_ThrowsException() {
        TransactionValidationException exception = assertThrows(TransactionValidationException.class,
                () -> validator.validateForTransfer(fromAccount, fromAccount, transaction.getAmount())); // <-- Aqui usa fromAccount duas vezes
        assertEquals("Conta de origem e destino devem ser diferentes", exception.getMessage());
    }

    @Test
    void testValidateForReverse_Success() {
        Transaction originalTransaction = new Transaction();
        originalTransaction.setTransactionStatus(TransactionStatus.COMPLETED);
        originalTransaction.setReversalTransactionId(null);
        assertDoesNotThrow(() -> validator.validateForReverse(originalTransaction));
    }

    @Test
    void testValidateForReverse_NotCompletedStatus_ThrowsException() {
        Transaction originalTransaction = new Transaction();
        originalTransaction.setTransactionStatus(TransactionStatus.PENDING);
        originalTransaction.setReversalTransactionId(null);
        TransactionValidationException exception = assertThrows(TransactionValidationException.class,
                () -> validator.validateForReverse(originalTransaction));
        assertEquals("Só transações completadas podem ser revertidas", exception.getMessage());
    }

    @Test
    void testValidateForReverse_AlreadyReversed_ThrowsException() {
        Transaction originalTransaction = new Transaction();
        originalTransaction.setTransactionStatus(TransactionStatus.COMPLETED);
        originalTransaction.setReversalTransactionId(UUID.randomUUID());
        TransactionValidationException exception = assertThrows(TransactionValidationException.class,
                () -> validator.validateForReverse(originalTransaction));
        assertEquals("Transação já foi revertida", exception.getMessage());
    }
}
