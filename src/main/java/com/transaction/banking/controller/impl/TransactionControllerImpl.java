package com.transaction.banking.controller.impl;

import com.transaction.banking.controller.TransactionController;
import com.transaction.banking.dto.request.TransactionRequestDTO;
import com.transaction.banking.dto.response.TransactionListResponse;
import com.transaction.banking.dto.response.TransactionResponseDTO;
import com.transaction.banking.mapper.TransactionMapper;
import com.transaction.banking.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class TransactionControllerImpl implements TransactionController {

    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    @Override
    public ResponseEntity<TransactionResponseDTO> createTransaction(TransactionRequestDTO dto) {
        var transaction = transactionService.createTransaction(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionMapper.toResponseDTO(transaction));
    }

    @Override
    public ResponseEntity<TransactionListResponse> getTransactions() {
        var transactions = transactionService.getTransactions();
        var responseList = transactions.stream()
                .map(transactionMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new TransactionListResponse(responseList));
    }

    @Override
    public ResponseEntity<TransactionResponseDTO> getTransactionId(UUID id) {
        var transaction = transactionService.getTransactionById(id);
        return ResponseEntity.ok(transactionMapper.toResponseDTO(transaction));
    }
}