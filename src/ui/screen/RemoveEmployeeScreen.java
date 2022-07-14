package src.ui.screen;

import src.ui.GesTADSUI;
import src.util.tools.BroadcastReceiver;
import src.util.tools.GesLogger;
import src.util.tools.Intent;

import java.util.List;

public class RemoveEmployeeScreen extends GesTADSUI {
    private static final String TAG = "RemoveScreen";

    public RemoveEmployeeScreen(Intent intent) {
        super(intent);
    }

    @Override
    protected void onDestroy() {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "onDestroy");
        super.onDestroy();
    }

    @Override
    protected void createView() {
        //[LAS]

        System.out.println(formattedTitle("REMOVER USUARIO"));

        if(mContextIntent != null){
            List<Integer> flags = getFlags();
            if(flags.contains(Intent.FLAG_REMOVING_USER_IN_PROGRESS)){
                //mostrar os usuarios achados ou nao achados

            }
        }

        System.out.println(formattedTitle("Escolha a forma de buscar o usuário desejado: "));
        System.out.println();
        System.out.println(formattedTitle("Menu"));
        System.out.println(formattedLineMenu("* por CPF", "[0]"));
        System.out.println(formattedLineMenu("* por matrícula", "[1]"));
        System.out.println(formattedLineMenu("* por nome", "[2]"));
        System.out.println(formattedLineMenu("* menu principal", "[3]"));
        System.out.println(formattedLineMenu("* logout", "[4]"));
        System.out.println(formattedLineMenu("* sair", "[5]"));

        boolean shouldRun = true;
        do {
            Intent i;
            switch (getUserInput()){
                case "0":
                    i = new Intent(Intent.ACTION_SEARCH_EMPLOYEE);
                    i.putFlag(Intent.FLAG_SEARCH_EMPLOYEE_BY_CPF);
                    BroadcastReceiver.sendBroadcast(i);
                    shouldRun = false;
                    break;

                case "1":
                    i = new Intent(Intent.ACTION_SEARCH_EMPLOYEE);
                    i.putFlag(Intent.FLAG_SEARCH_BY_MATRICULA);
                    BroadcastReceiver.sendBroadcast(i);
                    shouldRun = false;
                    break;

                case "2":
                    i = new Intent(Intent.ACTION_SEARCH_EMPLOYEE);
                    i.putFlag(Intent.FLAG_SEARCH_BY_NOME);
                    BroadcastReceiver.sendBroadcast(i);
                    shouldRun = false;
                    break;

                case "3":
                    i = new Intent(Intent.ACTION_LAUNCH_MAIN_SCREEN);
                    BroadcastReceiver.sendBroadcast(i);
                    shouldRun = false;
                    break;

                case "4":
                    i = new Intent(Intent.ACTION_LOGOUT);
                    BroadcastReceiver.sendBroadcast(i);
                    shouldRun = false;
                    break;

                case "5":
                i = new Intent(Intent.ACTION_QUIT);
                    BroadcastReceiver.sendBroadcast(i);
                    shouldRun = false;
                    break;
            }
        }while (shouldRun);


        //criar a pagina de excluir
    }
}