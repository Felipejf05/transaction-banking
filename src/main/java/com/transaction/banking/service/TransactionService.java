package com.transaction.banking.service;

import com.transaction.banking.domain.Transaction;
import com.transaction.banking.dto.request.TransactionRequestDTO;
import com.transaction.banking.dto.response.TransactionResponseDTO;
import com.transaction.banking.enums.TransactionStatus;
import com.transaction.banking.exceptions.TransactionValidationException;
import com.transaction.banking.mapper.TransactionMapper;
import com.transaction.banking.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionValidator transactionValidator;
    private final BankAccountService bankAccountService;
    private final TransactionMapper transactionMapper;
    @Transactional
    public TransactionResponseDTO createTransaction(TransactionRequestDTO dto) {
        log.info("Iniciando a criação de transação: De {} para {} - Valor: {}",
                dto.getFromAccountId(), dto.getToAccountId(), dto.getAmount());

        var from = bankAccountService.getAccountById(UUID.fromString(dto.getFromAccountId()));
        var to = bankAccountService.getAccountById(UUID.fromString(dto.getToAccountId()));

        var transaction = transactionMapper.toTransaction(dto);

        log.info("Contas encontradas  - De: {} (Saldo: {}), Para: {} (Saldo {})",
                from.getAccountId(), from.getAmount(), to.getAccountId(), to.getAmount());

        transactionValidator.validateForTransfer(transaction, from, to);
        log.info("Validação concluída");

        from.debit(dto.getAmount());
        to.credit(dto.getAmount());


        transaction.setId(UUID.randomUUID());
        transaction.setTransactionStatus(TransactionStatus.COMPLETED);
        transaction.setTimestamp(LocalDateTime.now());

        bankAccountService.save(from);
        bankAccountService.save(to);

        Transaction saveTransaction = transactionRepository.save(transaction);
        log.info("Transação salva com sucesso com o ID: {}", saveTransaction.getId());

        return transactionMapper.doDTO(saveTransaction);
    }

    public List<TransactionResponseDTO> getTransactions() {
        log.info("Buscando todas as transações");
        List<Transaction> transactions  = transactionRepository.findAll();
        log.info("Total de transações encontradas: {}", transactions.size());

        return transactions.stream()
                .map(transactionMapper::doDTO)
                .collect(Collectors.toList());
    }

    public TransactionResponseDTO getTransactionId(UUID idTransaction) {
        log.info("Buscando valor da transação com ID: {}", idTransaction);
            Transaction transaction = transactionRepository.findById(idTransaction)
                    .orElseThrow(() -> {
                        log.warn("Transação não encontrada com ID: {}", idTransaction);
                        return new TransactionValidationException("Transação não encontrada com ID: " + idTransaction);
                    });

            log.info("Valor da transação ID {} é {}", idTransaction, transaction.getAmount());

            return transactionMapper.doDTO(transaction);
    }
}
