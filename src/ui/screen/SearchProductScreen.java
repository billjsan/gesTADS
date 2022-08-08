package src.ui.screen;

import src.ui.GesTADSUI;
import src.util.tools.BroadcastReceiver;
import src.util.tools.GesLogger;
import src.util.tools.Intent;

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
        //[LAS]

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
        onDestroy();
    }

    private void resultScreen() {
        //[LAS]

    }
}
