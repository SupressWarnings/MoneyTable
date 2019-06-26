package de.supresswarnings.moneytable.desktop.settings;

import java.io.*;

/**
 * This custom-made Logger creates a text file (.txt). It helps understanding errors in production versions.
 *
 * @author Constantin Schulte
 */
public class Logger {

    /**
     * The object representation of the log file.
     */
    private File logFile;

    /**
     * The (new) content of the log file.
     */
    private String log;

    /**
     * To create time stamps, the start time of the application is important.
     */
    private long startTime = System.currentTimeMillis();

    /**
     * Initializes the log-String and the logFile-File.
     * This includes creating a new file on the hard-drive if it does not exist yet.
     */
    public Logger(){
        log = "";
        logFile = new File(System.getProperty("user.home") + "/.moneytable/logging/log.txt");
        if(!logFile.exists()){
            if(!logFile.getParentFile().mkdirs()){
                System.err.println("Failed to create directory for log file.");
            }
            try {
                if(!logFile.createNewFile()){
                    System.err.println("Failed to create log file.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            try (PrintWriter writer = new PrintWriter(new FileOutputStream(logFile, false))){
                writer.write("");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        log("INFO: Logging started");
    }

    /**
     * Adds a specific event description to the log-String, together with its timestamp.
     *
     * @param logEntry the description of the event
     */
    public void log(String logEntry){
        long logTime = System.currentTimeMillis() - startTime;
        String timePart = "[" + (logTime / (1000*60*60)) + ":";
        logTime = logTime % (1000*60*60);
        timePart += logTime / (1000*60) + ":";
        logTime = logTime % (1000*60);
        timePart += logTime / 1000 + ".";
        logTime = logTime % 1000;
        timePart += logTime + "] ";
        log += timePart + logEntry + "\n";
    }

    /**
     * Logs an exception as a description and the actual error message.
     *
     * @param logEntry the message about the error, especially containing an error code to identify its root
     * @param e the actual exception, which message will be logged too
     */
    public void logException(String logEntry, Exception e){
        log(logEntry);
        log(e.getMessage());
        writeLog();
    }

    /**
     * Appends the log-String to the logFile-File.
     * Necessary for the log to be permanently stored.
     */
    public void writeLog() {
        try(PrintWriter stream = new PrintWriter(new FileOutputStream(logFile, true))) {
            stream.append(log);
            log = "";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
