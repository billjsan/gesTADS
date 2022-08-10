package src.ui;

import src.ui.screen.RegisterEmployeeScreen;
import src.util.tools.GesLogger;
import src.util.tools.Intent;

// [CDS] explicar o que a classe faz
public class UIManager {
    private static final String TAG = UIManager.class.getSimpleName();

    private static UIManager instance;
    private final GesTADSSUIInterface mInterface;

    private UIManager(){
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE) GesLogger.d(TAG, "UIManager constructor");
        mInterface = UIFactory.getInterface(UIFactory.NO_UI);
    }

    public static UIManager getInstance(){
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "UIManager getInstance");
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
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "startLoginUI");
        mInterface.loginScreen(intent);
    }

    public void startHomeUI(Intent intent) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "startHomeUI "+ intent);

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
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "startMainUI "+ intent);
        mInterface.mainScreen(intent);
    }

    public void startRegisterUI(Intent intent) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "startRegisterUI "+ intent);
        mInterface.registerScreen(intent);
    }

    public void startDialogUI(Intent intent) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "startDialogUI "+ intent);
        mInterface.dialogScreen(intent);
    }

    @Deprecated
    public void startRemoveUI(Intent intent) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "startRemoveUI "+ intent);
        mInterface.removeScreen(intent);
    }

    public void startSearchUI(Intent intent) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "StartSearchUI "+ intent);
        mInterface.searchScreen(intent);
    }

    public void startSearchEmployeeUI(Intent intent) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "startSearchEmployeeUI "+ intent);
        mInterface.searchEmployeeScreen(intent);
    }

    public void startRegisterProductScreen(Intent intent) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "startRegisterProductScreen "+ intent);
        mInterface.registerProductScreen(intent);
    }

    public void startSearchProductUI(Intent intent) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "startSearchProductUI "+ intent);

        mInterface.searchProductScreen(intent);
    }
}