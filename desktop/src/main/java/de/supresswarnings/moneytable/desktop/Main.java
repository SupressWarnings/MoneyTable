package de.supresswarnings.moneytable.desktop;

import de.supresswarnings.moneytable.desktop.settings.Logger;

public class Main {
    public static final Logger LOGGER = new Logger();

    public static void main(String[] args){
        LOGGER.log("Welcome to MoneyTable");
        LOGGER.writeLog();
    }
}
