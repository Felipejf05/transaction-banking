package com.transaction.banking.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BankAccountResponseList {
    private List<BankAccountResponseDTO> data;

    public BankAccountResponseList(List<BankAccountResponseDTO> data) {
        this.data = data;
    }


}
