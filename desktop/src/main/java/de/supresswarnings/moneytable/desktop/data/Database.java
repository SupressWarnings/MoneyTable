package de.supresswarnings.moneytable.desktop.data;

import de.supresswarnings.moneytable.desktop.Main;
import de.supresswarnings.moneytable.model.Account;
import de.supresswarnings.moneytable.model.transaction.Transaction;
import de.supresswarnings.moneytable.model.transaction.TransactionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The class Database provides the SQL-connection to the database of the Moneytable-application.
 *
 * @author Constantin Schulte
 */
class Database {

    /**
     * To ensure that the Database is constructed one time only, there is a single static object of it.
     */
    private static Database database;

    /**
     * The {@link Connection} from the java.sql-package is needed for the connection.
     */
    private Connection connection;

    /**
     * This {@link PreparedStatement} is prepared to prevent SQL-injections when an account is created.
     * It has two variable values: the name (String) and the current(double) of the account.
     */
    private PreparedStatement createAccount;

    /**
     * This {@link PreparedStatement} is prepared to prevent SQL-injections when an account is updated.
     * It has three variable values: the new name(String), current(double) and the old id(int) of the account.
     */
    private PreparedStatement updateAccount;

    /**
     * This {@link PreparedStatement} is prepared to prevent SQL-injections when an account is deleted.
     * It has one variable value: the id(int) of the account.
     */
    private PreparedStatement deleteAccount;

    /**
     * This {@link Statement} is prepared to accelerate the process of getting all accounts loaded.
     */
    private Statement getAccounts;

    /**
     * This {@link PreparedStatement} is prepared to prevent SQL-injections when getting an account which is identified by its name.
     * It has three one value: the name(String) of the requested account.
     */
    private PreparedStatement getAccountByName;

    /**
     * This {@link PreparedStatement} is prepared to prevent SQL-injections when you request the id of an account.
     * It has one variable value: the id(int) of the account.
     */
    private PreparedStatement getAccountById;


    /**
     * This {@link PreparedStatement} is prepared to prevent SQL-injections when a transaction is created.
     * It has four variable values: the name(String), amount(double), the time(long) and the account id(int) of the transaction.
     */
    private PreparedStatement createTransaction;

    /**
     * This {@link PreparedStatement} is prepared to prevent SQL-injections when a transaction is updated.
     * It has four variable values: the new name(String), new amount(double), the new time(long) and the old id(int) of the transaction.
     */
    private PreparedStatement updateTransaction;

    /**
     * This {@link PreparedStatement} is prepared to prevent SQL-injections when a transaction is deleted.
     * It has one variable value: id(int) of the transaction.
     */
    private PreparedStatement deleteTransaction;

    /**
     * This {@link PreparedStatement} is prepared to prevent SQL-injections when getting all transactions of an account.
     * It has one variable value: the account id(int) of the transactions.
     */
    private PreparedStatement getTransactionsByAccount;

    /**
     * This {@link PreparedStatement} is prepared to prevent SQL-injections when a transactionId is requested.
     * It has four variable values: the name(String), amount(double), the time(long) of the transaction.
     */
    private PreparedStatement getTransactionId;

    private PreparedStatement getTransaction;

    /**
     * The constructor of the class Database is private to prevent multiple instantiations of it.
     *
     * It connects to the database files by instantiating the @Connection
     * It then proceeds to check whether all required tables exist with the help of the @Initializer class.
     * At the end, the @initialize method is called.
     */
    private Database(){
        String path = System.getProperty("user.home") + "/.moneytable/database";
        try {
            connection = DriverManager.getConnection("jdbc:h2:" + path, "userame", "passord");
            Main.LOGGER.log("INFO: Database connection established.");
            Initializer initializer = new Initializer(connection);
            initializer.checkTables();
            Main.LOGGER.log("INFO: Database initialization completed");
        } catch (SQLException e) {
            Main.LOGGER.logException("Error Code 601 (Database Connection error).", e);
            Main.LOGGER.writeLog();
        }
        initialize();
    }

    /**
     * This method prepares all SQL statements when the Database object is created. It is only called by the constructor.
     */
    private void initialize(){
        try{
            createAccount = connection.prepareStatement("INSERT INTO account VALUES(DEFAULT, ?, ?)");
            updateAccount = connection.prepareStatement("UPDATE account SET name = ?, current = ? WHERE id = ?");
            deleteAccount = connection.prepareStatement("DELETE FROM account WHERE id = ?");
            getAccounts = connection.createStatement();
            getAccountByName = connection.prepareStatement("SELECT * FROM account WHERE name = ?");
            getAccountById = connection.prepareStatement("SELECT * FROM account WHERE id = ?");

            createTransaction = connection.prepareStatement("INSERT INTO transaction VALUES(?, ?, ?, ?, ?)");
            updateTransaction = connection.prepareStatement("UPDATE transaction SET name = ?, amount = ?, time = ? WHERE id = ?");
            deleteTransaction = connection.prepareStatement("DELETE FROM transaction WHERE id = ?");
            getTransactionsByAccount = connection.prepareStatement("SELECT * FROM transaction WHERE account = ?");
            getTransactionId = connection.prepareStatement("SELECT id FROM transaction WHERE name = ? AND amount = ? AND time = ? AND account = ?");
            getTransaction = connection.prepareStatement("SELECT * FROM transaction WHERE id = ?");
        } catch (SQLException e) {
            Main.LOGGER.logException("ERROR: Code 602 (Preparing statements failed).", e);
            Main.LOGGER.writeLog();
        }
    }

