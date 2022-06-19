package src.ui;

import src.util.tools.GesLogger;

import java.util.Scanner;

public class LoginScreen extends GesTADSUI{

    private final String TAG = "TesteUI";

    @Override
    public void createView() {
        if(GesLogger.ISLOGABLE) GesLogger.d(TAG, "GesTADSUI child createView");

        System.out.println("Please insert user name: ");
        Scanner scanner = new Scanner(System.in);
        String username = scanner.nextLine();
        System.out.println("Please insert password: ");
        String password = scanner.nextLine();


//        Intent intent = new Intent(Intent.ACTION_LOGIN);
//        intent.putInt("will", 28);
//        BroadcastReceiver.sendBroadcast(intent);
    }
}