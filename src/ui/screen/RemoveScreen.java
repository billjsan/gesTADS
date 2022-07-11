package src.ui.screen;

import src.ui.GesTADSUI;
import src.util.tools.BroadcastReceiver;
import src.util.tools.Intent;

public class RemoveScreen extends GesTADSUI {
    public RemoveScreen(Intent intent) {
        super(intent);
    }

    @Override
    protected void createView() {
        //[LAS]

        System.out.println(formattedTitle("REMOVER USUARIO"));
        System.out.println(formattedTitle("Escolha a forma de buscar o usuário desejado: "));
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
                    i = new Intent(Intent.ACTION_SEARCH);
                    i.putFlag(Intent.FLAG_SEARCH_BY_CPF);
                    BroadcastReceiver.sendBroadcast(i);
                    shouldRun = false;
                    break;

                case "1":
                    i = new Intent(Intent.ACTION_SEARCH);
                    i.putFlag(Intent.FLAG_SEARCH_BY_MATRICULA);
                    BroadcastReceiver.sendBroadcast(i);
                    shouldRun = false;
                    break;

                case "2":
                    i = new Intent(Intent.ACTION_SEARCH);
                    i.putFlag(Intent.FLAG_SEARCH_BY_NOME);
                    BroadcastReceiver.sendBroadcast(i);
                    shouldRun = false;
                    break;

                case "3":
                    i = new Intent(Intent.LAUNCH_MAIN_SCREEN);
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