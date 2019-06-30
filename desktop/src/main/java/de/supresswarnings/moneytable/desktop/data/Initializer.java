package de.supresswarnings.moneytable.desktop.data;

import de.supresswarnings.moneytable.desktop.Main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * This class initially checks whether the database is set up.
 * If not, it creates the missing tables and will later, if necessary, update them to newer versions and port the data.
 */
class Initializer {

    /**
     * {@link Connection} required for connecting to the database file system.
     */
    private Connection connection;

    /**
     * Contains all the different table names to check whether they exist or not.
     */
    private ArrayList<String> tableNames = new ArrayList<>();

    /**
     * The name of account table.
     */
    private String accountTable = "ACCOUNT";

    /**
     * The name of the transaction table.
     */
    private String transactionTable = "TRANSACTION";

    /**
     * Creates a new Initializer by setting the connection and adding the table names to the list.
     *
     * @param connection the {@link Connection} to the database
     */
    Initializer(Connection connection){
        this.connection = connection;
        tableNames.add(accountTable);
        tableNames.add(transactionTable);
    }

    /**
     * Checks whether the tables in the database exist or not.
     */
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

    /**
     * Creates the missing tables.
     */
    private void createTables(){
        try(Statement createStatement = connection.createStatement()) {
            String statementPart = "CREATE TABLE IF NOT EXISTS ";

            if(tableNames.contains(accountTable)){
                createStatement.execute( statementPart+ accountTable + " (id INT AUTO_INCREMENT PRIMARY KEY, " +
                                                                                    "name VARCHAR(20) NOT NULL, " +
                                                                                    "current DECIMAL(100,2) NOT NULL)");
            }

            if(tableNames.contains(transactionTable)){
                createStatement.execute(statementPart + transactionTable + "(id INT PRIMARY KEY, " +
                                                                                        "name VARCHAR(20) NOT NULL, " +
                                                                                        "amount DECIMAL(100,2) NOT NULL, " +
                                                                                        "time long NOT NULL, " +
                                                                                        "account INT NOT NULL)");
            }
            Main.LOGGER.log("INFO: Created all tables");
        } catch (SQLException e) {
            Main.LOGGER.logException("Error Code 702 (Can't create tables).", e);
        }
    }
}
