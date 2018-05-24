package de.supresswarnings.moneytable.model.transaction;

public class PeriodicTransaction extends Transaction {

    private long first;
    private long rate;
    private boolean automatic;
    private double amount;

    public PeriodicTransaction(String name, boolean income, long first, long rate, boolean automatic, double amount){
        super(name, amount, income);
        this.first = first;
        this.rate = rate;
        this.automatic = automatic;
        this.amount = amount;
    }

    public void setFirst(long first) {
        this.first = first;
    }

    public void setRate(long rate) {
        if(rate == 0){
            throw new TransactionError("Rate can't be zero (Error Code 201).");
        }
        this.rate = rate;
    }

    public void setAutomatic(boolean automatic) {
        this.automatic = automatic;
    }

    public long getFirst() {
        return first;
    }

    public long getRate() {
        return rate;
    }

    public boolean isAutomatic() {
        return automatic;
    }
}
