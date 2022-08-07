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
        // [LAS]

        System.out.println(formattedTitle("REGISTER"));
        System.out.println(formattedTitle("Bem vindo ao GesTADS"));
        System.out.println(formattedTitle("Vocês está registrando um produto"));
        System.out.println();

        Intent intent = new Intent(Intent.ACTION_VALIDATE_NEW_PRODUCT);

        System.out.print("Nome: ");
        intent.putString(Intent.KEY_PRODUCT_NAME, screenGetTextFromUser());

        System.out.print("Número Serial: ");
        intent.putString(Intent.KEY_PRODUCT_SERIAL_NUMBER, screenGetTextFromUser());

        System.out.print("Fabricante: ");
        intent.putString(Intent.KEY_PRODUCT_FABRICANTE, screenGetTextFromUser());

        System.out.print("Descrição: ");
        intent.putString(Intent.KEY_PRODUCT_DESCRICAO, screenGetTextFromUser());

        System.out.print("Fabricação: ");
        intent.putString(Intent.KEY_PRODUCT_FABRICACAO, screenGetTextFromUser());

        System.out.print("Descrição: ");
        intent.putString(Intent.KEY_PRODUCT_DESCRICAO, screenGetTextFromUser());

        System.out.print("Validade: ");
        intent.putString(Intent.KEY_PRODUCT_VALIDADE, screenGetTextFromUser());

        BroadcastReceiver.sendBroadcast(intent);
    }
}