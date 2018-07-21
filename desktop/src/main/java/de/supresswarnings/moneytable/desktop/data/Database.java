package de.supresswarnings.moneytable.desktop.data;

import de.supresswarnings.moneytable.desktop.Main;

import java.sql.*;

class Database {

    private static Database database;

    private Connection connection;
    private PreparedStatement createAccount;
    private PreparedStatement createTransaction;
    private PreparedStatement createGroup;

    private PreparedStatement updateAccount;
    private PreparedStatement updateTransaction;

    private PreparedStatement deleteAccount;
    private PreparedStatement deleteTransaction;

    private Database(){
        String path = System.getProperty("user.home") + "/.moneytable/database";
        try {
            connection = DriverManager.getConnection("jdbc:h2:" + path, "", "");
            Main.LOGGER.log("INFO: Database connection established.");
            Initializer initializer = new Initializer(connection);
            initializer.checkTables();
            Main.LOGGER.log("INFO: Database initialization completed");
        } catch (SQLException e) {
            Main.LOGGER.log("Error Code 601 (Database Connection error).");
            Main.LOGGER.log(e.getMessage());
            Main.LOGGER.writeLog();
        }
        initialize();
    }

    private void initialize(){
        try{
            createAccount = connection.prepareStatement("INSERT INTO account VALUES(DEFAULT, ?, ?)");
            createTransaction = connection.prepareStatement("INSERT INTO transaction VALUES(DEFAULT, ?, ?, ?, ?, NULL)");
            createGroup = connection.prepareStatement("INSERT INTO transactiongroup VALUES(DEFAULT, ?, ?)");
//            createPeriodic = connection.prepareStatement("INSERT INTO periodictransaction VALUES(DEFAULT, ?, ?, ?, ?)");

            updateAccount = connection.prepareStatement("UPDATE account SET name = ?, current = ? WHERE id = ?");
            updateTransaction = connection.prepareStatement("UPDATE transaction SET name = ?, amount = ?, time = ?, account = ? WHERE id = ?");

            deleteAccount = connection.prepareStatement("DELETE FROM account WHERE id = ?");
            deleteTransaction = connection.prepareStatement("DELETE FROM transaction WHERE id = ?");
        } catch (SQLException e) {
            Main.LOGGER.log("ERROR: Code 603 (Preparing statements failed).");
            Main.LOGGER.log(e.getMessage());
            Main.LOGGER.writeLog();
        }
    }

    void createAccount(String name, double current){
        try {
            createAccount.setString(1, name);
            createAccount.setDouble(2, current);
            createAccount.execute();
        } catch (SQLException e) {
            Main.LOGGER.log("ERROR: Code 604 (Creating account failed).");
            Main.LOGGER.log(e.getMessage());
            Main.LOGGER.writeLog();
        }
    }

    void createTransaction(String name, double amount, long time, int account){
        try {
            createTransaction.setString(1, name);
            createTransaction.setDouble(2, amount);
            createTransaction.setLong(3, time);
            createTransaction.setInt(4, account);
            createTransaction.execute();
        } catch (SQLException e) {
            Main.LOGGER.log("ERROR: Code 605 (Creating transaction failed).");
            Main.LOGGER.log(e.getMessage());
            Main.LOGGER.writeLog();
        }
    }

    void createGroup(String name, int account){
        try {
            createGroup.setString(1, name);
            createGroup.setInt(2, account);
            createGroup.execute();
        } catch (SQLException e) {
            Main.LOGGER.log("ERROR: Code 606 (Creating group failed).");
            Main.LOGGER.log(e.getMessage());
            Main.LOGGER.writeLog();
        }
    }

    void updateAccount(String name, double current, int id){
        try {
            updateAccount.setString(1, name);
            updateAccount.setDouble(2, current);
            updateAccount.setInt(3, id);
            updateAccount.executeUpdate();
        } catch (SQLException e) {
            Main.LOGGER.log("ERROR: Code 607 (Updating account failed).");
            Main.LOGGER.log(e.getMessage());
            Main.LOGGER.writeLog();
        }
    }

    void updateTransaction(String name, double amount, long time, int account, int id){
        try {
            updateTransaction.setString(1, name);
            updateTransaction.setDouble(2, amount);
            updateTransaction.setLong(3, time);
            updateTransaction.setInt(4, account);
            updateTransaction.setInt(5, id);
            updateTransaction.executeUpdate();
        } catch (SQLException e) {
            Main.LOGGER.log("ERROR: Code 60x (Updating transaction failed).");
            Main.LOGGER.log(e.getMessage());
            Main.LOGGER.writeLog();
        }
    }

    void deleteTransaction(int id){
        try {
            deleteTransaction.setInt(1, id);
            deleteTransaction.executeUpdate();
        } catch (SQLException e) {
            Main.LOGGER.log("ERROR: Code 60x (Deleting transaction failed).");
            Main.LOGGER.log(e.getMessage());
            Main.LOGGER.writeLog();
        }
    }

    void deleteAccount(int id){
        try {
            deleteAccount.setInt(1, id);
            deleteAccount.executeUpdate();
        } catch (SQLException e) {
            Main.LOGGER.log("ERROR: Code 60x (Deleting account failed).");
            Main.LOGGER.log(e.getMessage());
            Main.LOGGER.writeLog();
        }
    }

    private void dispose(){
        try {
            connection.close();
            Main.LOGGER.log("INFO: Database connection closed.");
        } catch (SQLException e) {
            Main.LOGGER.log("Error Code 602 (Database connection not closed properly).");
            Main.LOGGER.log(e.getMessage());
            Main.LOGGER.writeLog();
        }
    }

    public static Database getDatabase(){
        if(database == null){
            database = new Database();
        }
        return database;
    }

    public static void close(){
        if(database != null){
            database.dispose();
        }
    }
}