    /**
     * This method creates a new account in the Database.
     *
     * @param name is the username of the account
     * @param current is the current of the account right now
     */
    void createAccount(String name, double current){
        try {
            createAccount.setString(1, name);
            createAccount.setDouble(2, current);
            createAccount.execute();
        } catch (SQLException e) {
            Main.LOGGER.logException("ERROR: Code 603 (Creating account failed).", e);
            Main.LOGGER.writeLog();
        }
    }

    /**
     * This method updates an account in the Database.
     *
     * @param name the new username of the account
     * @param current the new current of the account
     * @param id the id by which the account is identified
     */
    void updateAccount(String name, double current, int id){
        try {
            updateAccount.setString(1, name);
            updateAccount.setDouble(2, current);
            updateAccount.setInt(3, id);
            updateAccount.executeUpdate();
        } catch (SQLException e) {
            Main.LOGGER.logException("ERROR: Code 604 (Updating account failed).", e);
            Main.LOGGER.writeLog();
        }
    }

    /**
     * Deletes an account from the Database.
     * @param id the id of the account that will be deleted
     */
    void deleteAccount(int id){
        try {
            deleteAccount.setInt(1, id);
            deleteAccount.executeUpdate();
        } catch (SQLException e) {
            Main.LOGGER.logException("ERROR: Code 605 (Deleting account failed).", e);
            Main.LOGGER.writeLog();
        }
    }

    /**
     * Creates accounts from the saved data. These accounts only contain username and current but none of the transactions.
     *
     * @return an {@link ArrayList} containing Account objects with names and currents but without transactions
     */
    List<Account> getAccounts(){
        ArrayList<Account> accounts = new ArrayList<>();
        try(ResultSet set = getAccounts.executeQuery("SELECT * FROM account")){
            while(set.next()){
                accounts.add(new Account(set.getString(2), set.getDouble(3)));
            }
        } catch (SQLException e) {
            Main.LOGGER.logException("ERROR: Code 606 (Loading accounts failed).", e);
        }
        return accounts;
    }

    /**
     * Creates an account from the saved data containing username, current and all transactions affiliated with this account.
     *
     * @param id the id of the account that is requested
     * @return an Account object containing all existing information of it
     *          (null if an error occurs)
     */
    Account getAccount(int id){
        Account account = null;
        ResultSet set = null;
        try {
            if(id != -1){
                getAccountById.setInt(1, id);
                set = getAccountById.executeQuery();
                if(set.next()){
                    account = new Account(set.getString(2), set.getDouble(3));
                    for(Transaction transaction : getTransactions(set.getInt(1))){
                        account.add(transaction);
                    }
                }
            }else{
                Main.LOGGER.log("ERROR: Code xxx (Account id invalid)"); //todo
            }
        } catch (SQLException e) {
            Main.LOGGER.logException("ERROR: Code 607 (Loading account failed).", e);
        }finally{
            try {
                if(set!= null){
                    set.close();
                }
            } catch (SQLException e) {
                Main.LOGGER.logException("ERROR: Code 608 (Closing ResourceSet failed).", e);
            }
        }
        return account;
    }

    /**
     * Returns the id of the account with a specific username.
     *
     * @param name the username of the account
     * @return the id of the account with this username
     */
    int getAccountId(String name){
        int id = -1;
        ResultSet set = null;
        try {
            getAccountByName.setString(1, name);
            set = getAccountByName.executeQuery();
            if(set.next()){
                id = set.getInt(1);
            }
        } catch (SQLException e) {
            Main.LOGGER.logException("ERROR: Code 609 (Getting account id failed).", e);
        }finally{
            try {
                if(set!= null){
                    set.close();
                    System.err.println("Result Set geschlossen");
                }
            } catch (SQLException e) {
                Main.LOGGER.logException("ERROR: Code 610 (Closing ResourceSet failed).", e);
            }
        }
        return id;
    }

    /**
     * This method creates a new Transaction in the Database.
     *
     * @param name is the reason/name of the transaction
     * @param amount is the amount of money
     *               (negative if it is an expense,
     *               positive if it is income)
     * @param time the timestamp of the transaction
     * @param account every transaction is linked to an account by id
     */
    void createTransaction(long id, String name, double amount, long time, int account){
        try {
            createTransaction.setLong(1, id);
            createTransaction.setString(2, name);
            createTransaction.setDouble(3, amount);
            createTransaction.setLong(4, time);
            createTransaction.setInt(5, account);
            createTransaction.execute();
        } catch (SQLException e) {
            Main.LOGGER.logException("ERROR: Code 611 (Creating transaction failed).", e);
        }
    }

