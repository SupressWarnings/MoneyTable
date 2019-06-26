package de.supresswarnings.moneytable.desktop.data;

import de.supresswarnings.moneytable.model.Account;
import de.supresswarnings.moneytable.model.transaction.TransactionFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class DataProviderTest {

    @Test
    public void test(){
        DataProvider provider = new DataProvider();
        DataInserter inserter = new DataInserter();

        ArrayList<Account> accounts = (ArrayList<Account>)provider.getAccounts();

        inserter.insertAccount(new Account("provider", 0.0d));
        Assert.assertNotNull(provider.getAccount("provider"));

        inserter.insertTransaction(new Account("provider", 0.0d), TransactionFactory.createTransaction("provider", 0.1d, 1L));
        Assert.assertNotEquals(0, provider.getTransactionId(new Account("provider", 0.0d), TransactionFactory.createTransaction("provider", 0.1d, 1L)));
    }
}
