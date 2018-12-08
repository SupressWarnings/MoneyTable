package de.supresswarnings.moneytable.desktop.data;

import org.junit.Assert;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;

public class InitializerTest {

    @Test
    public void test(){
        ArrayList<String> tableNames = new ArrayList<>();
        String accountTable = "ACCOUNT", transactionTable = "TRANSACTION";
//                transactionGroupTable = "TRANSACTIONGROUP", periodicTranscationTable = "PERIODICTRANSACTION";
        tableNames.add(accountTable);
        tableNames.add(transactionTable);
//        tableNames.add(transactionGroupTable);
//        tableNames.add(periodicTranscationTable);

        String path = System.getProperty("user.home") + "/.moneytable/database";
        try {
            Connection connection = DriverManager.getConnection("jdbc:h2:" + path, "userame", "passord");
            Initializer initializer = new Initializer(connection);
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
            statement.execute("DROP TABLE transaction");
//            statement.execute("DROP TABLE transactionGroup");
//            statement.execute("DROP TABLE periodicTransaction");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
}
