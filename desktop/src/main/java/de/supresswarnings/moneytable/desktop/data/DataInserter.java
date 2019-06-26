package de.supresswarnings.moneytable.desktop.data;

import de.supresswarnings.moneytable.model.Account;
import de.supresswarnings.moneytable.model.transaction.Transaction;

/**
 * An interface between the {@link Database} and the {@link de.supresswarnings.moneytable.model.transaction}-package.
 * It processes save requests.
 */
public class DataInserter {
    /**
     * The single object of the database.
     */
    private Database database = Database.getDatabase();

    /**
     * A DataProvider is needed to convert between the {@link Database} and {@link de.supresswarnings.moneytable.model.transaction}-model.
     */
    private DataProvider provider = new DataProvider();

    /**
     * Inserts the account into the {@link Database}
     *
     * @param account The account that will be saved
     */
    public void insertAccount(Account account){
        database.createAccount(account.getName(), account.getBalance());
        for(Transaction transaction : account.getTransactions()){
            insertTransaction(account, transaction);
        }
    }

    /**
     * Inserts the transaction into the {@link Database}
     *
     * @param account the account the transaction will be pointing to
     * @param transaction the transaction that will be saved
     */
    public void insertTransaction(Account account, Transaction transaction){
        database.createTransaction(transaction.getName(), transaction.getAmount(), transaction.getTime(), provider.getAccountId(account.getName()));
    }

    /**
     * Updates the balance of the account.
     *
     * @param account the account
     * @param balance the new balance
     */
    public void updateAccount(Account account, double balance){
        database.updateAccount(account.getName(), balance, provider.getAccountId(account.getName()));
    }

    /**
     * Updates an existing account.
     *
     * @param accountName the username
     * @param newValues the new values
     */
    public void updateAccount(String accountName, Account newValues){
        database.updateAccount(newValues.getName(), newValues.getBalance(), provider.getAccountId(accountName));
    }

    /**
     * Deletes an account.
     *
     * @param account the account that will be deleted
     */
    public void deleteAccount(Account account){
        database.deleteAccount(provider.getAccountId(account.getName()));
    }

    /**
     * Updates a transaction to new values.
     *
     * @param account the account of the transaction
     * @param newValues the new values
     * @param oldValues the old values or null if the a new transaction is to be created
     */
    public void transactionInput(Account account, Transaction newValues, Transaction oldValues){
        if(oldValues != null && provider.getTransactionId(account, oldValues) != 0){
            database.updateTransaction(newValues.getName(), newValues.getAmount(), newValues.getTime(),
                    oldValues.getName(), oldValues.getAmount(), oldValues.getTime(), provider.getAccountId(account.getName()));
        }else{
            insertTransaction(account, newValues);
        }
    }

    /**
     * Deletes a transaction.
     *
     * @param account the account of the transaction
     * @param transaction the transaction
     */
    public void deleteTransaction(Account account, Transaction transaction){
        database.deleteTransaction(provider.getTransactionId(account, transaction));
    }
}
