package de.supresswarnings.moneytable.desktop.data;

import de.supresswarnings.moneytable.desktop.Main;
import de.supresswarnings.moneytable.model.Account;
import de.supresswarnings.moneytable.model.transaction.Transaction;
import org.junit.*;

import java.io.File;
import java.util.ArrayList;

public class DatabaseTest {

    @BeforeClass
    public static void init(){
        // Delete the existing Database for the tests
        String path = System.getProperty("user.home") + "/.moneytable/database.h2.db";
        File file = new File(path);
        file.delete();

        // Create the database
        Database database = Database.getDatabase();
        //Enter data
        database.createAccount("test", 0.1d);
        database.createTransaction(1L, "test", 0.5d, 15L, 1);
    }

    private Database database;

    @Before
    public void createDatabase(){
        this.database = Database.getDatabase();
    }

    @Test
    public void testGetDatabase(){
        Database database = Database.getDatabase();
        Assert.assertNotNull(database);

        Database database2 = Database.getDatabase();
        Assert.assertEquals(database, database2);

        Database.close();
        database = Database.getDatabase();
        Assert.assertNotNull(database);
    }

    @Test
    public void testCreateAccount(){
        database.createAccount("testCreateAccount", 0.0d);
    }

    @Test
    public void testCreateTransaction(){
        database.createTransaction(2, "database", 0.1d, 1L,1);
    }

    @Test
    public void testGetAccountId(){
        Assert.assertEquals(1, database.getAccountId("test"));
    }

    @Test
    public void testGetAccount(){
        Account account = database.getAccount(database.getAccountId("test"));
        Assert.assertNotNull(account);
        Assert.assertEquals("test", account.getName());
        Assert.assertEquals(0.1d, account.getBalance(), 0.0d);
    }

    @Test
    public void testGetAccounts(){
        Assert.assertFalse(database.getAccounts().isEmpty());
    }

    @Test
    public void testUpdateAccount(){
        database.updateAccount("updated", 10d, database.getAccountId("test"));

        Account account2 = database.getAccount(database.getAccountId("updated"));
        Assert.assertNotNull(account2);
        Assert.assertEquals("updated", account2.getName());
        Assert.assertEquals(10d, account2.getBalance(), 0.0d);
    }

    @Test
    public void testUpdateTransaction(){
        database.updateTransaction(1, "updated", 0.2d, 2L);
        Transaction updated = database.getTransaction(1);
        Assert.assertEquals("updated", updated.getName());
        Assert.assertEquals(0.2d, updated.getAmount(), 0.0d);
        Assert.assertEquals(2L, updated.getTime());

        database.updateTransaction( 2, "stuff", 0.2d, 2L);// no transaction
    }

    @Test
    public void testGetTransactions(){
        ArrayList<Transaction> transactions = (ArrayList<Transaction>)database.getTransactions(1);
        Assert.assertFalse(transactions.isEmpty());
    }

    @Test
    public void testDeleteTransaction(){
        database.deleteTransaction(1);
        Assert.assertNull(database.getTransaction(1));
    }

    @Test
    public void testDeleteAccount(){
        database.deleteAccount(1);
        Assert.assertNull(database.getAccount(1));
    }

    @Test
    public void testDatabaseClose(){
        Database.close();
        Database.close();
    }

    @After
    public void finish(){
        Database.close();
    }

    @AfterClass
    public static void writeLog(){
        Main.LOGGER.writeLog();
    }
}
