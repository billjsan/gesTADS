package src.ui.screen;

import src.ui.GesTADSUI;
import src.util.tools.BroadcastReceiver;
import src.util.tools.GesLogger;
import src.util.tools.Intent;

import java.util.ArrayList;
import java.util.List;

public class SearchProductScreen extends GesTADSUI {

    private static final String TAG = "SearchProductScreen";
    private final String PRIMARY_SCREEN = "primary-screen";
    private final String RESULT_SCREEN = "result-screen";
    private final String NO_SET_SCREEN = "no-set-screen";
    private String mContextFlag = "";

    public SearchProductScreen(Intent intent) {
        super(intent);

        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE) {
            GesLogger.d(TAG, Thread.currentThread(), "constructed with intent: " + intent.getAction());
        }
    }

    /**
     * Método é chamado no momento da criação da tela pela classe pai GesTADSUI. Ele configura o contexto
     * em que a UI deve ser exibida (mContextFlag) a partir do intent de contexto (mContextIntent)
     */
    @Override
    protected void onCreate() {

        //rotina de tratamento do context intent
        if (mContextIntent.getAction() == Intent.ACTION_LAUNCH_SEARCH_PRODUCT_SCREEN &&
                !mContextIntent.hasExtras()) {
            if (GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE) {
                GesLogger.d(TAG, Thread.currentThread(), "intent: ACTION_LAUNCH_SEARCH_PRODUCT_SCREEN");
            }

            mContextFlag = PRIMARY_SCREEN;

        } else if (mContextIntent.getAction() == Intent.ACTION_RESULT_SET && mContextIntent.hasExtras()) {
            if (GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE) {
                GesLogger.d(TAG, Thread.currentThread(), "intent: FLAG_RESULT_SET with extra");
            }

            mContextFlag = RESULT_SCREEN;

        } else {
            if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE) {
                GesLogger.d(TAG, Thread.currentThread(), "unreached case");
            }

            mContextFlag = NO_SET_SCREEN;
        }

        super.onCreate();
    }

    /**
     * Cria a tela como tela primária, ou seja,
     * a tela sem nenhuma alteração. Uma tela "limpa"
     */
    @Override
    protected void createView() {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE) {
            GesLogger.d(TAG, Thread.currentThread(), "createView");
        }

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

        onDestroy();
    }

    private void primaryScreen() {
        //[LAS]

        System.out.println(formattedTitle("BUSCAR PRODUTO"));
        System.out.println();
        System.out.println("Opções:");
        System.out.println();
        System.out.println(formattedLineMenu("Buscar por nome", "[não implementado]")); // [ICS] precisa implementar
        System.out.println(formattedLineMenu("Buscar por fabricante", "[não implementado]"));
        System.out.println(formattedLineMenu("Buscar por número de série", "[3]")); // [ICS] precisa implementar
        System.out.println(formattedLineMenu("Cancelar", "[c]"));

        Intent broadcastIntent = null;
        boolean isOptionSelected = false;
        do {

            switch (screenGetTextFromUser()) {
                case "3": // busca por número de série
                    if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
                        GesLogger.d(TAG, Thread.currentThread(), "busca por número de série");

                    System.out.print("Digite o número de série: ");
                    String serialNo = screenGetTextFromUser();

                    broadcastIntent = new Intent(Intent.ACTION_SEARCH_PRODUCT);
                    broadcastIntent.putFlag(Intent.FLAG_SEARCH_PRODUCT_BY_SERIAL_NUMBER);
                    broadcastIntent.putString(Intent.KEY_PRODUCT_SERIAL_NUMBER, serialNo);
                    isOptionSelected = true;
                    break;

                case "c": // cancelar
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
    }

    private void resultScreen() {
        //[LAS]

        System.out.println(formattedTitle("BUSCAR PRODUTO"));
        System.out.println();
        System.out.println("Resultado da busca:");
        System.out.println();

        if (getShowResultSetStatus() == STATUS_FAIL)
                restartThisScreen();

        System.out.println("Escolha um ítem ou [-1] para sair");

        if (getHandleSelectedResultItemStatus() == STATUS_FAIL)
                restartThisScreen();

    }

    private int getShowResultSetStatus() {
        // [LAS]

        try {
            List<Intent> resultSet = getResultSet();
            if (resultSet == null) return STATUS_FAIL;

            // shows the search result set
            for (int i = 0; i < resultSet.size(); i++) {

                Long idProduto = resultSet.get(i).getLong(Intent.KEY_PRODUCT_ID);
                String nome = resultSet.get(i).getString(Intent.KEY_PRODUCT_NAME);
                String fabricante = resultSet.get(i).getString(Intent.KEY_PRODUCT_FABRICANTE);
                String descricao = resultSet.get(i).getString(Intent.KEY_PRODUCT_DESCRICAO);
                String validade = resultSet.get(i).getString(Intent.KEY_PRODUCT_VALIDADE);
                String serialNo = resultSet.get(i).getString(Intent.KEY_PRODUCT_SERIAL_NUMBER);
                String dataFabricacao = resultSet.get(i).getString(Intent.KEY_PRODUCT_FABRICACAO);

                String set = "nome:" + nome + " fabricante:" + fabricante + " descrição:" + descricao + " id:" + idProduto;

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

    private void restartThisScreen() {
        BroadcastReceiver.sendBroadcast(new Intent(Intent.ACTION_LAUNCH_SEARCH_PRODUCT_SCREEN));
    }

    private int getHandleSelectedResultItemStatus() {
        //[LAS]

        do {
            List<Intent> resultSet = getResultSet();

            if (resultSet == null) return STATUS_FAIL;

            int input = screenGetIntegerFromUser();

            if (input >= 0 && input < resultSet.size()) {

                Intent selectedProduct = resultSet.get(input);

                System.out.println("Vocẽ selecionou: NOME:" +
                        selectedProduct.getString(Intent.KEY_PRODUCT_NAME) +
                        " Descrição:" + selectedProduct.getString(Intent.KEY_PRODUCT_DESCRICAO));

                return getHandleSelectedActionStatus(selectedProduct);
            } else if (input == -1) {

                System.out.println("Saindo...");
                return STATUS_FAIL;
            } else {

                showGesTADSUIDialogScreen("Você não escolheu uma opção");
            }

        } while (true);
    }

    private int getHandleSelectedActionStatus(Intent selectedProduct) {
        // [LAS]

        if (selectedProduct == null) return STATUS_FAIL;

        System.out.println("Opções:");
        System.out.println(formattedLineMenu("Remover", "[1]"));
        System.out.println(formattedLineMenu("Editar", "[2]"));
        System.out.println(formattedLineMenu("Cancelar", "[0]"));

        // captura o que o usuário deseja fazer com o item selecionado
        ArrayList<Intent> intents = new ArrayList<>();
        intents.add(selectedProduct);

        Intent broadcastIntent = null;

        switch (screenGetTextFromUser()) {
            case "1": // remover produto
                broadcastIntent = new Intent(Intent.ACTION_REMOVE_PRODUCT);
                broadcastIntent.putFlag(Intent.FLAG_RESULT_SET);
                broadcastIntent.putList(Intent.KEY_RESULT_SET, intents);
                break;

            case "2": // editar produto
                broadcastIntent = new Intent(Intent.ACTION_LAUNCH_EDIT_PRODUCT);
                broadcastIntent.putFlag(Intent.FLAG_RESULT_SET);
                broadcastIntent.putList(Intent.KEY_RESULT_SET, intents);
                break;

            case "0":// abrir tela de busca

                System.out.println("Cancelando...");
                return STATUS_FAIL;

        }

        BroadcastReceiver.sendBroadcast(broadcastIntent);
        return STATUS_SUCCESS;
    }
}
