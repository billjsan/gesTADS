package src.control;

import src.model.repository.Repository;
import src.util.tools.BroadcastReceiver;
import src.util.tools.GesLogger;
import src.util.tools.Intent;


public class Control extends BroadcastReceiver {

    private final Repository mRepository;
    private final String TAG = Control.class.getSimpleName();

    public Control(){
        System.out.println(Thread.currentThread().getName() + " Control");
        mRepository = Repository.getInstance();
    }

    /**
     * Entry point application
     */
    public void startApplication(){
        // mUIManager.startUIManager();
    }

    @Override
    public void onReceive(Intent intent) {

        if(GesLogger.ISLOGABLE) GesLogger.d(TAG, "onReceive");
    }
}