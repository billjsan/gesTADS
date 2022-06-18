package src.ui;

import src.util.tools.BroadcastReceiver;
import src.util.tools.GesLogger;
import src.util.tools.Intent;

public class TesteUI extends GesTADSUI{

    private final String TAG = "TesteUI";

    @Override
    public void createView() {
        if(GesLogger.ISLOGABLE) GesLogger.d(TAG, "GesTADSUI child createView");
        Intent intent = new Intent(Intent.ACTION_LOGIN);
        intent.putInt("will", 28);
        BroadcastReceiver.sendBroadcast(intent);
    }
}