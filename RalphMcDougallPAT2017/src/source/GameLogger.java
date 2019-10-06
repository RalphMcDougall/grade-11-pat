/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package source;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Ralph McDougall 22/10/2017
 */
public class GameLogger {
    public static enum LEVEL {ERROR, WARNING, INFO};
    private static ArrayList<String> log = new ArrayList<String>();
    private static LEVEL flagLevel = LEVEL.INFO;
    
    private static void log(LEVEL importance, String msg)
    {
        boolean display = false;
        
        switch (flagLevel)
        {
            case ERROR:
                if (importance == LEVEL.ERROR)
                {
                    display = true;
                }
                break;
            case WARNING:
                if (importance == LEVEL.WARNING || importance == LEVEL.ERROR)
                {
                    display = true;
                }
                break;
            case INFO:
                if (importance == LEVEL.INFO || importance == LEVEL.WARNING || importance == LEVEL.ERROR)
                {
                    display = true;
                }
                break;
            default:
                break;
        }
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        
        String lvl = "";
        switch (importance)
        {
            case ERROR:
                lvl = "ERROR";
                break;
            case WARNING:
                lvl = "WARNING";
                break;
            case INFO:
                lvl = "INFO";
                break;
            default:
                lvl = "*UNKNOWN IMPORTANCE LEVEL*";
                break;
        }
        
        String logMsg = "<" + dateFormat.format(date) + "> " + lvl + " : " + msg;
        
        if (display)
        {
            System.out.println(logMsg);
        }
        log.add(logMsg);
    }
    
    public static void logInfo(String s)
    {
        log(LEVEL.INFO, s);
    }
    public static void logWarning(String s)
    {
        log(LEVEL.WARNING, s);
    }
    public static void logError(String s)
    {
        log(LEVEL.ERROR, s);
        
        // Flush to the file after an error is detected
        flush();
    }
    
    public static void setFlagLevel(LEVEL lvl)
    {
        flagLevel = lvl;
    }
    
    public static LEVEL getFlagLevel()
    {
        return flagLevel;
    }
    
    public static void flush()
    {
        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter("src/resources/log.txt"));
            for (int i = 0; i < log.size(); ++i)
            {
                writer.write(log.get(i) + "\r\n");
            }
     
            writer.close();
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog(null, "Unable to flush to file.\n" + e.toString());
            System.out.println("Unable to flush logger to file :(");
        }
    }
    
}
