package src.ui;

import src.util.tools.BroadcastReceiver;
import src.util.tools.GesLogger;
import src.util.tools.Intent;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public abstract class GesTADSUI {

    private final String TAG = GesTADSUI.class.getSimpleName();
    public static int LINE_LENGTH = 80;
    protected Intent mContextIntent;

    public GesTADSUI() {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG,Thread.currentThread(), "constructor");
        onCreate();
    }

    public GesTADSUI(Intent intent) {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(),  "constructor with Intent");

        this.mContextIntent = intent;
        onCreate();
    }

    protected void onCreate() {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG,Thread.currentThread(),  "onCreated");
        onStart();
    }

    protected void onStart() {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG,Thread.currentThread(),  "onStart");
        createView();
    }

    protected abstract void createView();

    protected void onDestroy() {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "onDestroy");

    }

    protected List<Integer> getFlags(){ // avaliar necessidade [ICS]
        //[LAS]
        if(mContextIntent != null && (mContextIntent.getAction() == Intent.ACTION_UI_FLAG)){
            return new ArrayList<>(mContextIntent.getFlags());
        }else {
            return null;
        }
    }

    protected final String formattedLineMenu( String str1, String str2) {
        //[LAS]

        int lineSize = LINE_LENGTH;
        if ((str1.length() + str2.length() > lineSize)){
            return str1 + " " + str2;
        }else {

            int freeSpace = lineSize - str1.length() - str2.length();
            StringBuilder result = new StringBuilder(str1);
            for (int i = 0; i < freeSpace - 3; i++) {
                result.append(" ");
            }
            result.append(">> ");
            result.append(str2);

            return result.toString();
        }
    }

    protected String formattedTitle(String title){
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG,Thread.currentThread(),  "formattedTitle");

        if(title.length() > LINE_LENGTH){
            return title;
        }else {
            StringBuilder result = new StringBuilder("");
            int freeSpace = LINE_LENGTH - title.length();

            for (int i = 0; i < (freeSpace / 2) -1; i++) {
                result.append("-");
            }
            result.append(" ").append(title).append(" ");

            for (int i = 0; i < (freeSpace / 2) -1; i++) {
                result.append("-");
            }
            if(result.length()< LINE_LENGTH){
                result.append("-");
            }
            return result.toString();
        }
    }

    @Deprecated
    protected String getUserInput() {
        //[LAS]

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        while (input.isEmpty()) {

            System.out.println("Insira alguma coisa");
            input = scanner.nextLine();
        }
        return input;
    }

    protected void showMessageDialog(String message){
        //[LAS]

        Intent intent = new Intent(Intent.ACTION_UI_FLAG);
        intent.putString(Intent.KEY_MESSAGE_DIALOG, message);

        BroadcastReceiver.sendBroadcast(intent);
    }

    @Deprecated
    protected Integer getIntFromTheUser(){
        //[LAS]

        Integer integer = null;
        try {
            System.out.print("Digite um número inteiro: ");
            integer = Integer.valueOf(screenGetTextFromUser());
        }catch (NumberFormatException e){
//            if(GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE) GesLogger.e(TAG,
//                    "can't cast to int");

            showMessageDialog("Você não digitou um inteiro");
        }

        return integer;
    }

    /**
     * que deve ser usado como padrao em todas as UIs
     * @return
     */
    protected Integer screenGetIntegerFromUser(){
        //[LAS]

        Scanner input = new Scanner(System.in);
        Integer integerValue = null;
        boolean gotInput = false;
        do {

            try {
                System.out.println("Digite um número inteiro: ");

                String value = input.nextLine();
                if (!value.isEmpty()){
                    integerValue = Integer.parseInt(value);
                    gotInput = true;
                }else {
                    System.out.println("Digite alguma coisa!");
                }


            }catch (NumberFormatException e){
                if (GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                    GesLogger.e(TAG, "screenGetIntFromUser: error parsing to Integer");

                System.out.println("Você não digitou um número");

            }catch (Exception e){
                e.printStackTrace();
            }

        }while (!gotInput);

        return integerValue;
    }

    /**
     * que deve ser usado como padrao em todas as UIs
     * @return
     */
    protected String screenGetTextFromUser(){

        Scanner input = new Scanner(System.in);
        boolean gotInput = false;
        String value = null;
        do {

            try {
                System.out.println("Entre com o texto: ");
                value = input.nextLine();

                if (!value.isEmpty()){
                    gotInput = true;
                }else {
                    System.out.println("Digite alguma coisa!");
                }
            } catch (Exception e){
                if(GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                    GesLogger.e(TAG, "screenGetTextFromUser:  unexpected error: " + e.getMessage());

                System.out.println("Algo errado ocorreu");
            }

        }while (!gotInput);

        return value;
    }
}