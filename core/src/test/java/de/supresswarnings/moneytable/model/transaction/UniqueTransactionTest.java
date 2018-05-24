package de.supresswarnings.moneytable.model.transaction;

import org.junit.Assert;
import org.junit.Test;

public class UniqueTransactionTest extends TransactionTest {
    @Test
    public void test(){
        String name = "test";
        long time = System.currentTimeMillis();
        double amount = 1.23;

        UniqueTransaction transaction = new UniqueTransaction(name, true, time, amount);
        test(transaction, name, amount, true);
        Assert.assertEquals(time, transaction.getTime());

        name = "test2";
        time = System.currentTimeMillis();
        amount = 3.21;

        transaction.setName(name);
        transaction.setTime(time);
        transaction.setAmount(amount);
        transaction.setIncome(false);

        test(transaction, name, amount * -1, false);
        Assert.assertEquals(time, transaction.getTime());
    }

    @Test(expected = TransactionError.class)
    public void testNameNull(){
        UniqueTransaction transaction = new UniqueTransaction(null, true, System.currentTimeMillis(), 1.23);
    }

    @Test(expected = TransactionError.class)
    public void testNameEmpty(){
        UniqueTransaction transaction = new UniqueTransaction("", true, System.currentTimeMillis(), 1.23);
    }

    @Test(expected = TransactionError.class)
    public void testAmountZero(){
        UniqueTransaction transaction = new UniqueTransaction("test", true, System.currentTimeMillis(), 0);
    }
}
