package com.transaction.banking.service;

import com.transaction.banking.domain.Transaction;
import com.transaction.banking.enums.TransactionStatus;
import com.transaction.banking.exceptions.AccountNotFoundException;
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

    @Transactional
    public Transaction createTransaction(Transaction transaction) {
        log.info("Iniciando a criação de transação: De {} para {} - Valor: {}",
                transaction.getFromAccountId(), transaction.getToAccountId(), transaction.getAmount());

        var from = bankAccountService.getAccountById(transaction.getFromAccountId());
        var to = bankAccountService.getAccountById(transaction.getToAccountId());

        log.info("Contas encontradas  - De: {} (Saldo: {}), Para: {} (Saldo {})",
                from.getAccountId(), from.getAmount(), to.getAccountId(), to.getAmount());

        transactionValidator.validateForTransfer(transaction, from, to);
        log.info("Validação de transferências  concluída com sucesso");

        from.debit(transaction.getAmount());
        to.credit(transaction.getAmount());
        log.info("Débito e crédito realizados: De {} (Novo Saldo: {}), Para: {} (Novo saldo: {})",
                from.getAccountId(), from.getAmount(), to.getAccountId(), to.getAmount());

        transaction.setId(UUID.randomUUID());
        transaction.setTransactionStatus(TransactionStatus.COMPLETED);
        transaction.setTimestamp(LocalDateTime.now());

        bankAccountService.save(from);
        bankAccountService.save(to);
        log.info("Contas atualizadas no banco");

        Transaction saveTransaction = transactionRepository.save(transaction);
        log.info("Transação salva com sucesso com o ID: {}", saveTransaction.getId());

        return saveTransaction;
    }

    public List<Transaction> getTransactions() {
        log.info("Buscando todas as transações");
        List<Transaction> transactions  = transactionRepository.findAll();
        log.info("Total de transações encontradas: {}", transactions.size());

        return transactions;
    }

    public BigDecimal getTransactionId(UUID id) {
        log.info("Buscando valor da transação com ID: {}", id);
            Transaction transaction = transactionRepository.findById(id)
                    .orElseThrow(() -> {
                        log.warn("Transação não encontrada com ID: {}", id);
                        return new AccountNotFoundException("Transação não encontrada com ID: " + id);
                    });

            log.info("Valor da transação ID {} é {}", id, transaction.getAmount());

            return transaction.getAmount();
    }
}
