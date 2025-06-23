package com.transaction.banking.controller.impl;

import com.transaction.banking.controller.BankAccountController;
import com.transaction.banking.domain.BankAccount;
import com.transaction.banking.dto.request.BankAccountRequestDTO;
import com.transaction.banking.dto.response.BankAccountListResponse;
import com.transaction.banking.dto.response.BankAccountResponseDTO;
import com.transaction.banking.mapper.BankAccountMapper;
import com.transaction.banking.service.BankAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BankAccountControllerImpl implements BankAccountController {

    private final BankAccountService bankAccountService;
    private final BankAccountMapper bankAccountMapper;

    @Override
    public ResponseEntity<BankAccountResponseDTO> createAccount(BankAccountRequestDTO bankAccountRequestDTO) {
        BankAccount account = bankAccountMapper.toBankAccount(bankAccountRequestDTO);
        BankAccount savedAccount = bankAccountService.createAccount(account);
        BankAccountResponseDTO responseDTO = bankAccountMapper.toResponseDTO(savedAccount);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @Override
    public ResponseEntity<BankAccountListResponse> getAccounts() {
        List<BankAccount> accounts = bankAccountService.getAccounts();
        List<BankAccountResponseDTO> dtos = accounts.stream()
                .map(bankAccountMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new BankAccountListResponse(dtos));
    }

    @Override
    public ResponseEntity<BankAccountResponseDTO> getAccountId(UUID id) {
        BankAccount account = bankAccountService.getAccountById(id);
        BankAccountResponseDTO dto = bankAccountMapper.toResponseDTO(account);
        return ResponseEntity.ok(dto);
    }
}