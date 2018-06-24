package de.supresswarnings.moneytable.desktop.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private Connection connection;

    public Database(){
        String path = System.getProperty("user.home") + "/.moneytable/database";
        try {
            connection = DriverManager.getConnection("jdbc:h2:" + path, "", "");
            Initializer initializer = new Initializer(connection);
            initializer.checkTables();
        } catch (SQLException e) {
            System.err.println("Error Code 601 (Database Connection error).");
            e.printStackTrace();
        }
    }

    public void dispose(){
        try {
            connection.close();
        } catch (SQLException e) {
            System.err.println("Error Code 602 (Database connection not closed properly).");
            e.printStackTrace();
        }
    }
}
