package src.util.tools;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GesLogger {
    public static final String TAG = "GesTADS";
    public static final boolean ISSENSITIVELOGABLE = false;
    public static final boolean ISFULLLOGABLE = false;
    public static final boolean ISSAFELOGGABLE = false;

    public static void d(String subtag, String message){
        System.out.println(getCurrentTimeFormatted() + " [" + TAG + "] [" + subtag + "] " + message);
    }

    public static void d(String subtag, Thread thread, String message) {
        System.out.println(getCurrentTimeFormatted() + " [" + TAG + "] [" + subtag + "] [Thread: "
                + thread.getName() + "] " + message);
    }
    public static void e(String subtag, String message){
        System.err.println(getCurrentTimeFormatted() + " [" + TAG + "] [" + subtag + "] " + message);
    }

    private static String getCurrentTimeFormatted() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyy HH:mm:ss");
        return dtf.format(LocalDateTime.now());
    }
}