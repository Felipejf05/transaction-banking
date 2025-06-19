package com.transaction.banking.exceptions;

public class TransactionValidationException extends RuntimeException{
    public TransactionValidationException(String message){
            super(message);
    }
}
