package de.supresswarnings.moneytable.desktop.settings;

import java.io.*;

public class Logger {

    private File logFile;
    private String log;
    private long startTime = System.currentTimeMillis();

    public Logger(){
        log = "";
        logFile = new File(System.getProperty("user.home") + "/.moneytable/logging/log.txt");
        if(!logFile.exists()){
            logFile.getParentFile().mkdirs();
            try {
                logFile.createNewFile();
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

    public void writeLog() {
        try(PrintWriter stream = new PrintWriter(new FileOutputStream(logFile, true));) {
            stream.append(log);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
