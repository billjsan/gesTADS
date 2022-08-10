package src.ui.screen;

import src.model.model.Product;
import src.ui.GesTADSUI;
import src.util.tools.BroadcastReceiver;
import src.util.tools.GesLogger;
import src.util.tools.Intent;

import java.util.ArrayList;
import java.util.List;

public class TransactionScreen extends GesTADSUI {

    private final String TAG = "TransactionScreen";
    public TransactionScreen(Intent intent) {

        super(intent);
    }

    @Override
    protected void onCreate() {
        super.onCreate();


    }

    @Override
    protected void createView() {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "createView");

        System.out.println(formattedTitle("TRANSAÇÃO"));
        System.out.println();
        System.out.println("Opções:");
        System.out.println();
        System.out.println(formattedLineMenu("Entrada de produto", "[1]"));
        System.out.println(formattedLineMenu("Saída de produto", "[2]"));

        boolean isSellected = false;
        do{
            switch (screenGetTextFromUser()){
                case "1":
                    entradaDeProduto();
                    isSellected = true;
                    break;

                case "2":
                    saidaDeProduto();
                    isSellected = true;
                    break;

                default:
                    showGesTADSUIDialogScreen("Escolha uma opão");
            }
        }while (!isSellected);

    }

    private void saidaDeProduto() {
        Intent resultIntent = new Intent(Intent.ACTION_SAIDA_PRODUTO);
        resultIntent.putFlag(Intent.FLAG_RESULT_SET);

        Intent produtoSelecionado = getProdutoSelecionadoIntent();

        System.out.println("Quantidade de produtos RETIRADOS: ");

        Integer qtd = screenGetIntegerFromUser();
        resultIntent.putIntent(Intent.KEY_PRODUCT, produtoSelecionado);
        resultIntent.putInt(Intent.KEY_PRODUCT_QTD_RECEBIDA, qtd);

        BroadcastReceiver.sendBroadcast(resultIntent);
    }

    private void entradaDeProduto() {

        Intent resultIntent = new Intent(Intent.ACTION_ENTRADA_PRODUTO);
        resultIntent.putFlag(Intent.FLAG_RESULT_SET);

        Intent produtoSelecionado = getProdutoSelecionadoIntent();

        System.out.println("Quantidade de produtos RECEBIDOS: ");
        Integer qtd = screenGetIntegerFromUser();

        resultIntent.putIntent(Intent.KEY_PRODUCT, produtoSelecionado);
        resultIntent.putInt(Intent.KEY_PRODUCT_QTD_RECEBIDA, qtd);

        BroadcastReceiver.sendBroadcast(resultIntent);
    }

    private Intent getProdutoSelecionadoIntent(){

        List<Intent> resultSet = getResultSet();

        try {
            for (int i = 0; i < resultSet.size(); i++) {

                String item = "Id: ";
                item += resultSet.get(i).getLong(Intent.KEY_PRODUCT_ID);
                item += " nome: ";
                item += resultSet.get(i).getString(Intent.KEY_PRODUCT_NAME);
                item += " fabricante: ";
                item += resultSet.get(i).getString(Intent.KEY_PRODUCT_FABRICANTE);
                item += " qtd: ";
                item += resultSet.get(i).getInt(Intent.KEY_PRODUCT_QTD_ESTOQUE);

                System.out.println(formattedLineMenu(item, String.valueOf(i)));
            }
        }catch (NullPointerException e){
            if (GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE) GesLogger.e(TAG,
                    "can't show result set");

        }

        do{
            System.out.println();
            System.out.print("Escolha um dos produtos ");

            Integer indice = screenGetIntegerFromUser();

            if(indice >= 0 && indice < resultSet.size()){

                return resultSet.get(indice);
            }else {
                System.out.println("Entrada inválida");

            }
        }while (true);

    }

    private List<Intent> getResultSet() {

        List<Intent> result = new ArrayList<>();
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
