package src.ui;

import src.ui.screen.CadastroScreen;
import src.util.tools.GesLogger;
import src.util.tools.Intent;

public class UIManager {
    private final String TAG = UIManager.class.getSimpleName();

    private static UIManager instance;
    private final GesTADSScreensInterface mInterface;

    private UIManager(){
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE) GesLogger.d(TAG, "instantiation");
        mInterface = UIFactory.getInterface(UIFactory.NO_UI);
    }

    public static UIManager getInstance(){
        if(instance == null){
            instance = new UIManager();
        }
        return instance;
    }

    public void startUIManager() {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "startUIManager");

        //[ICS]
    }

    public void startLoginUI(Intent intent) {
        //[LAS]
        mInterface.loginScreen(intent);
//        new LoginScreen();
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//        executorService.submit(new Runnable() {
//            @Override
//            public void run() {
//                new LoginScreen();
//            }
//        });
    }

    public void startHomeUI(Integer currentPrivilege) {
        //[LAS]
    }

    public void startFirstLoginScreen() {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "startFirstLoginScreen");

        Intent intent = new Intent(Intent.ACTION_UI_FLAG);
        intent.putFlag(Intent.FLAG_FIRST_LOGIN);
        new CadastroScreen(intent);
    }
}