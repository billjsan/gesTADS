package src.control;

import src.model.model.Product;
import src.model.repository.Repository;
import src.ui.UIManager;
import src.util.tools.BroadcastReceiver;
import src.util.tools.GesLogger;
import src.util.tools.Intent;

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

        }

    }

    private void validateProduct(Intent intent) {
        //[LAS]

        List<Product> produtos = mRepository.getProdutos();

        for (Product p : produtos) {
            if (p.getSerialN().equals(intent.getString(Intent.KEY_PRODUCT_SERIAL_NUMBER))) {
                showDialogUI("produto ja cadastrado");

                mUIManager.startMainUI(intent);
                return;
            }
        }

        mRepository.addProduct(populateProductWithIntent(intent));
        showDialogUI("Produto cadastrado com sucesso");
        mUIManager.startMainUI(intent);
    }

    private Product populateProductWithIntent(Intent intent) {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "populateProductWithIntent");

        return new Product(
                intent.getString(Intent.KEY_PRODUCT_NAME),
                intent.getString(Intent.KEY_PRODUCT_SERIAL_NUMBER),
                intent.getString(Intent.KEY_PRODUCT_FABRICANTE),
                intent.getString(Intent.KEY_PRODUCT_DESCRICAO),
                intent.getString(Intent.KEY_PRODUCT_FABRICACAO),
                intent.getString(Intent.KEY_PRODUCT_VALIDADE));
    }

    private void launchRegisterProductScreen(Intent intent) {
        //[LAS]

        mUIManager.startRegisterProductScreen(intent);
    }

    private void showDialogUI(String message) {

        Intent dialogIntent = new Intent(Intent.ACTION_UI_FLAG);
        dialogIntent.putString(Intent.KEY_MESSAGE_DIALOG, message);

        mUIManager.startDialogUI(dialogIntent);
    }
}
