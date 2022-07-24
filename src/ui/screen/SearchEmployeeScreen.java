package src.ui.screen;

import src.ui.GesTADSUI;
import src.util.tools.BroadcastReceiver;
import src.util.tools.GesLogger;
import src.util.tools.Intent;

import java.util.ArrayList;
import java.util.List;

public class SearchEmployeeScreen extends GesTADSUI {

    private static final String TAG = "SearchEmployeeScreen";
    private final String PRIMARY_SCREEN = "primary-screen";
    private final String RESULT_SCREEN = "result-screen";
    private final String NO_SET_SCREEN = "no-set-screen";

    private String mContextFlag = "";

    public SearchEmployeeScreen(Intent intent) {
        //[LAS] mostar a action do intent no log
        // ex: intent.getAction()

        super(intent);
    }

    /**
     * Método é chamado no momento da criação da tela pela classe pai GesTADSUI. Ele configura o contexto
     * em que a UI deve ser exibida (mContextFlag) a partir do intent de contexto (mContextIntent)
     */
    @Override
    protected void onCreate() {
        //[LAS]

        //rotina de tratamento do context intent
        if (mContextIntent.getAction() == Intent.ACTION_LAUNCH_SEARCH_EMPLOYEE_SCREEN &&
                !mContextIntent.hasExtras()) {
            if (GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE) {
                GesLogger.d(TAG, Thread.currentThread(), "intent: ACTION_LAUNCH_SEARCH_EMPLOYEE_SCREEN");
            }

            mContextFlag = PRIMARY_SCREEN;

        } else if (mContextIntent.getAction() == Intent.ACTION_RESULT_SET && mContextIntent.hasExtras()) {
            if (GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE) {
                GesLogger.d(TAG, Thread.currentThread(), "intent: FLAG_RESULT_SET with extra");
            }

            mContextFlag = RESULT_SCREEN;

        } else {
            if (GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE) {
                GesLogger.d(TAG, Thread.currentThread(), "unreached case");
            }

            mContextFlag = NO_SET_SCREEN;
        }

        super.onCreate();
    }

    /**
     * Esse método cria a view de acordo com
     * o contexto configurado em onCreate()
     */
    @Override
    protected void createView() {
        //[LAS]

        switch (mContextFlag) {
            case PRIMARY_SCREEN:

                primaryScreen();
                break;

            case RESULT_SCREEN:

                resultScreen();
                break;

            case NO_SET_SCREEN:

                showGesTADSUIDialogScreen("Essa tela ainda não existe!");
                break;
        }
    }

    /**
     * Cria a tela como tela primária, ou seja,
     * a tela sem nenhuma alteração. Uma tela "limpa"
     */
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

            switch (screenGetIntegerFromUser()) {
                case 1: // busca por cpf
                    if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
                        GesLogger.d(TAG, Thread.currentThread(), "busca por cpf");

                    System.out.print("Digite o CPF: ");
                    String cpf = screenGetTextFromUser();

                    broadcastIntent = new Intent(Intent.ACTION_SEARCH_EMPLOYEE);
                    broadcastIntent.putFlag(Intent.FLAG_SEARCH_EMPLOYEE_BY_CPF);
                    broadcastIntent.putString(Intent.KEY_EMPLOYEE_CPF, cpf);
                    isOptionSelected = true;
                    break;

                case 0: // cancelar
                    if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
                        GesLogger.d(TAG, Thread.currentThread(), "cancel");

                    broadcastIntent = new Intent(Intent.ACTION_LAUNCH_MAIN_SCREEN);
                    isOptionSelected = true;
                    break;

                default:
                    if (GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                        GesLogger.e(TAG, "entrada inválida");

                    showGesTADSUIDialogScreen("Por favor, selecione uma opção");
            }
        } while (!isOptionSelected);

