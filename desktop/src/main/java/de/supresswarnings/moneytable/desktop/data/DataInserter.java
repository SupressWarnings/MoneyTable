package de.supresswarnings.moneytable.desktop.data;

import de.supresswarnings.moneytable.model.Account;
import de.supresswarnings.moneytable.model.transaction.Transaction;
import de.supresswarnings.moneytable.model.transaction.UniqueTransaction;

public class DataInserter {
    private Database database = Database.getDatabase();

    public void insertAccount(Account account){
        database.createAccount(account.getName(), account.getCurrent());
    }

    public void insertTransaction(int account, UniqueTransaction transaction){
        database.createTransaction(transaction.getName(), transaction.getAmount(), transaction.getTime(), account);
    }

    public void insertTransactionGroup(int account, Transaction transaction){
        database.createGroup(transaction.getName(), account);
    }

    public void updateAccount(int account, Account newValues){
        database.updateAccount(newValues.getName(), newValues.getCurrent(), account);
    }

    public void deleteAccount(int account){
        database.deleteAccount(account);
    }

    public void updateTransaction(int transaction, int account, UniqueTransaction newValues){
        database.updateTransaction(newValues.getName(), newValues.getAmount(), newValues.getTime(), account, transaction);
    }

    public void deleteTransaction(int transaction){
        database.deleteTransaction(transaction);
    }
}
