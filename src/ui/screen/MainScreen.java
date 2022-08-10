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

        System.out.println(formattedTitle("PÁGINA PRINCIPAL"));
        System.out.println(formattedTitle("Bem vindo ao GesTADS"));
        System.out.println(formattedTitle("Escolha uma das opção abaixo"));
        System.out.println();
        System.out.println(formattedTitle("Menu"));
        System.out.println(formattedLineMenu("* Registrar usuário", "[1]"));
        System.out.println(formattedLineMenu("* Registrar produto", "[2]"));
        System.out.println(formattedLineMenu("* Busca de usuário", "[3]"));
        System.out.println(formattedLineMenu("* Busca de produto", "[4]"));
        System.out.println(formattedLineMenu("* Transação de produto", "[5]"));
        System.out.println(formattedLineMenu("* Logout", "[l]"));
        System.out.println(formattedLineMenu("* Encerrar app", "[q]"));

        boolean match = false;
        Intent i = null;
        while (!match){

            switch (screenGetTextFromUser()){
                case "q":
                    i = new Intent(Intent.ACTION_QUIT);
                    match = true;
                    break;

                case "l":
                    i = new Intent(Intent.ACTION_LOGOUT);
                    match = true;
                    break;

                case "1":
                    i = new Intent(Intent.ACTION_LAUNCH_REGISTER_EMPLOYEE_SCREEN);
                    match = true;
                    break;

                case "2":
                    i = new Intent(Intent.ACTION_LAUNCH_REGISTER_PRODUCT_SCREEN);
                    match = true;
                    break;

                case "3":
                    i = new Intent(Intent.ACTION_LAUNCH_SEARCH_EMPLOYEE_SCREEN);
                    match = true;
                    break;

                case "4":
                    i = new Intent(Intent.ACTION_LAUNCH_SEARCH_PRODUCT_SCREEN);
                    match = true;
                    break;

                case "5":
                    i = new Intent(Intent.ACTION_LAUNCH_TRANSACTION_SCREEN);
                    match = true;
                    break;

                case "6":
                    match = true;
                    break;

                default:
                    System.out.println("Entrada inválida");

            }
        }
        BroadcastReceiver.sendBroadcast(i);
        onDestroy();
    }

    @Override
    protected void onDestroy() {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(),"onDestroy");
        super.onDestroy();
    }
}
