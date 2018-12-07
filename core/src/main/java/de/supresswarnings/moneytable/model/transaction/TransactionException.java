package de.supresswarnings.moneytable.model.transaction;

/**
 * This class represents a {@link RuntimeException} specifically with the transaction model.
 * It helps specifying that the exception has its root in the {@link de.supresswarnings.moneytable.model.transaction}-package sourcecode.
 *
 * @author Constantin Schulte
 */
class TransactionException extends  RuntimeException {
    /**
     * The constructor calls the constructor of {@link RuntimeException}.
     *
     * @param message The specific error message specifying the reason for the exception
     */
    TransactionException(String message){
        super(message);
    }
}
