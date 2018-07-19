package de.supresswarnings.moneytable.desktop.data;

import de.supresswarnings.moneytable.desktop.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private Connection connection;

    public Database(){
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
    }

    public void dispose(){
        try {
            connection.close();
            Main.LOGGER.log("INFO: Database connection closed.");
        } catch (SQLException e) {
            Main.LOGGER.log("Error Code 602 (Database connection not closed properly).");
            Main.LOGGER.log(e.getMessage());
            Main.LOGGER.writeLog();
        }
    }
}
