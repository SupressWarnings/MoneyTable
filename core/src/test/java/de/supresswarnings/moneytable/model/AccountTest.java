package de.supresswarnings.moneytable.model;

import de.supresswarnings.moneytable.model.transaction.TransactionGroup;
import de.supresswarnings.moneytable.model.transaction.UniqueTransaction;
import org.junit.Assert;
import org.junit.Test;

public class AccountTest {

    @Test
    public void test(){
        String name = "test";
        double current = 1.23;

        Account account = new Account(name, current);

        Assert.assertEquals(name, account.getName());
        Assert.assertEquals(current, account.getCurrent(), 0.0);

        UniqueTransaction transaction = new UniqueTransaction("test", System.currentTimeMillis(), 1.23);
        account.add(transaction);

        Assert.assertEquals(transaction, account.getTransactions().get(0));

        current = 2.34;
        account.setCurrent(current);

        Assert.assertEquals(current, account.getCurrent(), 0.0);

        name = "Name";
        account.setName(name);

        Assert.assertEquals(name, account.getName());

        TransactionGroup group = new TransactionGroup("test");
        account.add(group);

        Assert.assertEquals(group, account.getTransactionGroups().get(0));
    }
}
