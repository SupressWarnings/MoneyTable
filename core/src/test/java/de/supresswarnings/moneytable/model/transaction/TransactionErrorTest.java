package de.supresswarnings.moneytable.model.transaction;

import org.junit.Assert;
import org.junit.Test;

public class TransactionErrorTest {

    @Test(expected = TransactionError.class)
    public void testThrow(){
        throw new TransactionError("Test Error.");
    }

    @Test
    public void test(){
        String testMessage = "Test Error";
        TransactionError error = new TransactionError(testMessage);
        Assert.assertEquals(testMessage, error.getMessage());
    }
}
