package src.ui;

import src.util.tools.GesLogger;
import src.util.tools.Intent;

// [CDS] explicar o que a classe faz
public class UIManager {
    private static final String TAG = UIManager.class.getSimpleName();

    private static UIManager instance;
    private final GesTADSSUIInterface mInterface;

    private UIManager() {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE) GesLogger.d(TAG, "UIManager constructor");
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

    @Deprecated
    public void startRemoveUI(Intent intent) {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE) {

            int action = 0;
            boolean extra = false;
            if (intent != null) {
                action = intent.getAction();
                extra = intent.hasExtras();
            }

            GesLogger.d(TAG, "startRemoveUI: intent action: " + action + " has extras: " + extra);
        }

        mInterface.removeScreen(intent);
    }

    public void startSearchUI(Intent intent) {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE) {

            int action = 0;
            boolean extra = false;
            if (intent != null) {
                action = intent.getAction();
                extra = intent.hasExtras();
            }

            GesLogger.d(TAG, "startSearchUI: intent action: " + action + " has extras: " + extra);
        }

        mInterface.searchScreen(intent);
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
}