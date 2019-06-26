package de.supresswarnings.moneytable.model.transaction;

import org.junit.Assert;
import org.junit.Test;

public class TransactionTest {

    @Test
    public void testBasicTransaction(){

        String name = "testTransaction";
        long time = 0;
        double amount = 1.23;
        long id = 0;

        Transaction transaction = new Transaction(name, amount, time, id);
        testTransaction(transaction, name, amount, true, time, id);

        name = "test2";
        time = System.currentTimeMillis();
        amount = -3.21;

        transaction.setName(name);
        transaction.setTime(time);
        transaction.setAmount(amount);

        testTransaction(transaction, name, amount, false, time, id);
    }

    private void testTransaction(Transaction transaction, String name, double amount, boolean income, long time, long id){
        Assert.assertEquals(name, transaction.getName());
        Assert.assertEquals(income, transaction.isIncome());
        Assert.assertEquals(amount, transaction.getAmount(), 0.0);
        Assert.assertEquals(time, transaction.getTime());
        Assert.assertEquals(id, transaction.getId());
    }

    @Test(expected = TransactionException.class)
    public void testNameNull(){
        Transaction transaction = new Transaction(null, 1.23, 1, 0);
    }

    @Test(expected = TransactionException.class)
    public void testNameEmpty(){
        Transaction transaction = new Transaction("", 1.23, 1, 0);
    }

    @Test(expected = TransactionException.class)
    public void testAmountZero(){
        Transaction transaction = new Transaction("testTransaction", 0, 1, 0);
    }
}
