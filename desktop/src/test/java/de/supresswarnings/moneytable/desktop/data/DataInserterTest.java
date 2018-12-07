package de.supresswarnings.moneytable.desktop.data;

import de.supresswarnings.moneytable.model.Account;
import de.supresswarnings.moneytable.model.transaction.UniqueTransaction;
import org.junit.Test;

public class DataInserterTest {

    @Test
    public void test(){
        DataInserter inserter = new DataInserter();

        Account account = new Account("test", 0.0d);
        UniqueTransaction initialValues = new UniqueTransaction("test", 0.1d, 1L);
        UniqueTransaction updatedValues = new UniqueTransaction("test2", 0.2d, 2L);

        inserter.insertAccount(account);
        inserter.insertTransaction(account, initialValues);

        inserter.updateAccount("test", new Account("test2", 0.1d));
        inserter.updateTransaction(account, initialValues, updatedValues);

        inserter.deleteAccount(account);
        inserter.deleteTransaction(account, updatedValues);
    }
}
