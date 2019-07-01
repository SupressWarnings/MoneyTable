package de.supresswarnings.moneytable.model.transaction;

/**
 * This class is used to ensure the correct and progressive use of ids for the Transactions.
 * It is the only way to create new Transactions.
 */
public class TransactionFactory {
    private static long id = 1;

    /**
     * Creates a new {@link Transaction} containing the passed parameters. Ensures the transaction has the correct id.
     *
     * @param name the name of the Transaction
     * @param amount the amount of money moved by the transaction
     * @param time the time at which the transaction occurred
     * @return a new Transaction with the passed parameters and the correct id
     */
    public static Transaction createTransaction(String name, double amount, long time){
        if(name != null && !"".equals(name) && amount != 0){
            Transaction transaction = new Transaction(name, amount, time, id);
            ++id;
            return transaction;
        }
        return null;
    }

    /**
     * Sets the id that the next transaction will have
     *
     * @param newId
     */
    public static void setId(long newId){
        id = newId;
    }

    private TransactionFactory(){}
}
