package de.supresswarnings.moneytable.model.transaction;

public class UniqueTransaction extends Transaction {

    private long time;

    public UniqueTransaction(String name, boolean income, long time, double amount){
        super(name, amount, income);
        this.time = time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }
}
