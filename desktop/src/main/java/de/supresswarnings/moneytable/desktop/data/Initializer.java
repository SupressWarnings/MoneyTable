package de.supresswarnings.moneytable.desktop.data;

import java.sql.*;
import java.util.ArrayList;

class Initializer {

    private Connection connection;
    private ArrayList<String> tableNames = new ArrayList<>();
    private String accountTable = "ACCOUNT", transactionTable = "TRANSACTION",
            transactionGroupTable = "TRANSACTIONGROUP", periodicTranscationTable = "PERIODICTRANSACTION";

    Initializer(Connection connection){
        this.connection = connection;
        tableNames.add(accountTable);
        tableNames.add(transactionTable);
        tableNames.add(transactionGroupTable);
        tableNames.add(periodicTranscationTable);
    }

    void checkTables(){
        try {
            Statement statement = connection.createStatement();
            ResultSet tables = statement.executeQuery("SHOW TABLES");
            while(tables.next()){
                tableNames.remove(tables.getString(1));
            }
            if(!tableNames.isEmpty()){
                createTables();
            }
        } catch (SQLException e) {
            System.out.println("Error Code 701 (Metadata could not be retrieved).");
            e.printStackTrace();
        }
    }

    private void createTables(){
        Statement createStatement = null;
        try {
            createStatement = connection.createStatement();

            if(tableNames.contains(accountTable)){
                createStatement.execute("CREATE TABLE IF NOT EXISTS " + accountTable + " (id INT AUTO_INCREMENT PRIMARY KEY, " +
                                                                                    "name VARCHAR(20) NOT NULL, " +
                                                                                    "current DECIMAL(100,2))");
            }

            if(tableNames.contains(transactionTable)){
                createStatement.execute("CREATE TABLE IF NOT EXISTS " + transactionTable + "(id INT AUTO_INCREMENT PRIMARY KEY, " +
                                                                                        "name VARCHAR(20) NOT NULL, " +
                                                                                        "amount DECIMAL(100,2) NOT NULL, " +
                                                                                        "time INT NOT NULL, " +
                                                                                        "account INT NOT NULL, " +
                                                                                        "transactionGroup INT)");
            }

            if(tableNames.contains(transactionGroupTable)){
                createStatement.execute("CREATE TABLE IF NOT EXISTS " + transactionGroupTable + "(id INT AUTO_INCREMENT, " +
                                                                                        "name VARCHAR(20) NOT NULL, " +
                                                                                        "account INT NOT NULL)");
            }

            if(tableNames.contains(periodicTranscationTable)){
                createStatement.execute("CREATE TABLE IF NOT EXISTS " + periodicTranscationTable + "(id INT AUTO_INCREMENT, " +
                                                                                        "transaction INT NOT NULL, " +
                                                                                        "rate MEDIUMINT NOT NULL, " +
                                                                                        "account INT NOT NULL, " +
                                                                                        "last INT UNSIGNED)");
            }
        } catch (SQLException e) {
            System.err.println("Error Code 702 (Can't create statement).");
            e.printStackTrace();
        }finally {
            if(createStatement != null){
                try {
                    createStatement.close();
                } catch (SQLException e) {
                    System.err.println("Error Code 703 (Can't close statement).");
                    e.printStackTrace();
                }
            }
        }
    }
}
