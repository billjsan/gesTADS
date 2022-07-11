package src.ui.screen;

import src.ui.GesTADSScreensInterface;
import src.util.tools.Intent;

public class ScreensImpl implements GesTADSScreensInterface {
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
        new RegisterScreen(intent);
    }

    @Override
    public void dialogScreen(Intent intent) {
        //[LAS]
        new DialogScreen(intent);
    }

    @Override
    public void removeScreen(Intent intent) {
        //[LAS]
        new RemoveScreen(intent);
    }
}