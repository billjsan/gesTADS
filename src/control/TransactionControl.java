package src.control;

import src.model.model.Employee;
import src.model.model.Product;
import src.model.model.Transaction;
import src.model.repository.Repository;
import src.ui.UIManager;
import src.util.BroadcastReceiver;
import src.util.GesLogger;
import src.util.Intent;

import java.util.ArrayList;
import java.util.List;

public class TransactionControl extends BroadcastReceiver {

    private static final String TAG = TransactionControl.class.getSimpleName();
    private final Repository mRepository;
    private final UIManager mUIManager;

    public TransactionControl() {
        mRepository = Repository.getInstance();
        mUIManager = UIManager.getInstance();
    }

    @Override
    protected void onReceive(Intent intent) {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "onReceive action:" + intent.getAction());

        if (intent == null || intent.getAction() == null) return;

        switch (intent.getAction()) {
            case Intent.ACTION_LAUNCH_TRANSACTION_SCREEN:

                launchTransactionScreen(intent);
                break;

            case Intent.ACTION_ENTRADA_PRODUTO:

                entradaDePoduto(intent);
                break;

            case Intent.ACTION_SAIDA_PRODUTO:

                saidaDePoduto(intent);
                break;
        }
    }

    private void saidaDePoduto(Intent intent) {

        if (!intent.hasExtras()) return;

        int qtdRetirada = intent.getInt(Intent.KEY_PRODUCT_QTD_RECEBIDA);
        Intent produtoIntent = intent.getIntent(Intent.KEY_PRODUCT);

        Product product = populateProdutoComIntent(produtoIntent);

        if(qtdRetirada > product.getQtdEstoque()){

            showDialogUI("Valor solicitado superior ao disponível");
            mUIManager.startMainUI(null);

        }else {

            product.setQtdEsqoque(product.getQtdEstoque() - qtdRetirada);
            mRepository.updateProduto(product, produtoIntent.getLong(Intent.KEY_PRODUCT_ID));

            Transaction transaction = new Transaction();
            //transaction.generateId(); todo passei aqui
            transaction.setId(mRepository.getTransactions().get(mRepository.getTransactions().size() -1).getId() + 1);
            transaction.setTipoTranacao(Transaction.TRANSACTION_OUTCOMMING);
            transaction.setQuantidade(qtdRetirada);
            transaction.setSolicitante(mRepository.getCurrentUser().getId());
            transaction.setProdutoId(produtoIntent.getLong(Intent.KEY_PRODUCT_ID));

            mRepository.addTransaction(transaction);

            showDialogUI("Transação Realizada");
            mUIManager.startMainUI(null);
        }
    }

    private void entradaDePoduto(Intent intent) {

        if (!intent.hasExtras()) return;

        int qtdRecebida = intent.getInt(Intent.KEY_PRODUCT_QTD_RECEBIDA);
        Intent produtoIntent = intent.getIntent(Intent.KEY_PRODUCT);

        Product product = populateProdutoComIntent(produtoIntent);

        product.setQtdEsqoque(product.getQtdEstoque() + qtdRecebida);

        mRepository.updateProduto(product, produtoIntent.getLong(Intent.KEY_PRODUCT_ID));

        Transaction transaction = new Transaction();
        //transaction.generateId(); todo passei aqui
        transaction.setId(mRepository.getTransactions().get(mRepository.getTransactions().size() -1).getId() + 1);
        transaction.setTipoTranacao(Transaction.TRANSACTION_INCOMMING);
        transaction.setQuantidade(qtdRecebida);
        transaction.setSolicitante(mRepository.getCurrentUser().getId());
        transaction.setProdutoId(produtoIntent.getLong(Intent.KEY_PRODUCT_ID));

        mRepository.addTransaction(transaction);

        showDialogUI("Transação Realizada");
        mUIManager.startMainUI(null);

    }

    private Product populateProdutoComIntent(Intent intent) {

        Product product = new Product();
        product.setNome(intent.getString(Intent.KEY_PRODUCT_NAME));
        product.setFabricante(intent.getString(Intent.KEY_PRODUCT_FABRICANTE));
        product.setQtdEsqoque(intent.getInt(Intent.KEY_PRODUCT_QTD_ESTOQUE));

        return product;
    }

    private void launchTransactionScreen(Intent intent) {

        if(mRepository.getCurrentUser().getPrivilegio() != Employee.PRIVILEGE_OPERATOR){

            showDialogUI("Acesso negado!");
            mUIManager.startMainUI(null);
        }else{

            intent.putList(Intent.KEY_RESULT_SET, productToIntents(mRepository.getProdutos()));
            intent.putFlag(Intent.FLAG_RESULT_SET);

            intent.putLong(Intent.KEY_EMPLOYEE_ID, mRepository.getCurrentUser().getId());
            mUIManager.startTransactionUI(intent);
        }

    }

    private List<Intent> productToIntents(List<Product> products) {

        List<Intent> intents = new ArrayList<>();

        for (Product product : products) {

            Intent intent = new Intent(Intent.ACTION_RESULT_SET);

            System.out.println("productToIntents: id: " + product.getId());
            intent.putLong(Intent.KEY_PRODUCT_ID, product.getId());
            intent.putString(Intent.KEY_PRODUCT_NAME, product.getNome());
            intent.putString(Intent.KEY_PRODUCT_FABRICANTE, product.getFabricante());
            intent.putInt(Intent.KEY_PRODUCT_QTD_ESTOQUE, product.getQtdEstoque());

            intents.add(intent);
        }

        return intents;
    }

    private void showDialogUI(String message) {

        Intent dialogIntent = new Intent(Intent.ACTION_UI_FLAG);
        dialogIntent.putString(Intent.KEY_MESSAGE_DIALOG, message);

        mUIManager.startDialogUI(dialogIntent);
    }

}