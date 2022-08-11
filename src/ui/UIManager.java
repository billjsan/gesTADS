package src.ui;

import src.ui.screen.GesTADSScreensImpl;
import src.util.GesLogger;
import src.util.Intent;

// [CDS] explicar o que a classe faz
public class UIManager {
    private static final String TAG = UIManager.class.getSimpleName();

    private static UIManager instance;
    private final GesTADSSUIInterface mInterface;

    private UIManager() {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "UIManager constructor");

        mInterface = UIFactory.getInterface(UIFactory.NO_UI);
    }

    public static UIManager getInstance() {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "UIManager getInstance");
        if (instance == null) {
            instance = new UIManager();
        }
        return instance;
    }

    public void startUIManager() {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "startUIManager");

        //[ICS]
    }

    public void startLoginUI(Intent intent) {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE) {

            int action = 0;
            boolean extra = false;
            if (intent != null) {
                action = intent.getAction();
                extra = intent.hasExtras();
            }

            GesLogger.d(TAG, "startLoginUI: intent action: " + action + " has extras: " + extra);
        }

        mInterface.loginScreen(intent);
    }

    public void startHomeUI(Intent intent) {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE) {

            int action = 0;
            boolean extra = false;
            if (intent != null) {
                action = intent.getAction();
                extra = intent.hasExtras();
            }

            GesLogger.d(TAG, "startHomeUI: intent action: " + action + " has extras: " + extra);
        }

        mInterface.homeScreen(intent);
    }

    public void startMainUI(Intent intent) {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE) {

            int action = 0;
            boolean extra = false;
            if (intent != null) {
                action = intent.getAction();
                extra = intent.hasExtras();
            }

            GesLogger.d(TAG, "startMainUI: intent action: " + action + " has extras: " + extra);
        }

        mInterface.mainScreen(intent);
    }

    public void startRegisterUI(Intent intent) {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE) {

            int action = 0;
            boolean extra = false;
            if (intent != null) {
                action = intent.getAction();
                extra = intent.hasExtras();
            }

            GesLogger.d(TAG, "startRegisterUI: intent action: " + action + " has extras: " + extra);
        }

        mInterface.registerScreen(intent);
    }

    public void startDialogUI(Intent intent) {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE) {

            int action = 0;
            boolean extra = false;
            if (intent != null) {
                action = intent.getAction();
                extra = intent.hasExtras();
            }

            GesLogger.d(TAG, "startDialogUI: intent action: " + action + " has extras: " + extra);
        }

        mInterface.dialogScreen(intent);
    }

    public void startSearchEmployeeUI(Intent intent) {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE) {

            int action = 0;
            boolean extra = false;
            if (intent != null) {
                action = intent.getAction();
                extra = intent.hasExtras();
            }

            GesLogger.d(TAG, "startSearchEmployeeUI: intent action: " + action + " has extras: " + extra);
        }

        mInterface.searchEmployeeScreen(intent);
    }

    public void startRegisterProductScreen(Intent intent) {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE) {

            int action = 0;
            boolean extra = false;
            if (intent != null) {
                action = intent.getAction();
                extra = intent.hasExtras();
            }

            GesLogger.d(TAG, "startRegisterProductScreen: intent action: " + action + " has extras: " + extra);
        }

        mInterface.registerProductScreen(intent);
    }

    public void startSearchProductUI(Intent intent) {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE) {

            int action = 0;
            boolean extra = false;
            if (intent != null) {
                action = intent.getAction();
                extra = intent.hasExtras();
            }

            GesLogger.d(TAG, "startSearchProductUI: intent action: " + action + " has extras: " + extra);
        }

        mInterface.searchProductScreen(intent);
    }

    public void startTransactionUI(Intent intent) {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE) {

            int action = 0;
            boolean extra = false;
            if (intent != null) {
                action = intent.getAction();
                extra = intent.hasExtras();
            }

            GesLogger.d(TAG, "startTransactionUI: intent action: " + action + " has extras: " + extra);
        }

        mInterface.transactionScreen(intent);
    }

    // [CDS] explicar o que a classe faz
    private static class UIFactory {

        private static final String TAG = "UIFactory";
        static final int NO_UI = 1;
        static final int SWING_UI = 2;

        static GesTADSSUIInterface getInterface(int ui) {
            if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
                GesLogger.d(TAG, Thread.currentThread(), "getInterface");

            return ui == SWING_UI ? getSwingUI() : getNoUI();

        }

        private static GesTADSSUIInterface getSwingUI() {
            if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
                GesLogger.d(TAG, Thread.currentThread(), "getSingUI");
            return null;
        }

        private static GesTADSSUIInterface getNoUI() {
            if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
                GesLogger.d(TAG, Thread.currentThread(), "getNoUI");
            return new GesTADSScreensImpl();
        }
    }
}