    /**
     * This method updates an existing transaction in the Database or creates a new one if it does not exist.
     *
     * @param name the new reason/name of the transaction
     * @param amount the new amount of the transaction
     * @param time the new time of the transaction
     */
    void updateTransaction(long id, String name, double amount, long time){
        try {
            if(getTransaction(id) != null){
                updateTransaction.setString(1, name);
                updateTransaction.setDouble(2, amount);
                updateTransaction.setLong(3, time);
                updateTransaction.setLong(4, id);
                updateTransaction.executeUpdate();
            }else{
                Main.LOGGER.log("ERROR: Code 612 (Updated transaction does not exist).");
            }
        } catch (SQLException e) {
            Main.LOGGER.logException("ERROR: Code 613 (Updating transaction failed).", e);
        }
    }

    /**
     * Deletes a transaction from the Database.
     *
     * @param id the id of the transaction that will be deleted
     */
    void deleteTransaction(long id){
        try {
            deleteTransaction.setLong(1, id);
            deleteTransaction.executeUpdate();
        } catch (SQLException e) {
            Main.LOGGER.logException("ERROR: Code 614 (Deleting transaction failed).", e);
        }
    }

    /**
     * This method creates an {@link ArrayList} containing all Transactions affiliated with one specified account.
     *
     * @param accountId the id of the account
     * @return an {@link ArrayList} containing all Transactions in the Database that are affiliated with this account
     */
    List<Transaction> getTransactions(long accountId){
        ArrayList<Transaction> transactions = new ArrayList<>();
        ResultSet set = null;
        try {
            getTransactionsByAccount.setLong(1, accountId);
            set = getTransactionsByAccount.executeQuery();
            while(set.next()){
                Transaction transaction = TransactionFactory.createTransaction(set.getString(2), set.getDouble(3), set.getLong(4));
                transaction.setId(set.getInt(1));
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            Main.LOGGER.logException("ERROR: Code 615 (Transaction call failed).", e);
        }finally{
            try {
                if(set!= null){
                    set.close();
                }
            } catch (SQLException e) {
                Main.LOGGER.logException("ERROR: Code 616 (Closing ResourceSet failed).", e);
            }
        }
        return transactions;
    }

    /**
     * Gives the id of an transaction with the specified details.
     *
     * @param name the reason/name of the transaction
     * @param amount the amount of the transaction
     * @param time the timestamp of the transaction
     * @param accountId the id of the account of the transaction
     *
     * @return the id of the transaction with the specified information or -1 if the transaction does not exist
     */
    long getTransactionId(String name, double amount, long time, int accountId){
        int id = -1;
        ResultSet set = null;
        try {
            getTransactionId.setString(1, name);
            getTransactionId.setDouble(2, amount);
            getTransactionId.setLong(3, time);
            getTransactionId.setInt(4, accountId);

            set = getTransactionId.executeQuery();
            if(set.next()){
                id = set.getInt(1);
            }
        } catch (SQLException e) {
            Main.LOGGER.logException("ERROR: Code 617 (Getting transaction failed).", e);
        }finally{
            try {
                if(set!= null){
                    set.close();
                }
            } catch (SQLException e) {
                Main.LOGGER.logException("ERROR: Code 618 (Closing ResourceSet failed).", e);
            }
        }
        return id;
    }

    Transaction getTransaction(long id){
        ResultSet set = null;
        try{
            if(id != -1){
                getTransaction.setLong(1, id);
                set = getTransaction.executeQuery();
                if(set.next()){
                    Transaction transaction = TransactionFactory.createTransaction(set.getString(2), set.getDouble(3), set.getLong(4));
                    transaction.setId(set.getInt(1));
                    return transaction;
                }else{
                    Main.LOGGER.log("ERROR: Code xxx");
                    Main.LOGGER.log("Transaction not found");
                }
            }else{
                Main.LOGGER.log("ERROR: Code xxx"); //todo
                Main.LOGGER.log("Transaction id invalid");
            }
        } catch (SQLException e) {
            Main.LOGGER.logException("adsfa", e); //TODO
        }finally {
            if(set != null){
                try {
                    set.close();
                } catch (SQLException e) {
                    Main.LOGGER.logException("asdfa", e); //TODO
                }
            }
        }
        return null;
    }

    /**
     * Closes all resources when the application is closed.
     */
    private void dispose(){
        try {
            connection.close();
            Main.LOGGER.log("INFO: Database connection closed.");
        } catch (SQLException e) {
            Main.LOGGER.logException("Error Code 619 (Database connection not closed properly).", e);
        }
    }

    /**
     * Gives the single Database object or, if it does not exist, creates it.
     *
     * @return the single Database instantiation
     */
    static Database getDatabase() {
        if (database == null) {
            database = new Database();
        }
        return database;
    }

    /**
     * This method is called when the application is closed so the single Database object closes the connection to the file system.
     */
    static void close(){
        if(database != null){
            database.dispose();
            database = null;
        }
    }
}
