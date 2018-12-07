package de.supresswarnings.moneytable.model;

import de.supresswarnings.moneytable.model.transaction.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Models an account. It contains a username, the balance balance and a list of transactions.
 *
 * @author Constantin Schulte
 */
public class Account {

    /**
     * The username of the account.
     */
    private String name;

    /**
     * The balance balance of the account.
     */
    private double balance;

    /**
     * A list containing all {@link Transaction}s of the account.
     */
    private ArrayList<Transaction> transactions;

    /**
     * Sets username and balance and initializes the list.
     * @param name the username
     * @param balance the balance
     */
    public Account(String name, double balance){
        this.name = name;
        this.balance = balance;
        transactions = new ArrayList<>();
    }

    /**
     * Adds a transaction to the list.
     *
     * @param transaction the new transaction that will be added
     */
    public void add(Transaction transaction){
        transactions.add(transaction);
    }

    /**
     * Setter for the username of the account.
     *
     * @param name the new username
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter for the balance of the account.
     *
     * @param balance the new balance
     */
    void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * Getter for the username of the account.
     *
     * @return the username
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the balance of the account.
     *
     * @return the balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Gives all transactions of the account.
     *
     * @return the list containing all transactions
     */
    public List<Transaction> getTransactions() {
        return transactions;
    }
}
