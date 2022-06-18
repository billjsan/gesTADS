package src.ui;

import src.util.tools.GesLogger;

public class UIManager {
    private final String TAG = UIManager.class.getSimpleName();

    private static UIManager instance;

    private UIManager(){
        if(GesLogger.ISLOGABLE) GesLogger.d(TAG, "UIManager instantiation");
    }

    public static UIManager getInstance(){
        if(instance == null){
            instance = new UIManager();
        }
        return instance;
    }

    public void startUIManager() {

        if(GesLogger.ISLOGABLE) GesLogger.d(TAG, "startUIManager");
        new TesteUI();
    }
}