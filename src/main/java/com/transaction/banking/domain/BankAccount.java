package com.transaction.banking.domain;

import com.transaction.banking.exceptions.InsufficientFundsException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "bank_account")
public class BankAccount {

    @Id
    @GeneratedValue
    @Column(name = "account_id", columnDefinition = "BINARY(16)", nullable = false, updatable = false)
    private UUID accountId;

    @Column(name = "holder_name")
    private String holderName;

    @Column(nullable = false)
    private BigDecimal amount;
    public BankAccount(String holderName, BigDecimal amount) {
        this.accountId = UUID.randomUUID();
        this.holderName = holderName;
        this.amount = amount;
    }

    public void debit(BigDecimal amount){
        if(this.amount.compareTo(amount) < 0){
            throw new InsufficientFundsException("Saldo Insulficiente");
        }
        this.amount = this.amount.subtract(amount);
    }
    public void credit(BigDecimal amount){
        this.amount = this.amount.add(amount);
    }
}
