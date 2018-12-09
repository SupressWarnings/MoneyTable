package de.supresswarnings.moneytable.desktop.settings;

import org.junit.Test;

public class LoggerTest {

    @Test
    public void test(){
        Logger logger = new Logger();
        logger.log("test");
        logger.writeLog();
        logger.logException("test", new Exception());
        logger = new Logger();
    }
}
