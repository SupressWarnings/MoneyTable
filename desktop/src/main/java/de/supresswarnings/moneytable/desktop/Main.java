package de.supresswarnings.moneytable.desktop;

import de.supresswarnings.moneytable.desktop.settings.Logger;

public class Main {
    public static final Logger LOGGER = new Logger();

    public static void main(String[] args){
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            LOGGER.log("Error Code 501 (Class not found).");
            LOGGER.writeLog();
            System.exit(1);
        }
    }
}
