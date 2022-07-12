package src.ui.screen;

import src.ui.GesTADSUI;
import src.util.tools.BroadcastReceiver;
import src.util.tools.GesLogger;
import src.util.tools.Intent;

import java.util.List;

public class SearchScreen extends GesTADSUI {

    private final String TAG = "SearchScreen";

    public SearchScreen(Intent intent) {
        super(intent);
    }

    @Override
    public void createView() {

        if(mContextIntent == null){
            if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE) GesLogger.d(TAG,
                    Thread.currentThread(), "createView, intent is null");
            return;
        }

        System.out.println(formattedTitle("BUSCAR"));
        System.out.println(formattedTitle("você está na tela de buscas"));

        if(mContextIntent.getAction() == Intent.ACTION_SEARCH_EMPLOYEE){
            List<Integer> flags = mContextIntent.getFlags();

            if(flags.contains(Intent.FLAG_SEARCH_BY_CPF)){
                telaBuscaPorCPF();
            } else if (flags.contains(Intent.FLAG_SEARCH_BY_NOME)) {
                telaBuscaPorNome();
            } else if (flags.contains(Intent.FLAG_SEARCH_BY_MATRICULA)) {
                telaBuscaPorMatricula();
            }

            if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE) GesLogger.d(TAG,
                    Thread.currentThread(), "no Flags found. flags in the intend: " + flags.size());
        }


        onDestroy();
    }

    private void telaBuscaPorMatricula() {
        System.out.println(formattedTitle("BUSCA POR MATRÍCULA"));

        Intent matIntent = mContextIntent;

        if(matIntent.getString(Intent.KEY_EMPLOYEE_MATRICULA) == null){

            System.out.print("Ente com a matricula para busca");
            matIntent.putString(Intent.KEY_EMPLOYEE_MATRICULA, getUserInput());
        }else {
            //recuperar os dados que tem la
        }

        BroadcastReceiver.sendBroadcast(matIntent);
    }

    private void telaBuscaPorNome() {
        System.out.println(formattedTitle("BUSCA POR NOME"));
        System.out.print("Ente com o nome");


    }

    private void telaBuscaPorCPF() {
        System.out.println(formattedTitle("BUSCA POR CPF"));
        System.out.print("Ente com o CPF");



    }

    @Override
    protected void onDestroy() {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "onDestroy");
        super.onDestroy();
    }
}
