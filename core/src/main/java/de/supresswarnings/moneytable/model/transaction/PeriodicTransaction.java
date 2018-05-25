package de.supresswarnings.moneytable.model.transaction;

public class PeriodicTransaction extends Transaction {

    private long first;
    private long rate;
    private double amount;

    public PeriodicTransaction(String name, long first, long rate, double amount){
        super(name, amount);
        setRate(rate);
        this.first = first;
    }

    public void setRate(long rate) {
        if(rate == 0){
            throw new TransactionError("Rate can't be zero (Error Code 201).");
        }
        this.rate = rate;
    }


    public long getFirst() {
        return first;
    }

    public long getRate() {
        return rate;
    }
}
