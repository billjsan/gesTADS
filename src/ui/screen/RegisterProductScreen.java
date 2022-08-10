package src.ui.screen;

import src.ui.GesTADSUI;
import src.util.tools.BroadcastReceiver;
import src.util.tools.GesLogger;
import src.util.tools.Intent;

public class RegisterProductScreen extends GesTADSUI {

    private static final String TAG = "RegisterProductScreen";

    public RegisterProductScreen(Intent intent) {
        super(intent);

        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE) {
            GesLogger.d(TAG, Thread.currentThread(), "constructed with intent");
        }

    }

    @Override
    protected void createView() {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE) {
            GesLogger.d(TAG, Thread.currentThread(), "createView");
        }

        registerProduct();
    }

    private void registerProduct() {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "registerProduct");

        System.out.println(formattedTitle("REGISTER"));
        System.out.println(formattedTitle("Bem vindo ao GesTADS"));
        System.out.println(formattedTitle("Vocês está registrando um produto"));
        System.out.println();

        Intent intent = new Intent(Intent.ACTION_VALIDATE_NEW_PRODUCT);

        System.out.print("Nome: ");
        intent.putString(Intent.KEY_PRODUCT_NAME, screenGetTextFromUser());

        System.out.print("Fabricante: ");
        intent.putString(Intent.KEY_PRODUCT_FABRICANTE, screenGetTextFromUser());

        BroadcastReceiver.sendBroadcast(intent);
    }
}