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
        new RemoveEmployeeScreen(intent);
    }

    @Override
    public void searchScreen(Intent intent) {
        //[LAS]
        new SearchScreen(intent);
    }

    @Override
    public void searchEmployeeScreen(Intent intent) {
        //[LAS]
        new SearchEmployeeScreen(intent);
    }
}