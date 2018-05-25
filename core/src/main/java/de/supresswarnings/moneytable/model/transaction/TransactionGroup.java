package de.supresswarnings.moneytable.model.transaction;

import java.util.ArrayList;

public class TransactionGroup {
    private String name;

    private ArrayList<UniqueTransaction> transactions;

    public TransactionGroup(String name){
        if(name == null){
            throw new TransactionError("Name can't be null (Error Code 401).");
        }else if("".equals(name)){
            throw new TransactionError("Name can't be zero (Error Code 402).");
        }
        this.name = name;
        transactions = new ArrayList<>();
    }

    public void enterTransaction(double amount, long time){
        UniqueTransaction transaction = new UniqueTransaction(name, time, amount);
        transactions.add(transaction);
    }

    public void enterTransaction(UniqueTransaction transaction){
        transaction.setName(name);
        transactions.add(transaction);
    }

    public ArrayList<UniqueTransaction> getTransactions() {
        return transactions;
    }

    public UniqueTransaction getTransaction(long time){
        UniqueTransaction requested = null;
        for(UniqueTransaction transaction : transactions){
            if(time == transaction.getTime()){
                requested = transaction;
            }
        }
        return requested;
    }

    public boolean transactionExists(long time){
        boolean exists = false;
        for(UniqueTransaction transaction : transactions){
            if(transaction.getTime() == time){
                exists = true;
            }
        }
        return exists;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
