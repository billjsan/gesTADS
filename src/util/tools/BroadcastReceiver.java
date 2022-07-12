package src.util.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class BroadcastReceiver {
    private final static String TAG = BroadcastReceiver.class.getSimpleName();
    private static final List<BroadcastReceiver> receivers = new ArrayList<>();
    private static ExecutorService mExecutor = null;

    protected BroadcastReceiver() {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE) GesLogger.d(
                TAG, Thread.currentThread(), "BroadcastReceiver constructor");

        receivers.add(this);
        if (mExecutor == null) {
            mExecutor = Executors.newFixedThreadPool(3);
        }
    }

    protected static void shutdownBroadcastExecutors() {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE) GesLogger.d(
                TAG, Thread.currentThread(), "shutdownBroadcastExecutors");
        mExecutor.shutdown();
        mExecutor.shutdownNow();
    }

    protected abstract void onReceive(Intent intent);

    public static void sendBroadcast(Intent intent) {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE) GesLogger.d(
                TAG, Thread.currentThread(), "sendBroadcast");

        if (mExecutor == null) {
            if (GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE) GesLogger.e(
                    TAG, "Executor Service not initialized. No broadcasts listener registered");
            return;
        }

        mExecutor.submit(() -> {
            for (BroadcastReceiver bv : receivers) {
                bv.onReceive(intent);
            }
        });
    }
}