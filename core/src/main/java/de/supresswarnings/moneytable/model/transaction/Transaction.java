package de.supresswarnings.moneytable.model.transaction;

public class Transaction {
    private String name;
    private double amount;

    Transaction(String name, double amount){
        setName(name);
        setAmount(amount);
    }

    public void setName(String name){
        if(name == null){
            throw new TransactionError("Name can't be null (Error Code 101).");
        }else if(name.isEmpty()){
            throw new TransactionError("Name can't be empty (Error Code 102).");
        }
        this.name = name;
    }

    public void setAmount(double amount) {
        if(amount == 0){
            throw new TransactionError("Amount can't be zero (Error Code 103.");
        }
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public boolean isIncome() {
        return amount > 0;
    }
}
