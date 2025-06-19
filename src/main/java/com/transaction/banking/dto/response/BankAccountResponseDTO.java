package com.transaction.banking.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BankAccountResponseDTO {
    private String accountId;
    private String holderName;
    private BigDecimal amount;
}
