package de.supresswarnings.moneytable.desktop.data;

import de.supresswarnings.moneytable.model.Account;
import de.supresswarnings.moneytable.model.transaction.TransactionFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class DataProviderTest {
    private DataInserter insertor;
    private DataProvider provider;

    @Before
    public void init(){
        provider = new DataProvider();
        insertor = new DataInserter();
    }

    @Test
    public void testGetAccounts(){
        ArrayList<Account> accounts = (ArrayList<Account>)provider.getAccounts();
    }

    @Test
    public void testGetAccount(){
        insertor.insertAccount(new Account("provider", 0.0d));
        Assert.assertNotNull(provider.getAccount("provider"));
    }

    @Test
    public void testGetTransactionId(){
        insertor.insertTransaction(new Account("provider", 0.0d), TransactionFactory.createTransaction("provider", 0.1d, 1L));
        Assert.assertNotEquals(0, provider.getTransactionId(new Account("provider", 0.0d), TransactionFactory.createTransaction("provider", 0.1d, 1L)));
    }
}
