package com.transaction.banking.service;

import com.transaction.banking.domain.Transaction;
import com.transaction.banking.dto.request.TransactionRequestDTO;
import com.transaction.banking.enums.TransactionStatus;
import com.transaction.banking.exceptions.TransactionValidationException;
import com.transaction.banking.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Transaction createTransaction(TransactionRequestDTO dto) {
        log.info("Iniciando criação de transação: De {} para {} - Valor: {}",
                dto.getFromAccountId(), dto.getToAccountId(), dto.getAmount());

        var from = bankAccountService.getAccountById(UUID.fromString(dto.getFromAccountId()));
        var to = bankAccountService.getAccountById(UUID.fromString(dto.getToAccountId()));

        transactionValidator.validateForTransfer(from, to, dto.getAmount());

        from.debit(dto.getAmount());
        to.credit(dto.getAmount());

        var transaction = new Transaction();
        transaction.setId(UUID.randomUUID());
        transaction.setFromAccountId(from.getAccountId());
        transaction.setToAccountId(to.getAccountId());
        transaction.setAmount(dto.getAmount());
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setTransactionStatus(TransactionStatus.COMPLETED);

        bankAccountService.save(from);
        bankAccountService.save(to);

        var saved = transactionRepository.save(transaction);

        log.info("Transação salva com sucesso: {}", saved.getId());

        return saved;
    }

    public List<Transaction> getTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction getTransactionById(UUID id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionValidationException("Transação não encontrada com ID: " + id));
    }
}