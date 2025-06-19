package com.transaction.banking.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@AllArgsConstructor
public class TransactionRequestDTO {
    private String fromAccountId;
    private String toAccountId;
    private BigDecimal amount;
}
