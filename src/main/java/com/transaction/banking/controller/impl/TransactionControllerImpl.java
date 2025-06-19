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

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class TransactionControllerImpl implements TransactionController {

    private final TransactionMapper transactionMapper;
    private final TransactionService transactionService;
    @Override
    public ResponseEntity<TransactionResponseDTO> createTransaction(TransactionRequestDTO transactionRequestDTO) {
        TransactionResponseDTO saveTransaction = transactionService.createTransaction(transactionRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveTransaction);
    }

    @Override
    public ResponseEntity<TransactionListResponse> getTransactions() {
        List<TransactionResponseDTO> data = transactionService.getTransactions();
        return ResponseEntity.ok(new TransactionListResponse(data));
    }

    @Override
    public ResponseEntity<TransactionResponseDTO> getTransactionId(UUID id) {
      TransactionResponseDTO dto = transactionService.getTransactionId(id);
      return ResponseEntity.ok(dto);

    }
}
