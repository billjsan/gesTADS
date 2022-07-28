package src.ui;

import src.ui.screen.RegisterEmployeeScreen;
import src.util.tools.GesLogger;
import src.util.tools.Intent;

// [CDS] explicar o que a classe faz
public class UIManager {
    private final String TAG = UIManager.class.getSimpleName();

    private static UIManager instance;
    private final GesTADSSUIInterface mInterface;

    private UIManager(){
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE) GesLogger.d(TAG, "UIManager constructor");
        mInterface = UIFactory.getInterface(UIFactory.NO_UI);
    }

    public static UIManager getInstance(){
        //[LAS]
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
    }

    public void startHomeUI(Intent intent) {
        //[LAS]

        mInterface.homeScreen(intent);
    }

    public void startFirstLoginScreen() {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "startFirstLoginScreen");

        Intent intent = new Intent(Intent.ACTION_UI_FLAG);
        intent.putFlag(Intent.FLAG_FIRST_LOGIN);
        new RegisterEmployeeScreen(intent);
    }

    public void startMainUI(Intent intent) {
        //[LAS]
        mInterface.mainScreen(intent);
    }

    public void startRegisterUI(Intent intent) {
        //[LAS]
        mInterface.registerScreen(intent);
    }

    public void startDialogUI(Intent intent) {
        //[LAS]
        mInterface.dialogScreen(intent);
    }

    @Deprecated
    public void startRemoveUI(Intent intent) {
        //[LAS]
        mInterface.removeScreen(intent);
    }

    public void startSearchUI(Intent intent) {
        //[LAS]
        mInterface.searchScreen(intent);
    }

    public void startSearchEmployeeUI(Intent intent) {
        //[LAS]
        mInterface.searchEmployeeScreen(intent);
    }
}