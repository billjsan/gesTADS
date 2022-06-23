package src.ui.screen;

import src.ui.GesTADSUI;
import src.util.tools.BroadcastReceiver;
import src.util.tools.GesLogger;
import src.util.tools.Intent;

import java.util.Scanner;

public class LoginScreen extends GesTADSUI {

    private final String TAG = "LoginScreen";

    @Override
    public void createView() {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "createView");


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
}