package de.supresswarnings.moneytable.model.transaction;

import org.junit.Assert;
import org.junit.Test;

public class IrregularPeriodicTransactionTest extends PeriodicTransactionTest {

    @Test
    public void testIrregularPeriodicTransaction(){
        String name = "testTransaction";
        double amount = 1.23;
        long rate = 1;

        IrregularPeriodicTransaction transaction = new IrregularPeriodicTransaction(name, 0, rate, amount);
        testTransaction(transaction, name, amount, true);

        transaction.setChange(1d, 1);
        Assert.assertEquals(1d, transaction.getAmount(1), 0.0);

        long time = System.currentTimeMillis();
        transaction.setChange(1d, time);
        Assert.assertEquals(1d, transaction.getAmount(time), 0.0);
    }

    @Test(expected = TransactionError.class)
    public void testRateError(){
        IrregularPeriodicTransaction transaction = new IrregularPeriodicTransaction("test", 0, 1, 1.23);
        transaction.setChange(0d, 0);
    }

    @Test(expected = TransactionError.class)
    public void testTimeError(){
        IrregularPeriodicTransaction transaction = new IrregularPeriodicTransaction("test", 0, 1, 1.23);
        transaction.setChange(1d, -1L);
    }
}
