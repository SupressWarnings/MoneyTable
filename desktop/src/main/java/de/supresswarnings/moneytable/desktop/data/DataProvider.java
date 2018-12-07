package de.supresswarnings.moneytable.desktop.data;

import de.supresswarnings.moneytable.model.Account;
import de.supresswarnings.moneytable.model.transaction.UniqueTransaction;

import java.util.List;

/**
 * An interface between the {@link Database} and the {@link de.supresswarnings.moneytable.model.transaction}-package.
 * It processes data requests.
 */
public class DataProvider {

    /**
     * The single database object.
     */
    private Database database = Database.getDatabase();

    /**
     * Gives a {@link java.util.ArrayList}, but the accounts only contain username and balance.
     *
     * @return an {@link java.util.ArrayList} with accounts only containing username and balance.
     */
    public List<Account> getAccounts(){
        return database.getAccounts();
    }

    /**
     * Gives the account with the given name containing all existing information about it.
     *
     * @param name the name
     * @return the account containing username, balance and all its transactions
     */
    public Account getAccount(String name){
        return database.getAccount(database.getAccountId(name));
    }

    /**
     * Gets the id of an {@link Account} based on its name.
     *
     * @param name the username
     * @return the account id
     */
    int getAccountId(String name){
        return database.getAccountId(name);
    }

    /**
     * Gets the id of a {@link UniqueTransaction} based on its values.
     *
     * @param account the account of the transaction
     * @param transaction the transaction
     * @return the id of the transaction
     */
    int getTransactionId(Account account, UniqueTransaction transaction){
        return database.getTransaction(transaction.getName(), transaction.getAmount(), transaction.getTime(), getAccountId(account.getName()));
    }
}
