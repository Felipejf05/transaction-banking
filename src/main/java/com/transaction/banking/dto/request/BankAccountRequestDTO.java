package com.transaction.banking.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BankAccountRequestDTO {
    private String holderName;
    private BigDecimal amount;
}
