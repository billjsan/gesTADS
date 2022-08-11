package src.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GesLogger {
    public static final String TAG = "GesTADSApp";
    public static final boolean ISFULLLOGABLE = false;
    public static final boolean ISSENSITIVELOGABLE = false;
    public static final boolean ISSAFELOGGABLE = false;
    public static final boolean ISERRORLOGABLE = true;

    private static boolean shoutPrintHeader = true;
    private static void printHeader(){
        System.out.println("[Date and time] [Application] [type] [Class] [Current thread] [Message log]");
    }
    public static void d(String subtag, String message){
        if(shoutPrintHeader) {
            printHeader();
            shoutPrintHeader = false;
        }
        System.out.println("[" + getCurrentTimeFormatted() + "] [" + TAG + "] [D] [" + subtag + "] " + message);
    }

    public static void d(String subtag, Thread thread, String message) {
        if(shoutPrintHeader) {
            printHeader();
            shoutPrintHeader = false;
        }
        System.out.println("[" + getCurrentTimeFormatted() + "] [" + TAG + "] [D] [" + subtag + "] ["
                + thread.getName() + "] " + message);
    }
    public static void e(String subtag, String message){
        if(shoutPrintHeader) {
            printHeader();
            shoutPrintHeader = false;
        }
        System.err.println("[" + getCurrentTimeFormatted() + "] [" + TAG + "] [E] [" + subtag + "] " + message);
    }

    private static String getCurrentTimeFormatted() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyy HH:mm:ss");
        return dtf.format(LocalDateTime.now());
    }
}