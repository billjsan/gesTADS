package src.ui.screen;

import src.ui.GesTADSUI;
import src.util.tools.BroadcastReceiver;
import src.util.tools.GesLogger;
import src.util.tools.Intent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PROCURAREMPREGADO extends GesTADSUI {

    private static final String TAG = "PROCURAREMPREGADO"; //todo mudar o nome da classe
    private final String PRIMARY_SCREEN = "primary-screen";
    private final String RESULT_SCREEN = "result-screen";
    private final String NO_SET_SCREEN = "no-set-screen";

    private String mContextFlag = "";

    public PROCURAREMPREGADO(Intent intent) {
        //[LAS]

        super(intent);
    }

    @Override
    protected void onCreate() {
        //[LAS]

        //rotina de tratamento do context intent
        if (mContextIntent.getAction() == Intent.ACTION_LAUNCH_SEARCH_EMPLOYEE_SCREEN &&
                !mContextIntent.hasExtras()){
            if (GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE) {
                GesLogger.d(TAG, Thread.currentThread(), "intent: ACTION_LAUNCH_SEARCH_EMPLOYEE_SCREEN");
            }

            mContextFlag = PRIMARY_SCREEN;

        } else if (mContextIntent.getAction() == Intent.ACTION_RESULT_SET && mContextIntent.hasExtras()) {
            if (GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE) {
                GesLogger.d(TAG, Thread.currentThread(), "intent: FLAG_RESULT_SET with extra");
            }

            mContextFlag = RESULT_SCREEN;
            
        }else {
            if (GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE) {
                GesLogger.d(TAG, Thread.currentThread(), "unreached case");
            }

            mContextFlag = NO_SET_SCREEN;
        }
        
        super.onCreate();
    }

    @Override
    protected void createView() {
        //[LAS]

        switch (mContextFlag){
            case PRIMARY_SCREEN:
                
                primaryScreen();
                break;
                
            case RESULT_SCREEN:
                
                resultScreen();
                break;
                
            case NO_SET_SCREEN:
                
                showMessageDialog("Essa tela ainda não existe!");
                break;

        }

    }

    private void primaryScreen() {
        //[LAS]

        System.out.println(formattedTitle("BUSCAR FUNCIONÁRIO"));
        System.out.println();
        System.out.println("Opções:");
        System.out.println(formattedLineMenu("Buscar por CPF", "[1]")); //todo
        System.out.println(formattedLineMenu("Buscar por Nome", "[não implementado]")); //todo
        System.out.println(formattedLineMenu("Buscar por Cargo", "[não implementado]")); //todo
        System.out.println(formattedLineMenu("Buscar por Matrícula", "[não implementado]")); //todo
        System.out.println(formattedLineMenu("Cancelar", "[0]"));

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

                    showMessageDialog("Por favor, selecione uma opção");
            }
        }while (!isOptionSelected);

        BroadcastReceiver.sendBroadcast(broadcastIntent);
        onDestroy();
    }

    private void resultScreen() {
        System.out.println(formattedTitle("BUSCAR FUNCIONÁRIO"));
        System.out.println();
        System.out.println("Resultado da busca:");
        System.out.println();

        showResultSet();

        System.out.println("Escolha um ítem ou [-1] para sair");
        
        handleSelectedResultItem();

        BroadcastReceiver.sendBroadcast(new Intent(Intent.ACTION_LAUNCH_SEARCH_EMPLOYEE_SCREEN));
        onDestroy();
    }

    private void handleSelectedResultItem() {
        //[LAS]

        boolean isEmployeeSelected = false;
        do {
            
                List<Intent> list = getResultSet();
                
                if(list == null) return;

                Integer set = null;
                while (set == null){

                    set = screenGetIntegerFromUser();
                }
                System.out.println("foooooooooooooi");

                int input = 50 ;//Integer.parseInt(getUserInput());

                if(input >= 0 && input < list.size()){

                    Intent selectedEmployee = list.get(input);
                    isEmployeeSelected = true;

                    System.out.println("Vocẽ selecionou: NOME:" +
                            selectedEmployee.getString(Intent.KEY_EMPLOYEE_NAME) +
                            " CPF:" + selectedEmployee.getString(Intent.KEY_EMPLOYEE_CPF));

                    handleSelectedAction(selectedEmployee);
                }

                //o usuário escolheu sair da tela de resultado
                else if (input == -1) {
                    
                    BroadcastReceiver.sendBroadcast(new Intent(Intent.ACTION_LAUNCH_SEARCH_EMPLOYEE_SCREEN));
                    isEmployeeSelected = true;
                }else {

                    showMessageDialog("Você nao digitou um número");
                }

        }while (!isEmployeeSelected);
    }

    /**
     * trata a escolha do usuário sobre o que
     * deseja fazer com o item selecionado.
     * Ideia de como se fosse um menu de contexto
     *
     * @param selectedEmployee contém o intent selecionado
     *                         pelo usuário
     */
    private void handleSelectedAction(Intent selectedEmployee) {

        System.out.println("Opções:");
        System.out.println(formattedLineMenu("Remover", "[1]"));
        System.out.println(formattedLineMenu("Editar", "[2]"));
        System.out.println(formattedLineMenu("Cancelar", "[0]"));

        // captura o que o usuário deseja fazer com o item selecionado
        if(selectedEmployee == null) return;
        boolean isOptionSelected = false;
        do {
            ArrayList<Intent> intents = new ArrayList<>();
            intents.add(selectedEmployee);

            Intent broadcastIntent = null;

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

            BroadcastReceiver.sendBroadcast(broadcastIntent);

        }while (!isOptionSelected);
    }

    private void showResultSet() {
        //[LAS]
        
        try {
            List<Intent> resultSet = getResultSet();

            // shows the search result set
            for (int i = 0; i < resultSet.size(); i++) {

                String nome = resultSet.get(i).getString(Intent.KEY_EMPLOYEE_NAME);
                String cpf = resultSet.get(i).getString(Intent.KEY_EMPLOYEE_CPF);
                String cargo = resultSet.get(i).getString(Intent.KEY_EMPLOYEE_CARGO);
                int privilegio = resultSet.get(i).getInt(Intent.KEY_EMPLOYEE_PRIVILEGE);

                String set = "nome:" + nome + " cpf:" + cpf + " cargo:" + cargo + " privilégio:" + privilegio;

                System.out.println(formattedLineMenu(set,"["+i+"]"));
            }
        }catch (NullPointerException e){
            if(GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE) GesLogger.e(TAG,
                    "result set List issue");
            
            showMessageDialog("Ocorreu um erro");
            BroadcastReceiver.sendBroadcast(new Intent(Intent.ACTION_LAUNCH_MAIN_SCREEN));
            
        }catch (Exception e){
            if(GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE) GesLogger.e(TAG,
                    "casting error probably");

            showMessageDialog("Algo muito errada aconteceu");
            BroadcastReceiver.sendBroadcast(new Intent(Intent.ACTION_LAUNCH_MAIN_SCREEN));
        }
    }

    private List<Intent> getResultSet() {

        List<Intent> result = new ArrayList<>();
        try {
            result = (List<Intent>) mContextIntent.getList(Intent.KEY_RESULT_SET);
            
        }catch (Exception e){
            if(GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE) GesLogger.e(TAG,
                    "can't reach result set");
            
            showMessageDialog("Um ocorreu em sua busca");
            
            BroadcastReceiver.sendBroadcast(new Intent(Intent.ACTION_LAUNCH_MAIN_SCREEN));
        }
        return result;
    }
}
