package de.supresswarnings.moneytable.model.transaction;

import java.util.ArrayList;

public class IrregularPeriodicTransaction extends PeriodicTransaction {
    private ArrayList<Double> change;

    public IrregularPeriodicTransaction(String name, boolean income, long first, long rate, boolean automatic, double amount) {
        super(name, income, first, rate, automatic, amount);
        change = new ArrayList<>();
        change.add(0, 0d);
    }

    public void setChange(double amount, int numberOfRate){
        if(numberOfRate <= 0){
            throw new TransactionError("Number of Rate can't be smaller/equal to zero (Error Code 301).");
        }
        change.add(numberOfRate, amount - getAmount());
    }

    public void setChange(double amount, long time){
        if(time < getFirst()){
            throw new TransactionError("Can't enter transaction before first transaction (Error code 302).");
        }
        change.add((int)((time - getFirst())/getRate()), amount - getAmount());
    }

    public double getAmount(int numberOfRate){
        return getAmount() + change.get(numberOfRate);
    }

    public double getAmount(long time){
        return getAmount((int)((time - getFirst()) / getRate()));
    }
}
