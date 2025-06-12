package com.transaction.banking.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class BankAccountResponseDTO {
    private UUID accountId;
    private String holderName;
    private BigDecimal amount;
}
