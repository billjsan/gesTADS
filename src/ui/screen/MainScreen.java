package src.ui.screen;

import src.ui.GesTADSUI;
import src.util.tools.BroadcastReceiver;
import src.util.tools.GesLogger;
import src.util.tools.Intent;

import java.util.Scanner;


public class MainScreen extends GesTADSUI {

    private static final String TAG = "MainScreen";

    MainScreen(Intent intent){
        super(intent);
    }
    @Override
    public void createView() {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(),"createView");

        Scanner scanner = new Scanner(System.in);

        System.out.println(formattedTitle("PÁGINA PRINCIPAL"));
        System.out.println(formattedTitle("Bem vindo ao GesTADS"));
        System.out.println(formattedTitle("Escolha uma das opção abaixo"));
        System.out.println();
        System.out.println(formattedTitle("Menu"));
        System.out.println(formattedLineMenu("* Logout", "[1]"));
        System.out.println(formattedLineMenu("* Registrar usuário", "[2]"));
        System.out.println(formattedLineMenu("* Remover usuário", "[3]"));
        System.out.println(formattedLineMenu("* Entrada de produto", "[4]"));
        System.out.println(formattedLineMenu("* Saída de produto", "[5]"));
        System.out.println(formattedLineMenu("* Busca de produto", "[6]"));
        System.out.println(formattedLineMenu("* sair", "[0]"));

        boolean match = false;
        Intent i = null;
        while (!match){
            String input = scanner.nextLine();

            switch (input){
                case "0":
                    i = new Intent(Intent.ACTION_QUIT);
                    match = true;
                    break;

                case "1":
                    i = new Intent(Intent.ACTION_LOGOUT);
                    match = true;
                    break;

                case "2":
                    i = new Intent(Intent.LAUNCH_REGISTER_SCREEN);
                    match = true;
                    break;

                case "3":
                    i = new Intent(Intent.LAUNCH_REMOVE_USER_SCREEN);
                    match = true;
                    break;

                case "4":
                    match = true;
                    break;

                case "5":
                    match = true;
                    break;

            }
        }
        BroadcastReceiver.sendBroadcast(i);
        //GesTADSApp.infoThread();
        onDestroy();
    }

    @Override
    protected void onDestroy() {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(),"onDestroy");
        super.onDestroy();
    }
}
