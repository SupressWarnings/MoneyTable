package de.supresswarnings.moneytable.desktop.data;

import de.supresswarnings.moneytable.model.Account;
import org.junit.Assert;
import org.junit.Test;

public class DatabaseTest {

    @Test
    public void test(){
        Database.close();
        Database.getDatabase();
        Database database = Database.getDatabase();
        database.createAccount("test", 0.0d);
        database.createTransaction("test", 0.1d, 1L,1);

        Assert.assertEquals("", database.lastError);
        Assert.assertEquals(1, database.getAccounts().size());

        database.getAccountId("test");

        Account account = database.getAccount(database.getAccountId("test"));
        Assert.assertNotNull(account);
        Assert.assertEquals("test", account.getName());
        Assert.assertEquals(0.0d, account.getBalance(), 0.0d);

        database.getTransactions(1);

        account = database.getAccount(database.getAccountId("test"));
        Assert.assertEquals("test", account.getName());
        Assert.assertEquals(0.0d, account.getBalance(), 0.0d);

        database.updateAccount("test2", 0.1d, database.getAccountId("test"));
        database.deleteAccount(database.getAccountId("test2"));

        database.updateTransaction( "test2", 0.2d, 2L, "test", 0.1d, 1L, 1);
        database.deleteTransaction(1);

        Database.close();
    }
}
