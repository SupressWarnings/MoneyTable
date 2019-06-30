package de.supresswarnings.moneytable.desktop.data;

import de.supresswarnings.moneytable.model.Account;
import de.supresswarnings.moneytable.model.transaction.Transaction;
import de.supresswarnings.moneytable.model.transaction.TransactionFactory;
import org.junit.Test;

public class DataInserterTest {

    @Test
    public void test(){
        DataInserter inserter = new DataInserter();

        Account account = new Account("dataProvider", 0.0d);
        Account account2 = new Account("dataProvider2", 0.1d);
        Transaction initialValues = TransactionFactory.createTransaction("dataProvider", 0.1d, 1L);
        Transaction updatedValues = TransactionFactory.createTransaction("dataProvider", 0.2d, 2L);
        account2.add(initialValues);

        inserter.insertAccount(account);
        inserter.insertTransaction(account, initialValues);

        inserter.insertAccount(account2);
        inserter.updateAccount(account2, 0.2d);

        inserter.updateAccount("test", new Account("test2", 0.1d));
        inserter.transactionInput(account, initialValues);

        inserter.deleteAccount(account);
        inserter.deleteTransaction(account, updatedValues);
    }
}
