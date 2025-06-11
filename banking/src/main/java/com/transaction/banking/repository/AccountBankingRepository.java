package com.transaction.banking.repository;

import com.transaction.banking.domain.AccountBanking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface AccountBankingRepository extends JpaRepository<AccountBanking, UUID> {
}
