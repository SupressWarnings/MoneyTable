package de.supresswarnings.moneytable.model.transaction;

public class TransactionError extends  RuntimeException {
    public TransactionError(String message){
        super(message);
    }
}
