package src.ui.screen;

import src.ui.GesTADSUI;
import src.util.tools.BroadcastReceiver;
import src.util.tools.GesLogger;
import src.util.tools.Intent;

import java.util.ArrayList;
import java.util.List;

public class SearchEmployeeScreen extends GesTADSUI {
    private static final String TAG = "SearchEmployeeScreen";

    public SearchEmployeeScreen(Intent intent) {
        super(intent);
    }

    @Override
    protected void createView() {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "createView");

        Intent broadcastIntent = null;
        System.out.println(formattedTitle("BUSCAR FUNCIONARIO"));
        System.out.println();

        // inicia uma tela de busca nova (intent é vazio)
        if(mContextIntent.getAction() == Intent.ACTION_LAUNCH_SEARCH_EMPLOYEE_SCREEN &&
                !mContextIntent.hasExtras()){
            if (GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE)
                GesLogger.d(TAG, Thread.currentThread(), "intent: ACTION_LAUNCH_SEARCH_EMPLOYEE_SCREEN");

            System.out.println(formattedLineMenu("Buscar por CPF", "[1]"));
            System.out.println(formattedLineMenu("Cancelar", "[0]"));

            broadcastIntent = getSearchOptionIntent();

        } else if (mContextIntent.getAction() == Intent.ACTION_RESULT_SET && mContextIntent.hasExtras()) {

            if (GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE)
                GesLogger.d(TAG, Thread.currentThread(), "intent: FLAG_RESULT_SET with extra");

            try {
                List<Intent> list = (List<Intent>) mContextIntent.getList(Intent.KEY_RESULT_SET);

                for (int i = 0; i < list.size(); i++) {

                    System.out.println("resultado:");

                    String nome = list.get(i).getString(Intent.KEY_EMPLOYEE_NAME);
                    String cpf = list.get(i).getString(Intent.KEY_EMPLOYEE_CPF);
                    String cargo = list.get(i).getString(Intent.KEY_EMPLOYEE_CARGO);
                    int privilegio = list.get(i).getInt(Intent.KEY_EMPLOYEE_PRIVILEGE);

                    String set = "nome:" + nome + " cpf:" + cpf + " cargo:" + cargo + " privilégio:" + privilegio;

                    System.out.println(formattedLineMenu(set,"["+i+"]"));
                }

                System.out.println();
                System.out.println("selecione um item para mais opções ou [-1] para sair");

                boolean isEmployeeSelected = false;
                do {
                    int input = Integer.parseInt(getUserInput());

                    if(input >= 0 && input <= list.size()){
                        Intent selectedEmployee = list.get(input);

                        System.out.println("Vocẽ selecionou: nome:" +
                                selectedEmployee.getString(Intent.KEY_EMPLOYEE_NAME) +
                                " cpf:" + selectedEmployee.getString(Intent.KEY_EMPLOYEE_CPF));

                        System.out.println(formattedLineMenu("Remover", "[1]"));
                        System.out.println(formattedLineMenu("Editar", "[2]"));
                        System.out.println(formattedLineMenu("Cancelar", "[0]"));

                        boolean isOptionSelected = false;
                        do {
                            ArrayList<Intent> intents = new ArrayList<>();
                            intents.add(selectedEmployee);

                            switch (getUserInput()){
                                case "1":
                                    broadcastIntent = new Intent(Intent.ACTION_REMOVE_EMPLOYEE); //handler not created yet
                                    broadcastIntent.putFlag(Intent.FLAG_RESULT_SET);
                                    broadcastIntent.putList(Intent.KEY_RESULT_SET, intents);

                                    isOptionSelected = true;
                                    break;

                                case "2":
                                    broadcastIntent = new Intent(Intent.ACTION_EDIT_EMPLOYEE); //handler not created yet
                                    broadcastIntent.putFlag(Intent.FLAG_RESULT_SET);
                                    broadcastIntent.putList(Intent.KEY_RESULT_SET, intents);

                                    isOptionSelected = true;
                                    break;

                                case "0":
                                    broadcastIntent = new Intent(Intent.ACTION_LAUNCH_SEARCH_EMPLOYEE_SCREEN);
                                    isOptionSelected = true;
                                    break;

                                default:
                                    System.out.println("Entrada inválida!");
                            }

                        }while (!isOptionSelected);

                    } else if (input == -1) {
                        broadcastIntent = new Intent(Intent.ACTION_LAUNCH_SEARCH_EMPLOYEE_SCREEN);
                        isEmployeeSelected = true;
                    }
                }while (!isEmployeeSelected);



            }catch (NullPointerException e){
                if(GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE) GesLogger.e(TAG, e.getMessage());

                formattedTitle("ocorreu um erro");
                broadcastIntent = new Intent(Intent.ACTION_LAUNCH_MAIN_SCREEN);
            }
        }

        BroadcastReceiver.sendBroadcast(broadcastIntent);
        onDestroy();
    }

    /**
     * @return an Intent with search option selected
     *
     *  retorna um Intent contendo informações sobre
     * a busca
     */
    private Intent getSearchOptionIntent() {
        //[LAS]

        Intent broadcastIntent = null;
        boolean isOptionSelected = false;
        do {
            switch (getUserInput()){
                case "1":
                    if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
                        GesLogger.d(TAG, Thread.currentThread(), "busca por cpf");

                    System.out.print("Digite o CPF: ");
                    String cpf = getUserInput();

                    broadcastIntent = new Intent(Intent.ACTION_SEARCH_EMPLOYEE);
                    broadcastIntent.putFlag(Intent.FLAG_SEARCH_EMPLOYEE_BY_CPF);
                    broadcastIntent.putString(Intent.KEY_EMPLOYEE_CPF, cpf);
                    isOptionSelected = true;
                    break;

                case "0":
                    if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
                        GesLogger.d(TAG, Thread.currentThread(), "cancel");

                    broadcastIntent = new Intent(Intent.ACTION_LAUNCH_MAIN_SCREEN);
                    isOptionSelected = true;
                    break;

                default:
                    if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
                        GesLogger.d(TAG, Thread.currentThread(), "entrada inválida");

                    System.out.println("Por favor, selecione uma opção");
            }
        }while (!isOptionSelected);
        return broadcastIntent;
    }
}
