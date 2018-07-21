package de.supresswarnings.moneytable.desktop.data;

import de.supresswarnings.moneytable.model.Account;
import de.supresswarnings.moneytable.model.transaction.UniqueTransaction;
import org.junit.Test;

public class DataInserterTest {

    @Test
    public void test(){
        DataInserter inserter = new DataInserter();

        inserter.insertAccount(new Account("test", 0.0d));
        inserter.insertTransaction(1, new UniqueTransaction("test", 1L, 0.1d));
        inserter.insertTransactionGroup(1, new UniqueTransaction("test2", 2L, 0.2d));

        inserter.updateAccount(1, new Account("test2", 0.1d));
        inserter.updateTransaction(1, 1, new UniqueTransaction("test2", 2L, 0.2d));

        inserter.deleteAccount(1);
        inserter.deleteTransaction(1);
    }
}
