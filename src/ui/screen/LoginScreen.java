package src.ui.screen;

import src.ui.GesTADSUI;
import src.util.tools.BroadcastReceiver;
import src.util.tools.GesLogger;
import src.util.tools.Intent;

import java.util.Scanner;

public class LoginScreen extends GesTADSUI {

    private final String TAG = "LoginScreen";

    LoginScreen(Intent intent){
        super(intent);
    }

    @Override
    public void onCreate() {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "onCreated");
        super.onCreate();
    }

    @Override
    public void onStart() {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "onStart");
        super.onStart();
    }

    @Override
    public void createView() {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(),"createView");

        Scanner scanner = new Scanner(System.in);

        System.out.println("Please insert user matricula: ");
        String matricula = scanner.nextLine();
        System.out.println("Please insert user name: ");
        String username = scanner.nextLine();
        System.out.println("Please insert password: ");
        String password = scanner.nextLine();

        Intent intent = new Intent(Intent.ACTION_LOGIN);

        intent.putString(Intent.KEY_USERNAME, username);
        intent.putString(Intent.KEY_PASSWORD, password);
        intent.putString(Intent.KEY_MATRICULA, matricula);

        BroadcastReceiver.sendBroadcast(intent);

        onDestroy();
    }

    @Override
    protected void onDestroy() {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(),"onDestroy");
        super.onDestroy();
    }
}