        BroadcastReceiver.sendBroadcast(broadcastIntent);
        onDestroy();
    }

    /**
     * Mostra o resultado da busca e trata as escolhas do usuário sobre
     * o que fazer com o resultado da busca
     */
    private void resultScreen() {
        // [LAS]

        System.out.println(formattedTitle("BUSCAR FUNCIONÁRIO"));
        System.out.println();
        System.out.println("Resultado da busca:");
        System.out.println();

        if (getShowResultSetStatus() == STATUS_FAIL) {
            restartThisScreen();
            return;
        }

        System.out.println("Escolha um ítem ou [-1] para sair");

        if (getHandleSelectedResultItemStatus() == STATUS_FAIL) {
            restartThisScreen();
            return;
        }

        onDestroy();
    }

    /**
     * Esse método chama uma nova instância dessa mesma classe
     * sem nenhum dado nela, ou seja, como se fosse aberta pela primeira vez
     */
    private void restartThisScreen() {
        BroadcastReceiver.sendBroadcast(new Intent(Intent.ACTION_LAUNCH_SEARCH_EMPLOYEE_SCREEN));
        onDestroy();
    }

    /**
     * Esse método lida com a escolha do usuário sobre o que fazer com o item
     * que ele selecionou e @return um valor que define o status da operação
     */
    private int getHandleSelectedResultItemStatus() {
        //[LAS]

        do {
            List<Intent> resultSet = getResultSet();

            if (resultSet == null) return STATUS_FAIL;

            int input = screenGetIntegerFromUser();

            if (input >= 0 && input < resultSet.size()) {

                Intent selectedEmployee = resultSet.get(input);

                System.out.println("Vocẽ selecionou: NOME:" +
                        selectedEmployee.getString(Intent.KEY_EMPLOYEE_NAME) +
                        " CPF:" + selectedEmployee.getString(Intent.KEY_EMPLOYEE_CPF));

                return getHandleSelectedActionStatus(selectedEmployee);
            } else if (input == -1) {

                System.out.println("Saindo...");
                return STATUS_FAIL;
            } else {

                showGesTADSUIDialogScreen("Você não escolheu uma opção");
            }

        } while (true);
    }


    /**
     * esse método é responsável pela ação que será tomada de acordo com o item
     * que o usuário selecionou recebe como @param o usuário que foi selecionado e
     * @return um valor de status sobre a operação.
     */
    private int getHandleSelectedActionStatus(Intent selectedEmployee) {
        // [LAS]

        if (selectedEmployee == null) return STATUS_FAIL;

        System.out.println("Opções:");
        System.out.println(formattedLineMenu("Remover", "[1]"));
        System.out.println(formattedLineMenu("Editar", "[2]"));
        System.out.println(formattedLineMenu("Cancelar", "[0]"));

        // captura o que o usuário deseja fazer com o item selecionado
        ArrayList<Intent> intents = new ArrayList<>();
        intents.add(selectedEmployee);

        Intent broadcastIntent = null;

        switch (screenGetIntegerFromUser()) {
            case 1: // remover usuário
                broadcastIntent = new Intent(Intent.ACTION_REMOVE_EMPLOYEE); // todo handler not created yet
                broadcastIntent.putFlag(Intent.FLAG_RESULT_SET);
                broadcastIntent.putList(Intent.KEY_RESULT_SET, intents);
                break;

            case 2: // editar usuário
                broadcastIntent = new Intent(Intent.ACTION_EDIT_EMPLOYEE); //todo handler not created yet
                broadcastIntent.putFlag(Intent.FLAG_RESULT_SET);
                broadcastIntent.putList(Intent.KEY_RESULT_SET, intents);
                break;

            case 0:// abrir tela de busca

                System.out.println("Cancelando...");
                return STATUS_FAIL;

            default:
                System.out.println("Entrada inválida!");
        }

        BroadcastReceiver.sendBroadcast(broadcastIntent);
        return STATUS_SUCCESS;
    }

    /**
     * esse método tenta mostrar o resultado da busca
     * e retorna um inteiro com o status da transação
     *
     * @return o status da tentativa
     */
    private int getShowResultSetStatus() {
        // [LAS]

        try {
            List<Intent> resultSet = getResultSet();
            if (resultSet == null) return STATUS_FAIL;

            // shows the search result set
            for (int i = 0; i < resultSet.size(); i++) {

                String nome = resultSet.get(i).getString(Intent.KEY_EMPLOYEE_NAME);
                String cpf = resultSet.get(i).getString(Intent.KEY_EMPLOYEE_CPF);
                String cargo = resultSet.get(i).getString(Intent.KEY_EMPLOYEE_CARGO);
                int privilegio = resultSet.get(i).getInt(Intent.KEY_EMPLOYEE_PRIVILEGE);

                String set = "nome:" + nome + " cpf:" + cpf + " cargo:" + cargo + " privilégio:" + privilegio;

                System.out.println(formattedLineMenu(set, "[" + i + "]"));

            }
        } catch (NullPointerException e) {
            if (GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE) GesLogger.e(TAG,
                    "result set List issue");

            showGesTADSUIDialogScreen("Ocorreu um erro");
            return STATUS_FAIL;

        } catch (Exception e) {
            if (GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE) GesLogger.e(TAG,
                    "casting error probably");

            showGesTADSUIDialogScreen("Algo muito errada aconteceu");
            return STATUS_FAIL;
        }

        return STATUS_SUCCESS;
    }

    /**
     * Esse método pega o result set apartir do intent de contexto e
     * @return esse resultato como uma List de Intent
     */
    private List<Intent> getResultSet() {

        List<Intent> result;
        try {
            result = (List<Intent>) mContextIntent.getList(Intent.KEY_RESULT_SET);

        } catch (Exception e) {
            if (GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE) GesLogger.e(TAG,
                    "can't reach result set");

            showGesTADSUIDialogScreen("Um ocorreu em sua busca");
            return null;
        }
        return result;
    }
}