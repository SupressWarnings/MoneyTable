package de.supresswarnings.moneytable.model.transaction;

import org.junit.Assert;
import org.junit.Test;

public class TransactionExceptionTest {

    @Test(expected = TransactionException.class)
    public void testThrow(){
        throw new TransactionException("Test Error.");
    }

    @Test
    public void test(){
        String testMessage = "Test Error";
        TransactionException error = new TransactionException(testMessage);
        Assert.assertEquals(testMessage, error.getMessage());
    }
}
