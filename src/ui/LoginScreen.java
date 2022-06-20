package src.ui;

import src.util.tools.BroadcastReceiver;
import src.util.tools.GesLogger;
import src.util.tools.Intent;

import java.util.Scanner;

public class LoginScreen extends GesTADSUI{

    private final String TAG = LoginScreen.class.getSimpleName();

    @Override
    public void createView() {
        if(GesLogger.ISLOGABLE) GesLogger.d(TAG, "GesTADSUI child createView");

        System.out.println("Please insert user name: ");
        Scanner scanner = new Scanner(System.in);
        String username = scanner.nextLine();
        System.out.println("Please insert password: ");
        String password = scanner.nextLine();

        Intent intent = new Intent(Intent.ACTION_LOGIN);

        intent.putString(Intent.KEY_USERNAME, username);
        intent.putString(Intent.KEY_PASSWORD, password);

        BroadcastReceiver.sendBroadcast(intent);

        onDestroy();
    }
}