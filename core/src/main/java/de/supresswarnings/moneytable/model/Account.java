package de.supresswarnings.moneytable.model;

import de.supresswarnings.moneytable.model.transaction.Transaction;
import de.supresswarnings.moneytable.model.transaction.TransactionGroup;

import java.util.ArrayList;

public class Account {

    private String name;
    private double current;
    private ArrayList<TransactionGroup> transactionGroups;
    private ArrayList<Transaction> transactions;

    public Account(String name, double current){
        this.name = name;
        this.current = current;
        transactionGroups = new ArrayList<>();
        transactions = new ArrayList<>();
    }

    public void add(Transaction transaction){
        transactions.add(transaction);
    }

    public void add(TransactionGroup group){
        transactionGroups.add(group);
    }

    public void setCurrent(double current) {
        this.current = current;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public double getCurrent() {
        return current;
    }

    public ArrayList<TransactionGroup> getTransactionGroups() {
        return transactionGroups;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }
}
