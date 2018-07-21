package de.supresswarnings.moneytable.desktop.data;

import org.junit.Test;

public class DatabaseTest {

    @Test
    public void test(){
        Database.close();
        Database.getDatabase();
        Database database = Database.getDatabase();
        database.createAccount("test", 0.0d);
        database.createTransaction("test", 0.0d, 0L,1);
        database.createGroup("test", 1);

        database.updateAccount("test2", 0.1d, 1);
        database.deleteAccount(1);

        database.updateTransaction("test2", 0.1d, 1L, 1, 1);
        database.deleteTransaction(1);

        Database.close();
    }
}
