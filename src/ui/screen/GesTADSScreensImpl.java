package src.ui.screen;

import src.ui.GesTADSSUIInterface;
import src.util.Intent;


public class GesTADSScreensImpl implements GesTADSSUIInterface {

    private static final String TAG = GesTADSScreensImpl.class.getSimpleName();

    @Override
    public void loginScreen(Intent intent) {

        new LoginScreen(intent);
    }

    @Override
    public void homeScreen(Intent intent) {

        new HomeScreen(intent);
    }

    @Override
    public void mainScreen(Intent intent) {

        new MainScreen(intent);
    }

    @Override
    public void registerScreen(Intent intent) {

        new RegisterEmployeeScreen(intent);
    }

    @Override
    public void dialogScreen(Intent intent) {

        new DialogScreen(intent);
    }

    @Override
    public void searchEmployeeScreen(Intent intent) {

        new SearchEmployeeScreen(intent);
    }

    @Override
    public void registerProductScreen(Intent intent) {

        new RegisterProductScreen(intent);
    }

    @Override
    public void searchProductScreen(Intent intent) {

        new SearchProductScreen(intent);
    }

    @Override
    public void transactionScreen(Intent intent) {

        new TransactionScreen(intent);
    }
}