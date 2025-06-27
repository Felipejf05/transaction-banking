package com.transaction.banking.service;

import com.transaction.banking.domain.BankAccount;
import com.transaction.banking.exceptions.AccountNotFoundException;
import com.transaction.banking.repository.BankAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    public BankAccount save(BankAccount account) {
        log.info("Salvando conta ID: {}, Titular: {}", account.getAccountId(), account.getHolderName());
        return bankAccountRepository.save(account);
    }

    public List<BankAccount> getAccounts() {
        log.info("Buscando todas as contas cadastradas");
        return bankAccountRepository.findAll();
    }

    public BankAccount getAccountById(UUID idAccount) {
        log.info("Buscando conta com o ID: {}", idAccount);
        return bankAccountRepository.findById(idAccount)
                .orElseThrow(() -> new AccountNotFoundException("Conta não encontrada com o ID: " + idAccount));
    }

}