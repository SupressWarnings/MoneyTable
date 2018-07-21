package de.supresswarnings.moneytable.desktop.data;

import de.supresswarnings.moneytable.desktop.Main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

class Initializer {

    private Connection connection;
    private ArrayList<String> tableNames = new ArrayList<>();
    private String accountTable = "ACCOUNT";
    private String transactionTable = "TRANSACTION";
    private String transactionGroupTable = "TRANSACTIONGROUP";
    private String periodicTranscationTable = "PERIODICTRANSACTION";

    Initializer(Connection connection){
        this.connection = connection;
        tableNames.add(accountTable);
        tableNames.add(transactionTable);
        tableNames.add(transactionGroupTable);
        tableNames.add(periodicTranscationTable);
    }

    void checkTables(){
        try (Statement statement = connection.createStatement()){
            ResultSet tables = statement.executeQuery("SHOW TABLES");
            while(tables.next()){
                tableNames.remove(tables.getString(1));
            }
            if(!tableNames.isEmpty()){
                StringBuilder log = new StringBuilder("INFO: Creating tables ");
                for(String tableName : tableNames){
                    log.append(tableName).append(", ");
                }
                Main.LOGGER.log(log.substring(0, log.length()-2));
                createTables();
            }
            tables.close();
        } catch (SQLException e) {
            Main.LOGGER.log("Error Code 701 (Table names could not be retrieved).");
            Main.LOGGER.writeLog();
        }
    }

    private void createTables(){
        try(Statement createStatement = connection.createStatement()) {
            String statementPart = "CREATE TABLE IF NOT EXISTS ";

            if(tableNames.contains(accountTable)){
                createStatement.execute( statementPart+ accountTable + " (id INT AUTO_INCREMENT PRIMARY KEY, " +
                                                                                    "name VARCHAR(20) NOT NULL, " +
                                                                                    "current DECIMAL(100,2) NOT NULL)");
            }

            if(tableNames.contains(transactionTable)){
                createStatement.execute(statementPart + transactionTable + "(id INT AUTO_INCREMENT PRIMARY KEY, " +
                                                                                        "name VARCHAR(20) NOT NULL, " +
                                                                                        "amount DECIMAL(100,2) NOT NULL, " +
                                                                                        "time INT NOT NULL, " +
                                                                                        "account INT NOT NULL, " +
                                                                                        "transactionGroup INT)");
            }

            if(tableNames.contains(transactionGroupTable)){
                createStatement.execute(statementPart + transactionGroupTable + "(id INT AUTO_INCREMENT, " +
                                                                                        "name VARCHAR(20) NOT NULL, " +
                                                                                        "account INT NOT NULL)");
            }

            if(tableNames.contains(periodicTranscationTable)){
                createStatement.execute(statementPart + periodicTranscationTable + "(id INT AUTO_INCREMENT, " +
                                                                                        "transaction INT NOT NULL, " +
                                                                                        "rate MEDIUMINT NOT NULL, " +
                                                                                        "account INT NOT NULL, " +
                                                                                        "last INT UNSIGNED)");
            }
            Main.LOGGER.log("INFO: Created all tables");
        } catch (SQLException e) {
            Main.LOGGER.log("Error Code 702 (Can't create tables).");
            Main.LOGGER.writeLog();
        }
    }
}
