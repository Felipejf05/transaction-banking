package com.transaction.banking.service;

import com.transaction.banking.domain.BankAccount;
import com.transaction.banking.domain.Transaction;
import com.transaction.banking.enums.TransactionStatus;
import com.transaction.banking.exceptions.TransactionValidationException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TransactionValidator {

    public void validateForTransfer(BankAccount from, BankAccount to, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new TransactionValidationException("O valor da transferência deve ser maior que zero");
        }
        if (from.getAccountId().equals(to.getAccountId())) {
            throw new TransactionValidationException("Conta de origem e destino devem ser diferentes");
        }
        if (from.getAmount().compareTo(amount) < 0) {
            throw new TransactionValidationException("Saldo insuficiente para realizar a transferência");
        }
    }

    public void validateForReverse(Transaction originalTransaction){
        if(originalTransaction.getTransactionStatus() != TransactionStatus.COMPLETED){
            throw new TransactionValidationException("Só transações completadas podem ser revertidas");
        }
        if (originalTransaction.getReversalTransactionId() != null)
            throw new TransactionValidationException("Transação já foi revertida");
    }

}
