package src.util.tools;

import java.util.ArrayList;
import java.util.List;

public abstract class BroadcastReceiver {
    private final static String TAG = BroadcastReceiver.class.getSimpleName();
    private static final List<BroadcastReceiver> receivers = new ArrayList<>();

    public BroadcastReceiver(){
        receivers.add(this);
    }

    protected void unregisterBroadcast(BroadcastReceiver br){
        receivers.remove(br);
    }

    protected abstract void onReceive(Intent intent);

    public static void sendBroadcast(Intent i){
        if(GesLogger.ISFULLLOGABLE) GesLogger.d(TAG, "sendBroadcast");
        for (BroadcastReceiver b : receivers) {
            b.onReceive(i);
        }
    }
}