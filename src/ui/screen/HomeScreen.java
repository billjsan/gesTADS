package src.ui.screen;

import src.ui.GesTADSUI;
import src.util.BroadcastReceiver;
import src.util.GesLogger;
import src.util.Intent;

public class HomeScreen extends GesTADSUI {

    private final String TAG = "HomeScreen";

    HomeScreen(Intent intent){
        super(intent);
    }
    @Override
    public void createView() {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "createView");

        System.out.println(formattedTitle("HOME"));
        System.out.println(formattedTitle("Bem vindo ao GesTADS"));
        System.out.println();
        System.out.println(formattedTitle("Menu"));

        System.out.println(formattedLineMenu( "* Login", "[1]"));
        System.out.println(formattedLineMenu("* Sair", "[0]"));

        boolean shouldRun = true;
        do {

            Intent i ;
            switch (screenGetTextFromUser()){
                case "0":
                    i = new Intent(Intent.ACTION_QUIT);
                    BroadcastReceiver.sendBroadcast(i);
                    shouldRun = false;
                    break;
                case "1":
                    i = new Intent(Intent.ACTION_LAUNCH_LOGIN_SCREEN);
                    BroadcastReceiver.sendBroadcast(i);
                    shouldRun = false;
                    break;
            }
        }while (shouldRun);
        onDestroy();
    }

    @Override
    protected void onDestroy() {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(),"onDestroy");
        super.onDestroy();
    }
}