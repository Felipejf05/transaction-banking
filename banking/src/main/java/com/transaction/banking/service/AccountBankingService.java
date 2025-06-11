package com.transaction.banking.service;

import com.transaction.banking.domain.AccountBanking;
import com.transaction.banking.exceptions.AccountNotFoundException;
import com.transaction.banking.repository.AccountBankingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
@Slf4j
public class AccountBankingService {

    private final AccountBankingRepository accountBankingRepository;
    public AccountBankingService(AccountBankingRepository accountBankingRepository){
        this.accountBankingRepository = accountBankingRepository;
    }
        public AccountBanking createAccount(AccountBanking accountBanking){
            //log.info("Persistindo os dados no banco..");
            return accountBankingRepository.save(accountBanking);
        }
        public List<AccountBanking> getAccounts(){
            return accountBankingRepository.findAll();
        }

        public ResponseEntity<AccountBanking> getAccountId(UUID idAccount) {
            try {
                AccountBanking account = accountBankingRepository.findById(idAccount)
                        .orElseThrow(() -> new AccountNotFoundException("Conta não encontrada com o ID: " + idAccount));
                return ResponseEntity.ok(account);
            }catch (AccountNotFoundException e){
                return ResponseEntity.notFound().build();
            }
        }
}
