package de.supresswarnings.moneytable.model.transaction;

/**
 * This class is used to ensure the correct and progressive use of ids for the Transactions.
 * It is the only way to create new Transactions.
 */
public class TransactionFactory {
    private static long id = 1;

    public static Transaction createTransaction(String name, double amount, long time){
        if(name != null && !"".equals(name) && amount != 0){
            Transaction transaction = new Transaction(name, amount, time, id);
            ++id;
            return transaction;
        }
        return null;
    }

    public static void setId(long newId){
        id = newId;
    }
}
