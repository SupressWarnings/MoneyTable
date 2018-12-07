package de.supresswarnings.moneytable.model.transaction;

/**
 * This class models the basic transaction prototype. It contains simple information any kind of transaction will contain.
 *
 * @author Constantin Schulte
 */
public class Transaction {
    /**
     * The reason/name of a transaction helps to identify it.
     */
    private String name;

    /**
     * The amount of the transaction is required for any real modeling.
     */
    private double amount;

    /**
     * Instantiates the class and sets the two basic values.
     *
     * @param name the name/reason of the transaction
     * @param amount the amount of the transaction (negative for expenses, positive for income)
     */
    Transaction(String name, double amount){
        setName(name);
        setAmount(amount);
    }

    /**
     * Setter for the reason/name. Checks whether the name is valid and throws a {@link TransactionException} otherwise.
     *
     * @param name The new reason/name of the transaction
     */
    public void setName(String name){
        if(name == null){
            throw new TransactionException("Name can't be null (Error Code 101).");
        }else if(name.isEmpty()){
            throw new TransactionException("Name can't be empty (Error Code 102).");
        }
        this.name = name;
    }

    /**
     * Setter for the amount. Checks whether the amount is valid and throws a {@link TransactionException} otherwise.
     * @param amount the new amount of the transaction
     */
    void setAmount(double amount) {
        if(amount == 0){
            throw new TransactionException("Amount can't be zero (Error Code 103.");
        }
        this.amount = amount;
    }

    /**
     * Getter for the reason/name.
     *
     * @return the reason/name of the transaction
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the amount.
     *
     * @return the amount of the transaction
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Checks whether this transaction is income or an expense.
     *
     * @return true when it is an income, false if it is an expense
     */
    boolean isIncome() {
        return amount > 0;
    }
}
