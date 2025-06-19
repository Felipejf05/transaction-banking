package com.transaction.banking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BankAccountListResponse {
    private final List<BankAccountResponseDTO> data;

}
