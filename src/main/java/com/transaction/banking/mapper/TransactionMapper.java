package com.transaction.banking.mapper;

import com.transaction.banking.domain.Transaction;
import com.transaction.banking.dto.request.TransactionRequestDTO;
import com.transaction.banking.dto.response.TransactionResponseDTO;
import com.transaction.banking.enums.TransactionStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class TransactionMapper {

    public Transaction toTransaction(TransactionRequestDTO transactionRequestDTO) {
        if (transactionRequestDTO == null) {
            return null;
        }

        Transaction transaction = new Transaction();
        transaction.setFromAccountId(UUID.fromString(transactionRequestDTO.getFromAccountId()));
        transaction.setToAccountId(UUID.fromString(transactionRequestDTO.getToAccountId()));
        transaction.setAmount(transactionRequestDTO.getAmount());
        transaction.setTransactionStatus(TransactionStatus.PENDING);
        transaction.setTimestamp(LocalDateTime.now());

        return transaction;
    }

    public TransactionResponseDTO doDTO(Transaction transaction) {
        if (transaction == null) {
            return null;
        }
        TransactionResponseDTO dto = new TransactionResponseDTO();

        dto.setIdTransaction(String.valueOf(transaction.getId()));
        dto.setFromAccountId(String.valueOf(transaction.getFromAccountId()));
        dto.setToAccountId(String.valueOf(transaction.getToAccountId()));
        dto.setAmount(transaction.getAmount());
        dto.setTimestamp(LocalDateTime.now());

        return dto;
    }

    public List<TransactionResponseDTO> toResponseDTOList(List<Transaction> transactions) {
        if (transactions == null || transactions.isEmpty()) {
            return Collections.emptyList();
        }

        return transactions.stream().filter(transaction -> transaction != null)
                .map(this::doDTO)
                .collect(Collectors.toList());
    }

}
