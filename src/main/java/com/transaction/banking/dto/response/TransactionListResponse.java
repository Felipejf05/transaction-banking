package com.transaction.banking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class TransactionListResponse {
    private final List<TransactionResponseDTO> data;
}
