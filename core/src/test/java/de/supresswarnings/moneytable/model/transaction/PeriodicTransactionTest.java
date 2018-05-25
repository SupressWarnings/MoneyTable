package de.supresswarnings.moneytable.model.transaction;

import org.junit.Assert;
import org.junit.Test;

public class PeriodicTransactionTest extends TransactionTest {

    @Test
    public void testPeriodicTransaction(){
        String name = "testTransaction";
        double amount = 1.23;
        long first = 0;
        long rate = 1;

        PeriodicTransaction transaction = new PeriodicTransaction(name, first, rate, amount);
        testTransaction(transaction, name, amount, true);

        Assert.assertEquals(first, transaction.getFirst());
        Assert.assertEquals(rate, transaction.getRate());

        rate = 2;
        transaction.setRate(2);
        Assert.assertEquals(rate, transaction.getRate());
    }

    @Test(expected = TransactionError.class)
    public void testRateError(){
        PeriodicTransaction transaction = new PeriodicTransaction("testTransaction", 0, 0, 1.23);
    }
}
