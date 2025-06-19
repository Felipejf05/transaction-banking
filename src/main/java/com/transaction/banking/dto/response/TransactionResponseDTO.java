package com.transaction.banking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponseDTO {

    private String idTransaction;
    private String fromAccountId;
    private String toAccountId;
    private BigDecimal amount;
    private LocalDateTime timestamp;

}
