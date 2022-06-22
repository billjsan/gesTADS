package src.ui;

import src.ui.screen.CadastroScreen;
import src.ui.screen.LoginScreen;
import src.util.tools.GesLogger;
import src.util.tools.Intent;

public class UIManager {
    private final String TAG = UIManager.class.getSimpleName();

    private static UIManager instance;

    private UIManager(){
        if(GesLogger.ISFULLLOGABLE) GesLogger.d(TAG, "UIManager instantiation");
    }

    public static UIManager getInstance(){
        if(instance == null){
            instance = new UIManager();
        }
        return instance;
    }

    public void startUIManager() {

        if(GesLogger.ISFULLLOGABLE) GesLogger.d(TAG, "startUIManager");
        //new LoginScreen();

        //new LoginGUI().createView();
    }

    public void startLoginUI() {
        new LoginScreen();
    }

    public void startHomeUI(Integer currentPrivilege) {
    }

    public void startFirstLoginScreen() {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "startFirstLoginScreen");

        Intent intent = new Intent(Intent.ACTION_UI_FLAG);
        intent.putFlag(Intent.FLAG_FIRST_LOGIN);
        new CadastroScreen(intent);
    }
}