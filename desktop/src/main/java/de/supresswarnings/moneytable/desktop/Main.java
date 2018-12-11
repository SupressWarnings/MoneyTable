package de.supresswarnings.moneytable.desktop;

import de.supresswarnings.moneytable.desktop.settings.Logger;

/**
 * Contains the start method for the application.
 *
 * @author Constantin Schulte
 * @version 0.1-20181211
 */
public class Main {
    /**
     * The project-level logger object.
     */
    public static final Logger LOGGER = new Logger();

    /**
     * Starts the application.
     *
     * @param args (irrelevant so far)
     */
    public static void main(String[] args){
        LOGGER.log("Welcome to MoneyTable");
        LOGGER.writeLog();
    }
}
