package de.supresswarnings.moneytable.model.transaction;

import org.junit.Assert;
import org.junit.Test;

public class TransactionGroupTest {

    @Test
    public void testTransactionGroup(){
        String name = "test";

        TransactionGroup group = new TransactionGroup(name);

        Assert.assertEquals(name, group.getName());
        name = "test2";
        group.setName(name);
        Assert.assertEquals(name, group.getName());

        long time = System.currentTimeMillis();
        UniqueTransaction transaction = new UniqueTransaction("test", time,1.23);
        group.enterTransaction(transaction);
        Assert.assertEquals(transaction, group.getTransaction(time));
        Assert.assertTrue(group.getTransactions().contains(transaction));

        time = 0;
        group.enterTransaction(1.23, 0);
        Assert.assertTrue(group.transactionExists(0));
    }

    @Test(expected = TransactionError.class)
    public void testNameEmpty(){
        TransactionGroup group = new TransactionGroup("");
    }

    @Test(expected = TransactionError.class)
    public void testNameNull(){
        TransactionGroup group = new TransactionGroup(null);
    }
}
