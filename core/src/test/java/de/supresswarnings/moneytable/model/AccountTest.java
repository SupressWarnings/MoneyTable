package de.supresswarnings.moneytable.model;

import de.supresswarnings.moneytable.model.transaction.Transaction;
import de.supresswarnings.moneytable.model.transaction.TransactionFactory;
import org.junit.Assert;
import org.junit.Test;

public class AccountTest {

    @Test
    public void test(){
        String name = "test";
        double current = 1.23;

        Account account = new Account(name, current);

        Assert.assertEquals(name, account.getName());
        Assert.assertEquals(current, account.getBalance(), 0.0);

        Transaction transaction = TransactionFactory.createTransaction("test",1.23d,  System.currentTimeMillis());
        account.add(transaction);

        Assert.assertEquals(transaction, account.getTransactions().get(0));

        current = 2.34;
        account.setBalance(current);

        Assert.assertEquals(current, account.getBalance(), 0.0);

        name = "Name";
        account.setName(name);

        Assert.assertEquals(name, account.getName());
    }
}
