package src.ui.screen;

import src.ui.GesTADSSUIInterface;
import src.util.tools.Intent;

public class GesTADSScreensImpl implements GesTADSSUIInterface {
    @Override
    public void loginScreen(Intent intent) {
        //[LAS]
        new LoginScreen(intent);
    }

    @Override
    public void homeScreen(Intent intent) {
        //[LAS]
        new HomeScreen(intent);
    }

    @Override
    public void mainScreen(Intent intent) {
        //[LAS]
        new MainScreen(intent);
    }

    @Override
    public void registerScreen(Intent intent) {
        //[LAS]
        new RegisterEmployeeScreen(intent);
    }

    @Override
    public void dialogScreen(Intent intent) {
        //[LAS]
        new DialogScreen(intent);
    }

    @Override
    public void removeScreen(Intent intent) {
        //[LAS]

    }

    @Override
    public void searchScreen(Intent intent) {
        //[LAS]

    }

    @Override
    public void searchEmployeeScreen(Intent intent) {
        //[LAS]

        new SearchEmployeeScreen(intent);
    }

    @Override
    public void registerProductScreen(Intent intent) {
        //[LAS]

        new RegisterProductScreen(intent);
    }

    @Override
    public void searchProductScreen(Intent intent) {
        //[LAS]

        new SearchProductScreen(intent);
    }

    @Override
    public void transactionScreen(Intent intent) {

        new TransactionScreen(intent);
    }
}