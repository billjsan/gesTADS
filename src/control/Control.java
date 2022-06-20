package src.control;

import src.model.repository.Repository;
import src.ui.UIManager;
import src.util.tools.BroadcastReceiver;
import src.util.tools.GesLogger;
import src.util.tools.Intent;


public class Control extends BroadcastReceiver {

    private final Repository mRepository;
    private final UIManager mUIManager;
    private boolean shourRun = true;
    private Integer currentPrivilege = 0;
    private String currentUser = null;
    private boolean isLoggedIn = false;
    private final String TAG = Control.class.getSimpleName();

    public Control(){
        if(GesLogger.ISLOGABLE) GesLogger.d(TAG, Thread.currentThread().getName() + " Control");
        mRepository = Repository.getInstance();
        mUIManager = UIManager.getInstance();
    }

    /**
     * Entry point application
     */
    public void startApplication(){
        //mUIManager.startUIManager();

        while(shourRun){
            if(!isLoggedIn){
                mUIManager.startLoginUI();
            }else {
                mUIManager.startHomeUI(currentPrivilege);
            }

        }
    }

    @Override
    public void onReceive(Intent intent) {

        if(GesLogger.ISLOGABLE) GesLogger.d(TAG, "onReceive");
        if(intent == null || intent.getAction() == null) return;

        //usecases
        switch (intent.getAction()){
            case Intent.ACTION_LOGIN:
                //apenas mostrar os valores
                String login = intent.getString(Intent.KEY_USERNAME);
                String pass = intent.getString(Intent.KEY_PASSWORD);

                System.out.println("imprimido do broadcast receiver "
                + login + " " + pass);
                isLoggedIn = true;
                //verificar credenciais
                break;

            case Intent.ACTION_LOGOUT:
                //implements
                isLoggedIn = false;
                break;
        }
    }
}