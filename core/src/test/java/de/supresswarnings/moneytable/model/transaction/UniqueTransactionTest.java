package de.supresswarnings.moneytable.model.transaction;

import org.junit.Assert;
import org.junit.Test;

public class UniqueTransactionTest extends TransactionTest {
    @Test
    public void test(){
        String name = "testTransaction";
        long time = 0;
        double amount = 1.23;

        UniqueTransaction transaction = new UniqueTransaction(name, time, amount);
        testTransaction(transaction, name, amount, true);
        Assert.assertEquals(time, transaction.getTime());

        name = "test2";
        time = System.currentTimeMillis();
        amount = -3.21;

        transaction.setName(name);
        transaction.setTime(time);
        transaction.setAmount(amount);

        testTransaction(transaction, name, amount, false);
        Assert.assertEquals(time, transaction.getTime());
    }
}
