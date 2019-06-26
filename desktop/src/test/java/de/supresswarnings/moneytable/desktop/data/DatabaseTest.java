package de.supresswarnings.moneytable.desktop.data;

import de.supresswarnings.moneytable.model.Account;
import de.supresswarnings.moneytable.model.transaction.Transaction;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class DatabaseTest {

    @Test
    public void test(){
        Database.getDatabase();
        Database database = Database.getDatabase();
        database.createAccount("database", 0.0d);
        database.createTransaction("database", 0.1d, 1L,1);

        Account account = database.getAccount(database.getAccountId("database"));
        Assert.assertNotNull(account);
        Assert.assertEquals("database", account.getName());
        Assert.assertEquals(0.0d, account.getBalance(), 0.0d);

        Assert.assertFalse(database.getAccounts().isEmpty());

        database.updateAccount("database2", 0.1d, database.getAccountId("database"));

        Account account2 = database.getAccount(database.getAccountId("database2"));
        Assert.assertNotNull(account2);
        Assert.assertEquals("database2", account2.getName());
        Assert.assertEquals(0.1d, account2.getBalance(), 0.0d);

        database.updateTransaction( "database2", 0.2d, 2L, "database", 0.1d, 1L, 1);
        database.updateTransaction( "database2", 0.2d, 2L, "database", 0.1d, 1L, 1);// no transaction
        ArrayList<Transaction> transactions = (ArrayList)database.getTransactions(1);
        Assert.assertFalse(transactions.isEmpty());

        boolean contains = false;
        for(Transaction transaction : transactions){
            if(transaction.getName().equals("database2")){
                contains = true;
            }
        }
        if(!contains){
            Assert.fail("Does not contain updated transaction.");
        }

        Account account3 = database.getAccount(1);

        database.deleteTransaction(1);
        database.deleteAccount(database.getAccountId("database2"));

        Database.close();
        Database.close();
    }
}
