package de.supresswarnings.moneytable.model.transaction;

public class UniqueTransaction extends Transaction {

    private long time;

    public UniqueTransaction(String name, long time, double amount){
        super(name, amount);
        this.time = time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }
}
