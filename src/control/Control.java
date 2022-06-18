package src.control;

import src.model.repository.Repository;
import src.ui.UIManager;
import src.util.tools.BroadcastReceiver;
import src.util.tools.GesLogger;
import src.util.tools.Intent;

public class Control extends BroadcastReceiver {

    private UIManager mUIManager;
    private Repository mRepository;
    private final String TAG = Control.class.getSimpleName();

    public Control(){
        mUIManager = UIManager.getInstance();
        mRepository = Repository.getInstance();
    }

    /**
     * Entry point application
     */
    public void startApplication(){
       mUIManager.startUIManager();
    }


    @Override
    public void onReceive(Intent intent) {

        if(GesLogger.ISLOGABLE) GesLogger.d(TAG, "control onReceive");
        if(intent == null){
            return;
        }
        if(intent.getAction() != Intent.ACTION_LOGIN) {
            return;
        }

        if(GesLogger.ISLOGABLE) GesLogger.d(TAG, "valor recebido via intent "
                + intent.getInt("will"));
    }
}