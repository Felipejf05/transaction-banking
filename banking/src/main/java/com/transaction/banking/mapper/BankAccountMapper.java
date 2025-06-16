package com.transaction.banking.mapper;

import com.transaction.banking.domain.BankAccount;
import com.transaction.banking.dto.request.BankAccountRequestDTO;
import com.transaction.banking.dto.response.BankAccountResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class BankAccountMapper {

    public BankAccount toBankAccount(BankAccountRequestDTO bankAccountRequestDTO) {
        if (bankAccountRequestDTO == null) {
            return null;
        }

        BankAccount bankAccount = new BankAccount();

        bankAccount.setHolderName(bankAccountRequestDTO.getHolderName());
        bankAccount.setAmount(bankAccountRequestDTO.getAmount());

        return bankAccount;
    }

    public BankAccountResponseDTO toResponseDTO(BankAccount bankAccount) {
        if (bankAccount == null) {
            return null;
        }

        BankAccountResponseDTO toDTO = new BankAccountResponseDTO();
        toDTO.setAccountId(bankAccount.getAccountId().toString());
        toDTO.setHolderName(bankAccount.getHolderName());
        toDTO.setAmount(bankAccount.getAmount());

        return toDTO;
    }

    public List<BankAccountResponseDTO> toResponseDTOList(List<BankAccount> accounts) {
        if (accounts == null || accounts.isEmpty()) {
            return Collections.emptyList();
        }
        return accounts.stream().filter(account -> account != null)
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
}
