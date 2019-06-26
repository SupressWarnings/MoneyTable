package de.supresswarnings.moneytable.model.transaction;

import org.junit.Assert;
import org.junit.Test;

public class TransactionFactoryTest {

    @Test
    public void test(){
        String name = "test";
        double amount = 1.2;
        long time = 1;
        long id = 1;

        TransactionFactory.setId(id);
        Transaction transaction = TransactionFactory.createTransaction(name, amount, time);
        testTransaction(transaction, name, amount, time, id);

        Assert.assertNull(TransactionFactory.createTransaction(null, amount, time));
        Assert.assertNull(TransactionFactory.createTransaction("", amount, time));
        Assert.assertNull(TransactionFactory.createTransaction(name, 0.0, time));
    }


    private void testTransaction(Transaction transaction, String name, double amount, long time, long id){
        Assert.assertEquals(name, transaction.getName());
        Assert.assertEquals(amount, transaction.getAmount(), 0.0);
        Assert.assertEquals(time, transaction.getTime());
        Assert.assertEquals(id, transaction.getId());
    }
}
