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

    Initializer(Connection connection){
        this.connection = connection;
        tableNames.add(accountTable);
        tableNames.add(transactionTable);
    }

    void checkTables(){
        try (Statement statement = connection.createStatement()){
            Main.LOGGER.log("INFO: Checking database tables");
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
            Main.LOGGER.logException("Error Code 701 (Table names could not be retrieved).", e);
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
            Main.LOGGER.log("INFO: Created all tables");
        } catch (SQLException e) {
            Main.LOGGER.logException("Error Code 702 (Can't create tables).", e);
        }
    }
}
