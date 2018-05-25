package de.supresswarnings.moneytable.model.transaction;

public abstract class Transaction {
    private String name;
    private double amount;
    private boolean income = true;

    Transaction(String name, double amount, boolean income){
        this.income = true;
        setName(name);
        setIncome(income);
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
        if((income && this.amount < 0) || (!income && this.amount > 0)){
            this.amount *= -1;
        }
    }

    public void setIncome(boolean income) {
        this.income = income;
        if((income && amount < 0) || (!income && amount > 0)){
            this.amount *= -1;
        }
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public boolean isIncome() {
        return income;
    }
}
