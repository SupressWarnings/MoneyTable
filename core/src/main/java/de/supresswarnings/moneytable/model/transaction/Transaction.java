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
     * The timestamp of a transaction is important for sorting and identifying them in a larger group.
     */
    private long time;

    private long id;

    /**
     * Instantiates the class and sets the two basic values.
     *
     * @param name the name/reason of the transaction
     * @param amount the amount of the transaction (negative for expenses, positive for income)
     * @param time the time of the transaction
     */
    Transaction(String name, double amount, long time, long id){
        setName(name);
        setAmount(amount);
        setTime(time);
        this.id = id;
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

    /**
     * Setter for the timestamp.
     *
     * @param time the new time of the transaction
     */
    void setTime(long time) {
        this.time = time;
    }

    /**
     * Getter for the timestamp.
     *
     * @return the timestamp of the transaction
     */
    public long getTime() {
        return time;
    }

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }
}
