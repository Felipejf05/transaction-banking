package com.transaction.banking.controller.impl;

import com.transaction.banking.controller.BankAccountController;
import com.transaction.banking.domain.BankAccount;
import com.transaction.banking.dto.request.BankAccountRequestDTO;
import com.transaction.banking.dto.response.BankAccountResponseDTO;
import com.transaction.banking.dto.response.BankAccountResponseList;
import com.transaction.banking.exceptions.AccountNotFoundException;
import com.transaction.banking.mapper.BankAccountMapper;
import com.transaction.banking.service.BankAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class BankAccountControllerImpl implements BankAccountController {

    private final BankAccountMapper bankAccountMapper;
    private final BankAccountService bankAccountService;

    @Override
    public ResponseEntity<BankAccountResponseDTO> createAccount(BankAccountRequestDTO bankAccountRequestDTO) {
        BankAccount savedAccount = bankAccountService.createAccount(bankAccountMapper.toBankAccount(bankAccountRequestDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(bankAccountMapper.toResponseDTO(savedAccount));
    }
    @Override
    public ResponseEntity<BankAccountResponseList> getAccounts() {
        List<BankAccountResponseDTO> data = bankAccountMapper.toResponseDTOList(bankAccountService.getAccounts());
        return ResponseEntity.ok(new BankAccountResponseList(data));

    }
    @Override
    public ResponseEntity<BankAccountResponseDTO> getAccountId(UUID id) {
        try {
            BankAccount account = bankAccountService.getAccountById(id);
            BankAccountResponseDTO responseDTO = bankAccountMapper.toResponseDTO(account);
            return ResponseEntity.ok(responseDTO);
        } catch (AccountNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
