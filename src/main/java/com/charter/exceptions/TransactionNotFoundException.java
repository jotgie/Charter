package com.charter.exceptions;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException(String transactionUuid) {
        super("Transaction with uuid " + transactionUuid + " not found");
    }
}