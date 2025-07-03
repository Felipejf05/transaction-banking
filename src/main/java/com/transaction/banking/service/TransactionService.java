package com.transaction.banking.service;

import com.transaction.banking.domain.BankAccount;
import com.transaction.banking.domain.Transaction;
import com.transaction.banking.dto.request.TransactionRequestDTO;
import com.transaction.banking.enums.TransactionStatus;
import com.transaction.banking.exceptions.TransactionValidationException;
import com.transaction.banking.kafka.producer.TransactionEventPublisherService;
import com.transaction.banking.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionValidator transactionValidator;
    private final BankAccountService bankAccountService;
    private final TransactionEventPublisherService transactionEventPublisherService;

    @Transactional
    public Transaction createTransaction(TransactionRequestDTO dto) {
        log.info("Iniciando criação de transação: De {} para {} - Valor: {}",
                dto.getFromAccountId(), dto.getToAccountId(), dto.getAmount());

        var from = bankAccountService.getAccountById(UUID.fromString(dto.getFromAccountId()));
        var to = bankAccountService.getAccountById(UUID.fromString(dto.getToAccountId()));

        transactionValidator.validateForTransfer(from, to, dto.getAmount());

        var saved = processAndSaveTransaction(from, to, dto.getAmount());

        transactionEventPublisherService.publishCreated(saved);

        return saved;

    }

    private Transaction processAndSaveTransaction(BankAccount from, BankAccount to, BigDecimal amount) {
        from.debit(amount);
        to.credit(amount);

        var transaction = new Transaction();
        transaction.setFromAccountId(from.getAccountId());
        transaction.setToAccountId(to.getAccountId());
        transaction.setAmount(amount);
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setTransactionStatus(TransactionStatus.COMPLETED);

        bankAccountService.save(from);
        bankAccountService.save(to);

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction getTransactionById(UUID id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionValidationException("Transação não encontrada com ID: " + id));
    }

}