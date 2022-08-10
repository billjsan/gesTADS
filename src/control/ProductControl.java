package src.control;

import src.model.model.Employee;
import src.model.model.Product;
import src.model.repository.Repository;
import src.ui.UIManager;
import src.util.tools.BroadcastReceiver;
import src.util.tools.GesLogger;
import src.util.tools.Intent;

import java.util.ArrayList;
import java.util.List;

public class ProductControl extends BroadcastReceiver {

    private static final String TAG = ProductControl.class.getSimpleName();
    private final Repository mRepository;
    private final UIManager mUIManager;

    public ProductControl() {
        mRepository = Repository.getInstance();
        mUIManager = UIManager.getInstance();
    }

    @Override
    protected void onReceive(Intent intent) {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "onReceive action:" + intent.getAction());

        if (intent == null || intent.getAction() == null) return;

        switch (intent.getAction()) {
            case Intent.ACTION_LAUNCH_REGISTER_PRODUCT_SCREEN:

                    launchRegisterProductScreen(intent);
                break;

            case Intent.ACTION_VALIDATE_NEW_PRODUCT:
                validateProduct(intent);
                break;

            case Intent.ACTION_SEARCH_PRODUCT:
                searchProduct(intent);
                break;

            case Intent.ACTION_LAUNCH_SEARCH_PRODUCT_SCREEN:

                launchSearchProductScreen(intent);
                break;

        }

    }

    private void launchSearchProductScreen(Intent intent) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "launchSearchProductScreen");

        mUIManager.startSearchProductUI(intent);
    }

    private void searchProduct(Intent intent) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG,Thread.currentThread(), "searchProduct");

        if(!intent.hasExtras()) {
            showDialogUI("Erro na busca");
            if (mRepository.isLoggedIn()) mUIManager.startMainUI(null);
            else mUIManager.startHomeUI(null);
        }

        List<Integer> flags = intent.getFlags();

        if(flags.contains(Intent.FLAG_SEARCH_PRODUCT_BY_SERIAL_NUMBER)){
            if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
                GesLogger.d(TAG,Thread.currentThread(), "searchProduct by serial number");

            String serialNo = intent.getString(Intent.KEY_PRODUCT_SERIAL_NUMBER);
            Product product = mRepository.getProductBySerial(serialNo);

            if (product == null){
                showDialogUI("Nenhum resultado encontrado");

                if(mRepository.isLoggedIn())
                    mUIManager.startSearchProductUI(new Intent(Intent.ACTION_LAUNCH_SEARCH_PRODUCT_SCREEN));

            }else {
                ArrayList<Intent> intents = new ArrayList<>();
                intents.add(populateIntentWithProduct(Intent.ACTION_RESULT_SET, product));

                Intent resulSetIntent = new Intent(Intent.ACTION_RESULT_SET);
                resulSetIntent.putList(Intent.KEY_RESULT_SET, intents);

                if(mRepository.isLoggedIn()) mUIManager.startSearchProductUI(resulSetIntent);
            }
        }
    }

    private void validateProduct(Intent intent) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "validateProduct");

        List<Product> produtos = mRepository.getProdutos();

        for (Product p : produtos) {
            if (p.getId() == intent.getLong(Intent.KEY_PRODUCT_ID)) { // todo checar se isso dá certo
                showDialogUI("produto ja cadastrado");

                mUIManager.startMainUI(intent);
                return;
            }
        }

        Product product = populateProductWithIntent(intent);
        product.generateId();

        System.out.println("product control: salvando produto no repo: ");
        System.out.println(product.getId());
        System.out.println(product.getNome());
        System.out.println(product.getFabricante());
        System.out.println(product.getQtdEstoque());


        mRepository.addProduct(product);
        showDialogUI("Produto cadastrado com sucesso");
        mUIManager.startMainUI(intent);
    }

    private Product populateProductWithIntent(Intent intent) {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "populateProductWithIntent");

        return new Product(
                intent.getString(Intent.KEY_PRODUCT_NAME),
                intent.getString(Intent.KEY_PRODUCT_FABRICANTE));
    }

    private void launchRegisterProductScreen(Intent intent) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "launchRegisterProductScreen");

        if(mRepository.getCurrentUser().getPrivilegio() != Employee.PRIVILEGE_ADMIN){

            showDialogUI("Acesso negado!");
            mUIManager.startMainUI(null);
        }else{

            mUIManager.startRegisterProductScreen(intent);
        }
    }

    private void showDialogUI(String message) {

        Intent dialogIntent = new Intent(Intent.ACTION_UI_FLAG);
        dialogIntent.putString(Intent.KEY_MESSAGE_DIALOG, message);

        mUIManager.startDialogUI(dialogIntent);
    }

    private Intent populateIntentWithProduct(int action, Product product){
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG,Thread.currentThread(), "populateIntentWithProduct");

        Intent intent = new Intent(action);
        intent.putString(Intent.KEY_PRODUCT_NAME, product.getNome());
        intent.putString(Intent.KEY_PRODUCT_FABRICANTE, product.getFabricante());
        intent.putLong(Intent.KEY_PRODUCT_ID, product.getId());

        return intent;
    }


}
