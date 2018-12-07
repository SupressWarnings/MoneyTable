package de.supresswarnings.moneytable.model.transaction;

/**
 * Basic specialization of {@link Transaction} by adding a timestamp.
 *
 * @author Constantin Schulte
 */
public class UniqueTransaction extends Transaction {

    /**
     * The timestamp of a transaction is important for sorting and identifying them in a larger group.
     */
    private long time;

    /**
     * Calls the constructor of {@link Transaction} and sets the time.
     *
     * @param name the reason/name of the transaction
     * @param amount the amount of the transaction
     * @param time the time of the transaction
     */
    public UniqueTransaction(String name, double amount, long time){
        super(name, amount);
        this.time = time;
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
}
