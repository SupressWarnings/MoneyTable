package de.supresswarnings.moneytable.desktop.data;

import de.supresswarnings.moneytable.model.Account;
import de.supresswarnings.moneytable.model.transaction.UniqueTransaction;
import org.junit.Test;

public class DataInserterTest {

    @Test
    public void test(){
        DataInserter inserter = new DataInserter();

        Account account = new Account("dataProvider", 0.0d);
        Account account2 = new Account("dataProvider2", 0.1d);
        UniqueTransaction initialValues = new UniqueTransaction("dataProvider", 0.1d, 1L);
        UniqueTransaction updatedValues = new UniqueTransaction("dataProvider", 0.2d, 2L);
        account2.add(initialValues);

        inserter.insertAccount(account);
        inserter.insertTransaction(account, initialValues);

        inserter.insertAccount(account2);

        inserter.updateAccount("test", new Account("test2", 0.1d));
        inserter.updateTransaction(account, initialValues, updatedValues);

        inserter.deleteAccount(account);
        inserter.deleteTransaction(account, updatedValues);
    }
}
