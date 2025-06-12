package com.transaction.banking.service;

import com.transaction.banking.domain.BankAccount;
import com.transaction.banking.exceptions.AccountNotFoundException;
import com.transaction.banking.repository.BankAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BankAccountServiceTest {

    private BankAccountRepository bankAccountRepository;
    private BankAccountService bankAccountService;

    @BeforeEach
    void setUp() {
        bankAccountRepository = mock(BankAccountRepository.class);
        bankAccountService = new BankAccountService(bankAccountRepository);
    }

    @Test
    void createAccount_ShouldSaveAndReturnBankAccount() {
        BankAccount account = new BankAccount();
        account.setHolderName("João");

        when(bankAccountRepository.save(ArgumentMatchers.any(BankAccount.class)))
                .thenReturn(account);

        BankAccount result = bankAccountService.createAccount(account);

        assertNotNull(result);
        assertEquals("João", result.getHolderName());
        verify(bankAccountRepository, times(1)).save(account);
    }

    @Test
    void getAccounts_ShouldReturnListOfAccounts() {
        BankAccount account1 = new BankAccount();
        account1.setHolderName("Alice");

        BankAccount account2 = new BankAccount();
        account2.setHolderName("Bob");

        when(bankAccountRepository.findAll()).thenReturn(Arrays.asList(account1, account2));

        List<BankAccount> accounts = bankAccountService.getAccounts();

        assertEquals(2, accounts.size());
        verify(bankAccountRepository, times(1)).findAll();
    }

    @Test
    void getAccountById_ExistingId_ShouldReturnAccount() {
        UUID id = UUID.randomUUID();
        BankAccount account = new BankAccount();
        account.setAccountId(id);
        account.setHolderName("Carlos");

        when(bankAccountRepository.findById(id)).thenReturn(Optional.of(account));

        BankAccount result = bankAccountService.getAccountById(id);

        assertNotNull(result);
        assertEquals(id, result.getAccountId());
        assertEquals("Carlos", result.getHolderName());
        verify(bankAccountRepository, times(1)).findById(id);
    }

    @Test
    void getAccountById_NonExistingId_ShouldThrowException() {
        UUID id = UUID.randomUUID();

        when(bankAccountRepository.findById(id)).thenReturn(Optional.empty());

        AccountNotFoundException exception = assertThrows(AccountNotFoundException.class, () ->
                bankAccountService.getAccountById(id)
        );

        assertEquals("Conta não encontrada com o ID: " + id, exception.getMessage());
        verify(bankAccountRepository, times(1)).findById(id);
    }
}
