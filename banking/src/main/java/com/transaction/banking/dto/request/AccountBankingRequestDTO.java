package com.transaction.banking.dto.request;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
public class AccountBankingRequestDTO {

    private UUID accountId;
    private String holderName;
    private BigDecimal amount;
}
