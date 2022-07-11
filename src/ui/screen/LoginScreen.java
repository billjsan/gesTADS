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

        System.out.println(formattedTitle("LOGIN"));
        System.out.println(formattedTitle("Bem vindo ao GesTADS"));
        System.out.println(formattedTitle("Vocês está realizando login"));
        System.out.println();

        System.out.print("Entre com a matrícula: ");
        String matricula = scanner.nextLine();
        System.out.print("Entre com o login: ");
        String username = scanner.nextLine();
        System.out.print("Entre com a senha: ");
        String password = scanner.nextLine();

        Intent intent = new Intent(Intent.ACTION_CHECK_CREDENTIALS);

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