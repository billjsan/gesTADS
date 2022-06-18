package src.util.tools;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GesLogger {
    public static final boolean ISLOGABLE = true;
    public static final String TAG = "GesTADS";

    public static void d(String subtag, String message){
        System.out.println(getCurrentTimeFormatted() + " [" + TAG + "] [" + subtag + "] " + message);
    }

    private static String getCurrentTimeFormatted() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyy HH:mm:ss");
        return dtf.format(LocalDateTime.now());
    }
}