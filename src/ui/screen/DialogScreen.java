package src.ui.screen;

import src.ui.GesTADSUI;
import src.util.tools.GesLogger;
import src.util.tools.Intent;

import java.util.List;

public class DialogScreen extends GesTADSUI {

    private final String TAG = "DialogScreen";
    public DialogScreen(Intent intent) {
        //[LAS] mostrar a action do intent - não entendi o que seria essa ação
        super(intent);
    }

    @Override
    protected void createView() {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "createView");

        if(mContextIntent == null){
            if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
                GesLogger.d(TAG, " mContextIntent nulo");

            onDestroy();
            return;
        }

        if(mContextIntent.getAction() == Intent.ACTION_LAUNCH_DIALOG_SCREEN){

            List<Integer> flags = getContextFlags();

            if(flags.contains(Intent.FLAG_DIALOG_MESSAGE)){
                String mensagem = mContextIntent.getString(Intent.KEY_MESSAGE_DIALOG);

                System.out.println();
                System.out.println(formattedTitle("DIALOG"));
                System.out.println();
                System.out.println(formattedTitle(mensagem).toUpperCase());
                System.out.println();
                System.out.println(formattedTitle(".."));
                System.out.println();
            }
        }else {

            System.out.println();
            System.out.println(formattedTitle("DIALOG"));
            System.out.println();
            System.out.println(formattedTitle(mContextIntent.getString(Intent.KEY_MESSAGE_DIALOG)).toUpperCase());
            System.out.println();
            System.out.println(formattedTitle(".."));
            System.out.println();
        }

        onDestroy();
    }

    @Override
    protected void onDestroy() {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(),"onDestroy");

        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            if(GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                GesLogger.e(TAG, "Erro ao esperar dialog screen: " + e.getMessage());
        }

        super.onDestroy();
    }
}
