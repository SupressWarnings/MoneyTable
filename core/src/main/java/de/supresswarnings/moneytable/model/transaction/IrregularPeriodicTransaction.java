package de.supresswarnings.moneytable.model.transaction;

import java.util.HashMap;

public class IrregularPeriodicTransaction extends PeriodicTransaction {
    private HashMap<Integer, Double> change;

    public IrregularPeriodicTransaction(String name, long first, long rate, double amount) {
        super(name, first, rate, amount);
        change = new HashMap<>();
        change.put(0, 0d);
    }

    public void setChange(double amount, int numberOfRate){
        if(numberOfRate <= 0){
            throw new TransactionError("Number of Rate can't be smaller/equal to zero (Error Code 301).");
        }
        change.put(numberOfRate, amount - getAmount());
    }

    public void setChange(double amount, long time){
        if(time < getFirst()){
            throw new TransactionError("Can't enter transaction before first transaction (Error code 302).");
        }
        change.put((int)((time - getFirst())/getRate()), amount - getAmount());
    }

    public double getAmount(int numberOfRate){
        return getAmount() + change.get(numberOfRate);
    }

    public double getAmount(long time){
        return getAmount((int)((time - getFirst()) / getRate()));
    }
}
