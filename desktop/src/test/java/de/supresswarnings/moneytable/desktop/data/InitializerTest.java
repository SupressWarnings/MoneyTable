package de.supresswarnings.moneytable.desktop.data;

import de.supresswarnings.moneytable.desktop.Main;
import org.junit.Assert;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;

public class InitializerTest {

    @Test
    public void test(){
        ArrayList<String> tableNames = new ArrayList<>();
        String accountTable = "ACCOUNT", transactionTable = "TRANSACTION";
        tableNames.add(accountTable);
        tableNames.add(transactionTable);

        String path = System.getProperty("user.home") + "/.moneytable/database";
        try {
            Connection connection = DriverManager.getConnection("jdbc:h2:" + path, "userame", "passord");
            Initializer initializer = new Initializer(connection);
            initializer.checkTables();
            initializer.checkTables();
            initializer.checkTables();

            Statement statement = connection.createStatement();
            ResultSet tables = statement.executeQuery("SHOW TABLES");
            while(tables.next()){
                tableNames.remove(tables.getString(1));
            }
            if(!tableNames.isEmpty()){
                Assert.fail("Tables not created");
            }

            statement.execute("DROP TABLE account");
            ResultSet deletedAccount = statement.executeQuery("SHOW TABLES");
            deletedAccount.next();
            if(!"ACCOUNT".equals(deletedAccount.getString(1))){
                initializer.checkTables();
            }else{
                Assert.fail("Deleting Table ACCOUNT failed");
            }

            statement.execute("DROP TABLE transaction");
            ResultSet deletedTransaction = statement.executeQuery("SHOW TABLES");
            deletedTransaction.next();
            if(!deletedTransaction.next()){
                initializer.checkTables();
            }else{
                Assert.fail("Deleting Table TRANSACTION failed");
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            Assert.fail();
        }
        Main.LOGGER.writeLog();
    }
}
