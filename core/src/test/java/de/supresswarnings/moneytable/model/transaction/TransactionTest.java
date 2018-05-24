package de.supresswarnings.moneytable.model.transaction;

import org.junit.Assert;

public class TransactionTest {

    public void test(Transaction transaction, String name, double amount, boolean income){
        Assert.assertEquals(name, transaction.getName());
        Assert.assertEquals(amount, transaction.getAmount(), 0.0);
        Assert.assertEquals(income, transaction.isIncome());
    }
}
