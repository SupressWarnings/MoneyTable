package de.supresswarnings.moneytable.model.transaction;

public class TransactionFactory {
    private static long id = 0;

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
