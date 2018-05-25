package de.supresswarnings.moneytable.model.transaction;

import org.junit.Assert;
import org.junit.Test;

public class TransactionTest {

    @Test
    public void testBasicTransaction(){
        String name = "testTransaction";
        double amount = 1.23;

        Transaction transaction = new Transaction(name, amount);
        testTransaction(transaction, name, amount, true);

        name = "test2";
        amount = -3.21;

        transaction.setName(name);
        transaction.setAmount(amount);

        testTransaction(transaction, name, amount, false);
    }

    public void testTransaction(Transaction transaction, String name, double amount, boolean income){
        Assert.assertEquals(name, transaction.getName());
        Assert.assertEquals(income, transaction.isIncome());
        Assert.assertEquals(amount, transaction.getAmount(), 0.0);
    }

    @Test(expected = TransactionError.class)
    public void testNameNull(){
        Transaction transaction = new Transaction(null, 1.23);
    }

    @Test(expected = TransactionError.class)
    public void testNameEmpty(){
        Transaction transaction = new Transaction("", 1.23);
    }

    @Test(expected = TransactionError.class)
    public void testAmountZero(){
        Transaction transaction = new Transaction("testTransaction", 0);
    }
}
