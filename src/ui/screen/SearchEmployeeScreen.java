package src.ui.screen;

import src.ui.GesTADSUI;
import src.util.tools.BroadcastReceiver;
import src.util.tools.GesLogger;
import src.util.tools.Intent;

import java.util.ArrayList;
import java.util.Arrays;
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

        // INTENT VAZIO - ACTION_LAUNCH_SEARCH_EMPLOYEE_SCREEN
        if(mContextIntent.getAction() == Intent.ACTION_LAUNCH_SEARCH_EMPLOYEE_SCREEN &&
                !mContextIntent.hasExtras()){
            if (GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE)
                GesLogger.d(TAG, Thread.currentThread(), "intent: ACTION_LAUNCH_SEARCH_EMPLOYEE_SCREEN");

            System.out.println(formattedLineMenu("Buscar por CPF", "[1]"));
            System.out.println(formattedLineMenu("Cancelar", "[0]"));

            broadcastIntent = getSearchDetailsIntent();
        }

        // INTENT COM EXTRA - ACTION_RESULT_SET
        else if (mContextIntent.getAction() == Intent.ACTION_RESULT_SET && mContextIntent.hasExtras()) {
            if (GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE)
                GesLogger.d(TAG, Thread.currentThread(), "intent: FLAG_RESULT_SET with extra");

            broadcastIntent = getResultForDataSetIntent();
        }

        BroadcastReceiver.sendBroadcast(broadcastIntent);
        onDestroy();
    }

    /**
     * this method handle the data set from search result
     * to show it on screen and handle the options avaiable
     * for user selection
     *
     * @return an intent with user action about result item selected
     *
     * todo simplify this method
     */
    private Intent getResultForDataSetIntent() {
        Intent broadcastIntent = null;

        try {
            List<Intent> list = (List<Intent>) mContextIntent.getList(Intent.KEY_RESULT_SET);

            // shows the search result set
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

            // captura o item escolhido pelo usuario
            boolean isEmployeeSelected = false;
            do {
                    int input = Integer.parseInt(screenGetTextFromUser());

                // todo [ICS] problemas ao capturar uma letra do usuario (especializar o metodo?)

                // o usuário escolheu um item válido
                if(input >= 0 && input <= list.size()){
                    Intent selectedEmployee = list.get(input);

                    isEmployeeSelected = true;

                    System.out.println("Vocẽ selecionou: nome:" +
                            selectedEmployee.getString(Intent.KEY_EMPLOYEE_NAME) +
                            " cpf:" + selectedEmployee.getString(Intent.KEY_EMPLOYEE_CPF));

                    System.out.println(formattedLineMenu("Remover", "[1]"));
                    System.out.println(formattedLineMenu("Editar", "[2]"));
                    System.out.println(formattedLineMenu("Cancelar", "[0]"));

                    // captura o que o usuário deseja fazer com o item selecionado
                    boolean isOptionSelected = false;
                    do {
                        System.out.println("captura o que o usuário deseja fazer com o item selecionado");
                        ArrayList<Intent> intents = new ArrayList<>();
                        intents.add(selectedEmployee);

                        switch (screenGetTextFromUser()){
                            case "1": // remover usuário
                                broadcastIntent = new Intent(Intent.ACTION_REMOVE_EMPLOYEE); // todo handler not created yet
                                broadcastIntent.putFlag(Intent.FLAG_RESULT_SET);
                                broadcastIntent.putList(Intent.KEY_RESULT_SET, intents);

                                isOptionSelected = true;
                                break;

                            case "2": // editar usuário
                                broadcastIntent = new Intent(Intent.ACTION_EDIT_EMPLOYEE); //todo handler not created yet
                                broadcastIntent.putFlag(Intent.FLAG_RESULT_SET);
                                broadcastIntent.putList(Intent.KEY_RESULT_SET, intents);

                                isOptionSelected = true;
                                break;

                            case "0":// abrir tela de busca
                                broadcastIntent = new Intent(Intent.ACTION_LAUNCH_SEARCH_EMPLOYEE_SCREEN);
                                isOptionSelected = true;
                                break;

                            default:
                                System.out.println("Entrada inválida!");
                        }

                    }while (!isOptionSelected);

                }

                //o usuário escolheu sair da tela de resultado
                else if (input == -1) {
                    broadcastIntent = new Intent(Intent.ACTION_LAUNCH_SEARCH_EMPLOYEE_SCREEN);
                    isEmployeeSelected = true;
                }
            }while (!isEmployeeSelected);

        }catch (Exception e){
            if(GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE) GesLogger.e(TAG,
                    e.getMessage() + " " + e.getCause() + " " + Arrays.toString(e.getStackTrace()));

            System.out.println(formattedTitle("ocorreu um erro"));
            broadcastIntent = new Intent(Intent.ACTION_LAUNCH_MAIN_SCREEN);
        }
        return broadcastIntent;
    }

    /**
     * @return an Intent with search option selected
     *
     *  retorna um Intent contendo informações sobre
     * a busca
     */
    private Intent getSearchDetailsIntent() {
        //[LAS]

        Intent broadcastIntent = null;
        boolean isOptionSelected = false;
        do {
            switch (screenGetTextFromUser()){
                case "1": // busca por cpf
                    if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
                        GesLogger.d(TAG, Thread.currentThread(), "busca por cpf");

                    System.out.print("Digite o CPF: ");
                    String cpf = screenGetTextFromUser();

                    broadcastIntent = new Intent(Intent.ACTION_SEARCH_EMPLOYEE);
                    broadcastIntent.putFlag(Intent.FLAG_SEARCH_EMPLOYEE_BY_CPF);
                    broadcastIntent.putString(Intent.KEY_EMPLOYEE_CPF, cpf);
                    isOptionSelected = true;
                    break;

                case "0": // cancelar
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
