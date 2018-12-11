package de.supresswarnings.moneytable.desktop.data;

import de.supresswarnings.moneytable.model.Account;
import de.supresswarnings.moneytable.model.transaction.Transaction;
import de.supresswarnings.moneytable.model.transaction.UniqueTransaction;

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
            insertTransaction(account, (UniqueTransaction)transaction);
        }
    }

    /**
     * Inserts the transaction into the {@link Database}
     *
     * @param account the account the transaction will be pointing to
     * @param transaction the transaction that will be saved
     */
    public void insertTransaction(Account account, UniqueTransaction transaction){
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
     * @param oldValues the old values
     */
    public void updateTransaction(Account account, UniqueTransaction newValues, UniqueTransaction oldValues){
        database.updateTransaction(newValues.getName(), newValues.getAmount(), newValues.getTime(),
                oldValues.getName(), oldValues.getAmount(), oldValues.getTime(), provider.getAccountId(account.getName()));
    }

    /**
     * Deletes a transaction.
     *
     * @param account the account of the transaction
     * @param transaction the transaction
     */
    public void deleteTransaction(Account account, UniqueTransaction transaction){
        database.deleteTransaction(provider.getTransactionId(account, transaction));
    }
}
