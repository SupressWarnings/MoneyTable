package de.supresswarnings.moneytable.desktop.data;

import de.supresswarnings.moneytable.model.Account;
import de.supresswarnings.moneytable.model.transaction.Transaction;
import de.supresswarnings.moneytable.model.transaction.TransactionGroup;
import de.supresswarnings.moneytable.model.transaction.UniqueTransaction;

import java.util.*;

public class DataProvider {

    //TODO
    public List<TransactionGroup> getTransactionGroups(){
        return new ArrayList<>();
    }

    //TODO
    public List<TransactionGroup> getTransactionGroups(int account){
        return new ArrayList<>();
    }

    //TODO
    public TransactionGroup getTransactionGroup(int id){
        return new TransactionGroup(null);
    }

    //TODO
    public List<Transaction> getTransactionList(){
        return new ArrayList<>();
    }

    //TODO
    public List<Transaction> getTransactionList(int account){
        return new ArrayList<>();
    }

    //TODO
    public Transaction getTransaction(int id){
        return new UniqueTransaction(null, 0, 0);
    }

    //TODO
    public Account getAccount(int id){
        return new Account(null, 0);
    }

    //TODO
    public Account getAccount(String name){
        return new Account(null, 0);
    }
}
