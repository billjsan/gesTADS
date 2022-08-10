package test;

import src.ui.GesTADSUI;
import src.util.GesLogger;

import java.io.IOException;

public class ScreenInputTest extends GesTADSUI {

    private static final String TAG = ScreenInputTest.class.getSimpleName();

    @Override
    protected void createView() {

        String textFromTheUser = screenGetTextFromUser();
        Integer integerFromTheUser = screenGetIntegerFromUser();
    }

    @underTest
    public static void apagaScreen(){
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "apagaScreen");

        try {
            if (System.getProperty("os.name").contains("Windows")){

                // [ICS] Código não testado em windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }else {

                // [MCS] não funciona no terminal da IDE
                // procurar se existe outro jeito que funcione em
                // qualquer lugar:
                System.out.print("\033\143");
            }
        }catch (IOException e){
            if(GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                GesLogger.e(TAG, "erro ao apagar a tela: " + e.getMessage());

        }catch (InterruptedException e){
            if(GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                GesLogger.e(TAG, "a operação de apagar a tela foi interrompida: " + e.getMessage());

        }
    }

}